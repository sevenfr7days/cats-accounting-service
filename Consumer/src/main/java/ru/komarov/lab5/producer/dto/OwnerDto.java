package ru.komarov.lab5.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto {
    private long id;
    private String name;
    private Calendar birthdayDate;
}

