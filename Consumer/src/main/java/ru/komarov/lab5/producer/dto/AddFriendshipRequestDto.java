package ru.komarov.lab5.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendshipRequestDto {
    private long cat1Id;
    private long cat2Id;
}
