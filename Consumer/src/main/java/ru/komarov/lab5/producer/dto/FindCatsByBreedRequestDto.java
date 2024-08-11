package ru.komarov.lab5.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.komarov.lab5.producer.entity.enums.CatBreed;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindCatsByBreedRequestDto {
    private CatBreed catBreed;
}
