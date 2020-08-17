package br.com.reips.chatroom.listener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import br.com.reips.chatroom.model.ChatMessage;
import br.com.reips.chatroom.model.ChatMessage.MessageType;

@Component
public class WebSocketEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void socketConnected(SessionConnectedEvent event) {
        LOGGER.info("Socket connected");
    }

    @EventListener
    public void socketDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAcessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAcessor.getSessionAttributes().get("username");
        String privateUsername = (String) headerAcessor.getSessionAttributes().get("private-username");

        if (StringUtils.isNotBlank(username)) {
            LOGGER.info(String.format("User {} disconnected", username));
            messageSendingOperations.convertAndSend("/topic/public", createLeaveMessage(username));
        }

        if (StringUtils.isNotBlank(username)) {
            LOGGER.info(String.format("User {} disconnected", privateUsername));
            messageSendingOperations.convertAndSend("/queue/reply", createLeaveMessage(privateUsername));
        }

    }

    private ChatMessage createLeaveMessage(String username) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(MessageType.LEAVE);
        chatMessage.setSender(username);
        return chatMessage;
    }

}
