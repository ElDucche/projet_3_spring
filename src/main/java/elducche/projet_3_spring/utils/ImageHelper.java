package elducche.projet_3_spring.utils;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ImageHelper {

    @Value("${app.upload.dir:rentals-images}")
    private String uploadDir;  // Ajout du point-virgule

    @Value("${app.base.url:http://localhost:9000}")
    private String baseUrl;
    // Retrait du mot-clé 'static'
    public String saveImage(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("File should not be empty");
            }

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            if (fileName == null || fileName.contains("..")) {
                throw new RuntimeException("The filename is invalid");
            }

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            return baseUrl + "/" + uploadDir + "/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to store file", e);
        }
    }
}


