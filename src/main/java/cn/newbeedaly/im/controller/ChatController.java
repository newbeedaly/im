package cn.newbeedaly.im.controller;

import cn.newbeedaly.im.model.Message;
import cn.newbeedaly.im.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String username, @RequestParam String room, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("room", room);
        return "chat";
    }

    @MessageMapping("/chat/{room}")
    public void sendMessage(@DestinationVariable String room, Message message) {
        message.setRoom(room);
        chatService.sendMessage(message);
    }
}
