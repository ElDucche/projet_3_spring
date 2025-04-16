package elducche.projet_3_spring.services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import elducche.projet_3_spring.exception.ImageException;
import elducche.projet_3_spring.model.User;
import elducche.projet_3_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import elducche.projet_3_spring.exception.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import elducche.projet_3_spring.dto.RentalDTO;
import elducche.projet_3_spring.dto.ResponseDTO;
import elducche.projet_3_spring.exception.UnauthenticatedException;
import elducche.projet_3_spring.model.Rental;
import elducche.projet_3_spring.repository.RentalRepository;
import elducche.projet_3_spring.security.UserDetailsServiceImplementation;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserRepository userRepository;

    final Instant now = Instant.now();
    private final Path storageLocation = Paths.get("uploads");
    private static final String UPLOAD_DIR = "/uploads/";

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll().stream()
                .peek(rental -> rental.setPicture(
                        ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .path(rental.getPicture())
                                .toUriString()
                ))
                .toList();
    }


    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id)
                .map(rental -> {
                    rental.setPicture(ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path(rental.getPicture())
                            .toUriString());
                    return rental;
                }).orElseThrow(() -> new RuntimeException("rental not found"));
    }

    public ResponseDTO saveRental(RentalDTO rentalDTO) {
        if (rentalDTO.getDescription() == null || rentalDTO.getDescription().length() > 2000) {
            throw new IllegalArgumentException("Description must be between 1 and 2000 characters");
        }
        // Gestion du fichier
        String fileName = null;

        // Récupération de l'id de l'utilisateur connecté via le token
        // Get the current authenticated user id
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = null;
        if (principal instanceof UserDetails userDetails && userDetails.getUsername() != null) {
            String email = userDetails.getUsername();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UnauthenticatedException("User not found with email: " + email));
            userId = user.getId();
        }

        if (userId == null) {
            throw new UnauthenticatedException("User not authenticated");
        }
        // Création de l'entité
        Rental rental = new Rental();

        String picturePath = storePicture(rentalDTO.getPicture());

        // Assign the user id to the rental
        rental.setOwnerId(userId);
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setDescription(rentalDTO.getDescription());
        rental.setPicture(fileName);
        rental.setPicture(UPLOAD_DIR + picturePath);
        rental.setCreatedAt(now);
        rental.setUpdatedAt(now);

        rentalRepository.save(rental);
        return new ResponseDTO("Rental created !");

    }

    public ResponseDTO updateRental(Long id, RentalDTO rental) {
        final Rental rentalToUpdate = rentalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found with id: " + id));
        rentalToUpdate.setName(null != rental.getName() ? rental.getName() : rentalToUpdate.getName());
        rentalToUpdate.setPrice(null != rental.getPrice() ? rental.getPrice() : rentalToUpdate.getPrice());
        rentalToUpdate.setSurface(null != rental.getSurface() ? rental.getSurface() : rentalToUpdate.getSurface());
        rentalToUpdate.setDescription(null != rental.getDescription() ? rental.getDescription() : rentalToUpdate.getDescription());
        rentalRepository.save(rentalToUpdate);

        return new ResponseDTO("Rental updated !");
    }

    private String storePicture(MultipartFile picture) {
        try {
            String fileName = System.currentTimeMillis() + "_" + picture.getOriginalFilename();
            Path destination = storageLocation.resolve(fileName);
            Files.copy(picture.getInputStream(), destination);
            return fileName;
        } catch (Exception ex) {
            throw new ImageException("Error occurred while uploading rental picture", ex);
        }
    }
}
