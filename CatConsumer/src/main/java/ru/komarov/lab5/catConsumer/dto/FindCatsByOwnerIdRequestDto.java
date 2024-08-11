package ru.komarov.lab5.catConsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindCatsByOwnerIdRequestDto {
    private long ownerId;
}
