package ru.komarov.lab5.catConsumer.dto;

import ru.komarov.lab5.catConsumer.entities.enums.CatBreed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindCatsByBreedRequestDto {
    private CatBreed catBreed;
}
