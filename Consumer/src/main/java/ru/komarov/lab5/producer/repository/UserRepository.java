package ru.komarov.lab5.producer.repository;

import org.springframework.stereotype.Repository;
import ru.komarov.lab5.producer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

}
