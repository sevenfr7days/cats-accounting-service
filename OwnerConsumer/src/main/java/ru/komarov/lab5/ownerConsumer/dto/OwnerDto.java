package ru.komarov.lab5.ownerConsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    private long id;
    private String name;
    private Calendar birthdayDate;
}

