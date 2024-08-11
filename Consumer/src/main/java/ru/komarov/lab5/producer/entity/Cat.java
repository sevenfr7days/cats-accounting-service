package ru.komarov.lab5.producer.entity;


import ru.komarov.lab5.producer.entity.enums.CatBreed;
import ru.komarov.lab5.producer.entity.enums.CatColor;
import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Getter
@Setter
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cats")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "name")
    private String name;

    @Column (name = "birth_date")
    private Calendar birthdayDate;

    @Column (name = "color")
    @Enumerated(EnumType.STRING)
    private CatColor color;

    @Column (name = "breed")
    @Enumerated(EnumType.STRING)
    private CatBreed breed;

    @Column (name = "owner_id")
    private long ownerId;

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthday = dateFormat.format(birthdayDate.getTime());

        return "Котик : " +
                "id = " + id +
                ", имя : '" + name + '\'' +
                ", день рождения : " + birthday +
                ", цвет : " + color +
                ", порода : " + breed +
                ", id владельца : " + ownerId;
    }
}