package elducche.projet_3_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import elducche.projet_3_spring.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    
}
