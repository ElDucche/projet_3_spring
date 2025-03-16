package elducche.projet_3_spring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import elducche.projet_3_spring.model.User;
import elducche.projet_3_spring.repository.UserRepository;
import lombok.Data;

@Data
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current authentication: {}" + authentication);
        return userRepository.findById(id);
    }
}
