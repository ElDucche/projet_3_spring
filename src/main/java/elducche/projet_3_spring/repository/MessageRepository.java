package elducche.projet_3_spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import elducche.projet_3_spring.model.Message;

@Repository
public interface MessageRepository  extends CrudRepository<Message, Long> {   
}
