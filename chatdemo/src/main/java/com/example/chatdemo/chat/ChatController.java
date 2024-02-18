package com.example.chatdemo.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations messageTemplate;
    private final UserCountService userCountService;
    @MessageMapping("/chat.addUser") //ขาเข้า frontendต้องเรียกผ่านนี้
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        int currentCount = userCountService.incrementCount();
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        messageTemplate.convertAndSend("/topic/userOnline", currentCount);
        return message;
    }
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
       return chatMessage;
    }
}
