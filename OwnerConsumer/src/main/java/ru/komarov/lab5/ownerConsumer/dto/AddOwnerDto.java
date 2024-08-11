package ru.komarov.lab5.ownerConsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddOwnerDto {
    private long id;
    private String name;
    private Calendar birthdayDate;
}
