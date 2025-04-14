package elducche.projet_3_spring.utils;

import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@NoArgsConstructor
public class ImageHelper {

    private static final String UPLOAD_DIR = "rentals-images/";

    public static String saveImage(MultipartFile file) {

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("File should not be empty");
            }

            // Get the file's original filename
            String fileName = UUID.randomUUID().toString() + "_" +file.getOriginalFilename();

            // Check if the file has a valid filename
            if (fileName == null || fileName.contains("..")) {
                throw new RuntimeException("The filename is invalid");
            }

            // Ensure the uploads directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file to the target location
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            return UPLOAD_DIR + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not upload the file: " + e.getMessage());
        }

    }
}


