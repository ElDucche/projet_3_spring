package elducche.projet_3_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import elducche.projet_3_spring.dto.MessageDTO;
import elducche.projet_3_spring.services.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping(path = "/messages", consumes = "application/json", produces = "application/json") 
    public String sendMessage(@RequestBody MessageDTO message) {
        return messageService.sendMessage(message);
    }

}
