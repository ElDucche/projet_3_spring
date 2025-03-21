package elducche.projet_3_spring.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RentalDTO {
    private String name;
    private Integer surface;
    private String price;
    private String description;
    private MultipartFile picture;
}
