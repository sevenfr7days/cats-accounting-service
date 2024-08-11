package ru.komarov.lab5.producer.repository;

import org.springframework.stereotype.Repository;
import ru.komarov.lab5.producer.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
