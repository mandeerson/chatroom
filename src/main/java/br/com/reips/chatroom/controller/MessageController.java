package br.com.reips.chatroom.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import br.com.reips.chatroom.enums.ActivityType;
import br.com.reips.chatroom.model.Activity;
import br.com.reips.chatroom.model.Colaborate;
import br.com.reips.chatroom.model.Content;
import br.com.reips.chatroom.model.User;

@Controller
public class MessageController {

    private static final Content CONTENT = new Content();

    @Autowired
    private SimpMessageSendingOperations messageSendingOperations;

    @MessageMapping("/join")
    @SendTo("/topic/response")
    public Content message(@Payload User user, SimpMessageHeaderAccessor headerAccessor) {
        CONTENT.getUsers().add(user);
        CONTENT.getActivity().add(new Activity(user, ActivityType.JOIN));
        headerAccessor.getSessionAttributes().put("user", user);
        return CONTENT;
    }

    @MessageMapping("/colaborate")
    @SendTo("/topic/response")
    public Content colaborate(@Payload Colaborate message, SimpMessageHeaderAccessor headerAccessor) {
        CONTENT.setContent(message.getContent());
        return CONTENT;
    }

    @EventListener
    public void socketDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        if (headerAccessor.getSessionAttributes().containsKey("user")) {
            User user = (User) headerAccessor.getSessionAttributes().get("user");
            Iterator<User> iterator = CONTENT.getUsers().iterator();
            while (iterator.hasNext()) {
                User current = iterator.next();
                if (user.getUsername().equals(current.getUsername())) {
                    iterator.remove();
                }
            }
            CONTENT.getActivity().add(new Activity(user, ActivityType.LEAVE));
        }
        messageSendingOperations.convertAndSend("/topic/response", CONTENT);
    }

}
