package elducche.projet_3_spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import elducche.projet_3_spring.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
}
