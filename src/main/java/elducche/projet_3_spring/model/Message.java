package elducche.projet_3_spring.model;

import java.security.Timestamp;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "rental_id")
    private Long rentalId;

    @Column(name = "user_id")
    private Long userId;
}
