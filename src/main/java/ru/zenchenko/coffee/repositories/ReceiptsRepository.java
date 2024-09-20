package ru.zenchenko.coffee.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zenchenko.coffee.model.Receipt;

import java.util.Optional;

@Repository
public interface ReceiptsRepository extends JpaRepository<Receipt, Long> {
    Optional<Receipt> findByName(String name);
}
