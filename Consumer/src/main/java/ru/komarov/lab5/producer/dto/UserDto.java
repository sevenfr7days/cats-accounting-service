package ru.komarov.lab5.producer.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.komarov.lab5.producer.entity.Role;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    private long ownerId;
    private String password;
    private String username;
    private Role role;
}
