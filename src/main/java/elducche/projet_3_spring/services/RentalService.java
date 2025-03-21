package elducche.projet_3_spring.services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import elducche.projet_3_spring.exception.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import elducche.projet_3_spring.dto.RentalDTO;
import elducche.projet_3_spring.dto.ResponseDTO;
import elducche.projet_3_spring.exception.UnauthenticatedException;
import elducche.projet_3_spring.model.Rental;
import elducche.projet_3_spring.repository.RentalRepository;
import elducche.projet_3_spring.repository.UserRepository;
import elducche.projet_3_spring.security.UserDetailsServiceImplementation;

import java.io.IOException;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public ResponseDTO saveRental(RentalDTO rentalDTO) {
        try {
            if (rentalDTO.getDescription() == null || rentalDTO.getDescription().length() > 2000) {
                throw new IllegalArgumentException("Description must be between 1 and 2000 characters");
            }
            // Gestion du fichier
            String fileName = null;
            if (rentalDTO.getPicture() != null && !rentalDTO.getPicture().isEmpty()) {
                fileName = UUID.randomUUID().toString() + "_" + rentalDTO.getPicture().getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.createDirectories(path.getParent());
                Files.copy(rentalDTO.getPicture().getInputStream(), path);
            }

            // Récupération de l'id de l'utilisateur connecté via le token
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Long userId = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new UnauthenticatedException("User not found"))
                    .getId();

            // Création de l'entité
            Rental rental = new Rental();
            rental.setOwnerId(userId);
            rental.setName(rentalDTO.getName());
            rental.setSurface(rentalDTO.getSurface());
            rental.setPrice(rentalDTO.getPrice());
            rental.setDescription(rentalDTO.getDescription());
            rental.setPicture(fileName);

            rentalRepository.save(rental);
            return new ResponseDTO("Rental created !");

        } catch (IOException e) {
            throw new RuntimeException("Error when saving rental", e);
        }
    }

    public ResponseDTO updateRental(Long id, RentalDTO rental) {
        final Rental rentalToUpdate = rentalRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found with id: " + id));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long userId = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UnauthenticatedException("User not found"))
                .getId();
        if (!userId.equals(rentalToUpdate.getOwnerId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this rental");
        }
        rentalToUpdate.setName(null != rental.getName() ? rental.getName() : rentalToUpdate.getName());
        rentalToUpdate.setPrice(null != rental.getPrice() ? rental.getPrice() : rentalToUpdate.getPrice());
        rentalToUpdate.setSurface(null != rental.getSurface() ? rental.getSurface() : rentalToUpdate.getSurface());
        rentalToUpdate.setDescription(
                null != rental.getDescription() ? rental.getDescription() : rentalToUpdate.getDescription());
        rentalRepository.save(rentalToUpdate);

        return new ResponseDTO("Rental updated !");
    }
}
