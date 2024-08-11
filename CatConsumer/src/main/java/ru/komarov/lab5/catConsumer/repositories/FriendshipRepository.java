package ru.komarov.lab5.catConsumer.repositories;

import ru.komarov.lab5.catConsumer.entities.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Collection<Friendship> findFriendshipByCat1Id(long cat1Id);
}
