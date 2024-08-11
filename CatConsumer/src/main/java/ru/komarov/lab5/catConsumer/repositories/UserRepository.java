package ru.komarov.lab5.catConsumer.repositories;

import org.springframework.stereotype.Repository;
import ru.komarov.lab5.catConsumer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

}
