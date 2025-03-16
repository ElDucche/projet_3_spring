package elducche.projet_3_spring.model;

import java.sql.Timestamp;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "rentals")
public class Rental {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "picture")
    private String picture;

    @Column(name = "created_at")
    private Timestamp createdAt;
 
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name= "surface")
    private Integer surface;
}
