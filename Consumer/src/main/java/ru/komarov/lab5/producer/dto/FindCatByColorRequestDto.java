package ru.komarov.lab5.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.komarov.lab5.producer.entity.enums.CatColor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindCatByColorRequestDto {
    private CatColor color;
}
