package ru.komarov.lab5.ownerConsumer.entities;

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
    private long id;

    @Column(name = "name")
    private String name;

    @Column (name = "birth_date")
    private Calendar birthdayDate;
}
