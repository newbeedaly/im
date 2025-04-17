package cn.newbeedaly.im.service;


import cn.newbeedaly.im.model.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final SimpMessagingTemplate messagingTemplate;

    public ChatService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(Message message) {
        messagingTemplate.convertAndSend("/topic/" + message.getRoom(), message);
    }
}
