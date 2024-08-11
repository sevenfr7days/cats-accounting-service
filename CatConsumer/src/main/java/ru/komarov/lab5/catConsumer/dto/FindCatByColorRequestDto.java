package ru.komarov.lab5.catConsumer.dto;

import ru.komarov.lab5.catConsumer.entities.enums.CatColor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindCatByColorRequestDto {
    private CatColor color;
}
