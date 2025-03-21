package elducche.projet_3_spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elducche.projet_3_spring.dto.RentalDTO;
import elducche.projet_3_spring.dto.ResponseDTO;
import elducche.projet_3_spring.model.Rental;
import elducche.projet_3_spring.services.RentalService;
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
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("{id}")
    public Optional<Rental> getUserById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> saveRental(@ModelAttribute RentalDTO rental) {
        return ResponseEntity.ok(rentalService.saveRental(rental));
    }

    @PutMapping("{id}")
    public ResponseDTO updateRental(@PathVariable Long id, @ModelAttribute RentalDTO rental) {
        return rentalService.updateRental(id, rental);
    }

}
