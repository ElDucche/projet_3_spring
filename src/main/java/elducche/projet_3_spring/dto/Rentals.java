package elducche.projet_3_spring.dto;

import elducche.projet_3_spring.model.Rental;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Rentals {
    List<Rental> rentals;
}
