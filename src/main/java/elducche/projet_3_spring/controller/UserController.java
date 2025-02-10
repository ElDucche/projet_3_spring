package elducche.projet_3_spring.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import elducche.projet_3_spring.model.User;
import elducche.projet_3_spring.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/user/:id")
    public Optional<User> getUserById(@RequestParam Long id) {
        return userService.getUserById(id);
    }
    
    
}
