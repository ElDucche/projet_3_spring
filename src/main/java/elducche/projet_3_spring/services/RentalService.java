package elducche.projet_3_spring.services;

import java.util.List;
import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import elducche.projet_3_spring.exception.ForbiddenException;
import elducche.projet_3_spring.exception.NotFoundException;
import elducche.projet_3_spring.exception.UnauthenticatedException;

import elducche.projet_3_spring.dto.RentalDTO;
import elducche.projet_3_spring.dto.RentalResponse;
import elducche.projet_3_spring.dto.ResponseDTO;
import elducche.projet_3_spring.model.Rental;
import elducche.projet_3_spring.repository.RentalRepository;
import elducche.projet_3_spring.repository.UserRepository;
import elducche.projet_3_spring.utils.ImageHelper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageHelper imageHelper;

    private final Path storageLocation = Paths.get("uploads");
  private static final String UPLOAD_DIR = "/uploads/";

    public List<Rental> getAllRentals() {
 return rentalRepository.findAll().stream()
                .peek(rental -> rental.setPicture(ServletUriComponentsBuilder.fromCurrentContextPath().path(rental.getPicture()).toUriString())).toList();
    }

    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).map(rental -> {
            rental.setPicture(ServletUriComponentsBuilder.fromCurrentContextPath().path(rental.getPicture()).toUriString());
            return rental;
        }).orElseThrow(()-> new NotFoundException("Rental not found with id: " + id));
    }

    public ResponseDTO saveRental(RentalDTO rentalDTO) {

        // Récupération de l'id de l'utilisateur connecté via le token
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long userId = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UnauthenticatedException("User not found"))
                .getId();

        // Création de l'entité
        Rental rental = new Rental();
    String picturePath = storePicture(rentalDTO.getPicture());
        rental.setOwnerId(userId);
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setDescription(rentalDTO.getDescription());
        rental.setPicture(UPLOAD_DIR + picturePath);

        rentalRepository.save(rental);
        return new ResponseDTO("Rental created !");

    }

    public ResponseDTO updateRental(Long id, RentalDTO rental) {
        final Rental rentalToUpdate = rentalRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Rental not found with id: " + id));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long userId = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UnauthenticatedException("User not found"))
                .getId();
        if (!userId.equals(rentalToUpdate.getOwnerId())) {
            throw new ForbiddenException("You are not the owner of this rental");
        }
        rentalToUpdate.setName(null != rental.getName() ? rental.getName() : rentalToUpdate.getName());
        rentalToUpdate.setPrice(null != rental.getPrice() ? rental.getPrice() : rentalToUpdate.getPrice());
        rentalToUpdate.setSurface(null != rental.getSurface() ? rental.getSurface() : rentalToUpdate.getSurface());
        rentalToUpdate.setDescription(
                null != rental.getDescription() ? rental.getDescription() : rentalToUpdate.getDescription());
        rentalRepository.save(rentalToUpdate);

        return new ResponseDTO("Rental updated !");
    }

    public ResponseDTO deleteRental(Long id) {
        final Rental rentalToDelete = rentalRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Rental not found with id: " + id));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long userId = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UnauthenticatedException("User not found"))
                .getId();
        if (!userId.equals(rentalToDelete.getOwnerId())) {
            throw new ForbiddenException("You are not the owner of this rental");
        }
        rentalRepository.delete(rentalToDelete);
        return new ResponseDTO("Rental deleted !");
    }

  private String storePicture(MultipartFile picture) {
        try {
            String fileName = System.currentTimeMillis() + "_" + picture.getOriginalFilename();
            Path targetLocation = storageLocation.resolve(fileName);
            Files.copy(picture.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store picture: " + e.getMessage());
        }
    }
}
