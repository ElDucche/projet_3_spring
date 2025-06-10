package elducche.projet_3_spring.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "RENTALS")
public class Rental {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "picture")
    private String picture;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "yyyy/MM/dd")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
 
    @LastModifiedDate
    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "owner_id")
    @JsonProperty("owner_id")
    private Long ownerId;

    @Column(name= "surface")
    private Integer surface;
}
