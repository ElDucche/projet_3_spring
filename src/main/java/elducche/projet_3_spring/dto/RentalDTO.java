package elducche.projet_3_spring.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RentalDTO {
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Surface is required")
    @Positive(message = "Surface must be greater than 0")
    private Integer surface;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Integer price;
    
    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;
    
    @NotNull(message = "Picture is required")
    private MultipartFile picture;
}
