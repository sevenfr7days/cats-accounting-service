package ru.komarov.lab5.catConsumer.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column (name = "birth_date")
    private Calendar birthdayDate;
}
