package elducche.projet_3_spring.dto;

import lombok.Data;

@Data
public class ResponseDTO {
    private String message;

    public ResponseDTO(String message) {
        this.message = message;
    }
}
