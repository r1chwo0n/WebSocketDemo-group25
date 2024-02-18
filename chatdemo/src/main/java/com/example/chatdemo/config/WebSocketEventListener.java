package com.example.chatdemo.config;
import com.example.chatdemo.chat.ChatMessage;
import com.example.chatdemo.chat.MessageType;
import com.example.chatdemo.chat.UserCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageTemplate;
    private final UserCountService userCountService;
    @EventListener
    public void handleWebsocketDisconnect(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null){
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            int currentCount = userCountService.decrementCount();
            messageTemplate.convertAndSend("/topic/userOnline", currentCount);
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
