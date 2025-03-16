package elducche.projet_3_spring.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
