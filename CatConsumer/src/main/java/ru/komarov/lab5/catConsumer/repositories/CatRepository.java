package ru.komarov.lab5.catConsumer.repositories;

import ru.komarov.lab5.catConsumer.entities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
}
