package ru.komarov.lab5.catConsumer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "friendships")
public class Friendship {
    @Id
    private long id;

    @Column(name = "cat1_id")
    private long cat1Id;

    @Column(name = "cat2_id")
    private long cat2Id;
}
