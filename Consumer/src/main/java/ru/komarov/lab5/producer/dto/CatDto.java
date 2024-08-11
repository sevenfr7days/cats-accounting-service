package ru.komarov.lab5.producer.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.komarov.lab5.producer.entity.enums.CatBreed;
import ru.komarov.lab5.producer.entity.enums.CatColor;
import lombok.Builder;
import lombok.Data;

import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatDto {
    private long id;
    private String name;
    private Calendar birthdayDate;
    private CatColor color;
    private CatBreed breed;
    private long ownerId;
}
