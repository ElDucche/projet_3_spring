package elducche.projet_3_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import elducche.projet_3_spring.model.Message;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Long> {   
}
