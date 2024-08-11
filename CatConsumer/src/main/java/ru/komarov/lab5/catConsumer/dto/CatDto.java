package ru.komarov.lab5.catConsumer.dto;

import ru.komarov.lab5.catConsumer.entities.enums.CatBreed;
import ru.komarov.lab5.catConsumer.entities.enums.CatColor;
import lombok.*;

import java.util.Calendar;

@Data
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CatDto {
    private long id;
    private String name;
    private Calendar birthdayDate;
    private CatColor color;
    private CatBreed breed;
    private long ownerId;

    public CatDto(CatDto catDto) {
        this.id = catDto.id;
        this.name = catDto.name;
        this.birthdayDate = catDto.birthdayDate;
        this.color = catDto.color;
        this.breed = catDto.breed;
        this.ownerId = catDto.ownerId;
    }
}
