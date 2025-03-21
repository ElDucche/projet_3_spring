package elducche.projet_3_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import elducche.projet_3_spring.dto.MessageDTO;

import elducche.projet_3_spring.services.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/messages")  
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping // Simplification du mapping
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO message) {
        return ResponseEntity.ok(messageService.sendMessage(message));
    }
}
