package elducche.projet_3_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elducche.projet_3_spring.dto.MessageDTO;
import elducche.projet_3_spring.dto.ResponseDTO;
import elducche.projet_3_spring.model.Message;
import elducche.projet_3_spring.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public ResponseDTO sendMessage(MessageDTO message) {
        System.out.println("Accès au service message");
        final Message messageToSave = new Message()
                .setMessage(message.getMessage())
                .setRentalId(message.getRentalId())
                .setUserId(message.getUserId());
        messageRepository.save(messageToSave).getMessage();
        return  new ResponseDTO("Message send with success");
    }
}
