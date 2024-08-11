package ru.komarov.lab5.producer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table (name = "friendship")
public class Friendship {
    @Id
    private long id;

    @Column(name = "cat1_id")
    private long cat1Id;

    @Column(name = "cat2_id")
    private long cat2Id;

    public Friendship(){
    }
}