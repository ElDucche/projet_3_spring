package elducche.projet_3_spring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull(message = "Email is required")
    private String email;
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Password is required")
    private String password;
}
