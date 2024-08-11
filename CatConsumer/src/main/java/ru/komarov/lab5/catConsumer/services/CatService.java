package ru.komarov.lab5.catConsumer.services;

import ru.komarov.lab5.catConsumer.dto.CatDto;
import ru.komarov.lab5.catConsumer.entities.Cat;
import ru.komarov.lab5.catConsumer.entities.Friendship;
import ru.komarov.lab5.catConsumer.entities.enums.CatBreed;
import ru.komarov.lab5.catConsumer.entities.enums.CatColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komarov.lab5.catConsumer.repositories.CatRepository;
import ru.komarov.lab5.catConsumer.repositories.FriendshipRepository;
import ru.komarov.lab5.catConsumer.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CatService {
    private final CatRepository catRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public Collection<CatDto> findAllCats(){
        var cats = catRepository.findAll();
        List<CatDto> catDtos = new ArrayList<>();
        for(var cat : cats){
            var catDto = CatDto.builder()
                    .id(cat.getId())
                    .name(cat.getName())
                    .birthdayDate(cat.getBirthdayDate())
                    .color(cat.getColor())
                    .breed(cat.getBreed())
                    .ownerId(cat.getOwnerId())
                    .build();
            catDtos.add(catDto);
        }
        return catDtos;
    }

    public CatDto addCat(CatDto CatDto){
        Cat cat = Cat.builder()
                .name(CatDto.getName())
                .breed(CatDto.getBreed())
                .color(CatDto.getColor())
                .birthdayDate(CatDto.getBirthdayDate())
                .id(CatDto.getId())
                .ownerId(CatDto.getOwnerId()).build();
        Cat requestCat = catRepository.save(cat);
        return ru.komarov.lab5.catConsumer.dto.CatDto.builder()
                .id(requestCat.getId())
                .name(requestCat.getName())
                .birthdayDate(requestCat.getBirthdayDate())
                .color(requestCat.getColor())
                .breed(requestCat.getBreed())
                .ownerId(requestCat.getOwnerId())
                .build();
    }

    public void deleteCatById(Long id){
        Optional<Cat> cat = catRepository.findById(id);

        cat.ifPresent(catRepository::delete);
    }

    public CatDto findCatById(Long id){
        Optional<Cat> requestCat = catRepository.findById(id);
        return requestCat.map(cat -> CatDto.builder()
                .id(cat.getId())
                .name(cat.getName())
                .birthdayDate(cat.getBirthdayDate())
                .color(cat.getColor())
                .breed(cat.getBreed())
                .ownerId(cat.getOwnerId())
                .build()).orElse(null);
    }

    public CatDto updateCat(CatDto CatDto){
        Optional<Cat> optionalCat = catRepository.findById(CatDto.getId());
        if(optionalCat.isPresent()){
            Cat updatedCat = optionalCat.get();
            updatedCat.setBirthdayDate(CatDto.getBirthdayDate());
            updatedCat.setColor(CatDto.getColor());
            updatedCat.setBreed(CatDto.getBreed());
            updatedCat.setName(CatDto.getName());
            updatedCat.setOwnerId(CatDto.getOwnerId());

            var responseCat = catRepository.save(updatedCat);
            return ru.komarov.lab5.catConsumer.dto.CatDto.builder()
                    .id(responseCat.getId())
                    .name(responseCat.getName())
                    .birthdayDate(responseCat.getBirthdayDate())
                    .color(responseCat.getColor())
                    .breed(responseCat.getBreed())
                    .ownerId(responseCat.getOwnerId())
                    .build();
        }
        return null;
    }

    public Collection<CatDto> findCatsByColor(CatColor color){
        List<Cat> allCats = catRepository.findAll();
        List<CatDto> requestedCats = new ArrayList<>();
        for(var cat : allCats){
            if(cat.getColor() == color){
                var catDto = CatDto.builder()
                        .id(cat.getId())
                        .name(cat.getName())
                        .birthdayDate(cat.getBirthdayDate())
                        .color(cat.getColor())
                        .breed(cat.getBreed())
                        .ownerId(cat.getOwnerId())
                        .build();
                requestedCats.add(catDto);
            }
        }
        return requestedCats;
    }

    public Collection<CatDto> findCatsByBreed(CatBreed breed){
        List<Cat> allCats = catRepository.findAll();
        List<CatDto> requestedCats = new ArrayList<>();
        for(var cat : allCats){
            if(cat.getBreed() == breed){
                var catDto = CatDto.builder()
                        .id(cat.getId())
                        .name(cat.getName())
                        .birthdayDate(cat.getBirthdayDate())
                        .color(cat.getColor())
                        .breed(cat.getBreed())
                        .ownerId(cat.getOwnerId())
                        .build();
                requestedCats.add(catDto);
            }
        }
        return requestedCats;
    }

    public Collection<CatDto> findCatsByOwnerId(long ownerId){
        List<Cat> allCats = catRepository.findAll();
        List<CatDto> requestedCats = new ArrayList<>();
        for(var cat : allCats){
            if(cat.getOwnerId() == ownerId){
                var catDto = CatDto.builder()
                        .id(cat.getId())
                        .name(cat.getName())
                        .birthdayDate(cat.getBirthdayDate())
                        .color(cat.getColor())
                        .breed(cat.getBreed())
                        .ownerId(cat.getOwnerId())
                        .build();
                requestedCats.add(catDto);
            }
        }
        return requestedCats;
    }

    public boolean isCatUser(String username, long catId){
        var optionalCat = catRepository.findById(catId);
        var optionalUser = userRepository.findUserByUsername(username);
        if(optionalUser.isPresent() && optionalCat.isPresent()){
            return optionalUser.get().getOwner().getId() == optionalCat.get().getOwnerId();
        }
        return false;
    }

    public Collection<CatDto> findFriendsByCatId(long catId) {
        Collection<Friendship> friendships = friendshipRepository.findFriendshipByCat1Id(catId);
        List<Long> friendsIds = friendships.stream()
                .flatMap(f -> Stream.of(f.getCat1Id(), f.getCat2Id()))
                .distinct()
                .filter(id -> id != catId)
                .toList();
        List<CatDto> friends = new ArrayList<>();
        for(var friendId : friendsIds){
            Optional<Cat> optionalCat = catRepository.findById(friendId);
            if(optionalCat.isPresent()){
                var cat = optionalCat.get();
                var catDto = CatDto.builder()
                        .id(cat.getId())
                        .name(cat.getName())
                        .birthdayDate(cat.getBirthdayDate())
                        .color(cat.getColor())
                        .breed(cat.getBreed())
                        .ownerId(cat.getOwnerId())
                        .build();
                friends.add(catDto);
            }
        }
        return friends;
    }

    public void makeFriendship(long id, long cat1Id, long cat2Id){
        friendshipRepository.save(new Friendship(id, cat1Id, cat2Id));
        friendshipRepository.save(new Friendship(id+1, cat2Id, cat1Id));
    }
}
