package elducche.projet_3_spring.dto;

import java.util.List;

import elducche.projet_3_spring.model.Rental;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentalResponse {
    private List<Rental> rentals;
}
