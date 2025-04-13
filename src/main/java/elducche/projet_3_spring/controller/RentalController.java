package elducche.projet_3_spring.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elducche.projet_3_spring.dto.RentalDTO;
import elducche.projet_3_spring.dto.RentalResponse;
import elducche.projet_3_spring.dto.ResponseDTO;
import elducche.projet_3_spring.exception.NotFoundException;
import elducche.projet_3_spring.model.Rental;
import elducche.projet_3_spring.services.RentalService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public RentalResponse getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("{id}")
    public Rental getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id).orElseThrow(
                () -> new NotFoundException("Rental not found"));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> saveRental(@Valid @ModelAttribute RentalDTO rental, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO("Invalid data: " +
                            result.getAllErrors().stream()
                                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                    .collect(Collectors.joining(", "))));
        }
        return ResponseEntity.ok(rentalService.saveRental(rental));
    }

    @PutMapping("{id}")
    public ResponseDTO updateRental(@PathVariable Long id, @ModelAttribute RentalDTO rental) {
        return rentalService.updateRental(id, rental);
    }

}
