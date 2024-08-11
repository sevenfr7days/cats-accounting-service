package ru.komarov.lab5.ownerConsumer.services;

import ru.komarov.lab5.ownerConsumer.dto.OwnerDto;
import ru.komarov.lab5.ownerConsumer.entities.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komarov.lab5.ownerConsumer.repositories.OwnerRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerDto findOwnerById(Long id){
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        if(optionalOwner.isPresent()){
            var owner = optionalOwner.get();
            return OwnerDto.builder()
                    .id(owner.getId())
                    .name(owner.getName())
                    .birthdayDate(owner.getBirthdayDate())
                    .build();
        }
        return null;
    }

    public OwnerDto addOwner(OwnerDto OwnerDto){
        Owner owner = Owner.builder()
                .name(OwnerDto.getName())
                .birthdayDate(OwnerDto.getBirthdayDate())
                .id(OwnerDto.getId()).build();

        var responseOwner = ownerRepository.save(owner);
        return ru.komarov.lab5.ownerConsumer.dto.OwnerDto.builder()
                .id(responseOwner.getId())
                .name(responseOwner.getName())
                .birthdayDate(responseOwner.getBirthdayDate())
                .build();
    }

    public void deleteOwnerById(Long id){
        Optional<Owner> owner = ownerRepository.findById(id);

        owner.ifPresent(ownerRepository::delete);
    }

    public Collection<OwnerDto> findAll(){
        var owners = ownerRepository.findAll();
        List<OwnerDto> ownerDtos = new ArrayList<>();
        for(var owner : owners){
            var ownerDto = OwnerDto.builder()
                    .id(owner.getId())
                    .name(owner.getName())
                    .birthdayDate(owner.getBirthdayDate())
                    .build();
            ownerDtos.add(ownerDto);
        }
        return ownerDtos;
    }

    public OwnerDto updateOwner(OwnerDto OwnerDto) {
        Optional<Owner> optional_owner = ownerRepository.findById(OwnerDto.getId());

        if(optional_owner.isPresent()){
            Owner old_owner = optional_owner.get();
            old_owner.setBirthdayDate(OwnerDto.getBirthdayDate());
            old_owner.setName(OwnerDto.getName());

            Owner updatedOwner = ownerRepository.save(old_owner);

            return ru.komarov.lab5.ownerConsumer.dto.OwnerDto.builder()
                    .id(updatedOwner.getId())
                    .name(updatedOwner.getName())
                    .birthdayDate(updatedOwner.getBirthdayDate())
                    .build();
        }
        return null;
    }
}

