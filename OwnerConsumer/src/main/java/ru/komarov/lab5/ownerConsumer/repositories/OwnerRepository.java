package ru.komarov.lab5.ownerConsumer.repositories;

import org.springframework.stereotype.Repository;
import ru.komarov.lab5.ownerConsumer.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
