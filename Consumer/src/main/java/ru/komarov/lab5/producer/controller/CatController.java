package ru.komarov.lab5.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.komarov.lab5.producer.dto.AddFriendshipRequestDto;
import ru.komarov.lab5.producer.dto.CatDto;
import ru.komarov.lab5.producer.dto.FindCatByColorRequestDto;
import ru.komarov.lab5.producer.dto.FindCatsByBreedRequestDto;
import ru.komarov.lab5.producer.entity.enums.CatBreed;
import ru.komarov.lab5.producer.entity.enums.CatColor;
import ru.komarov.lab5.producer.entity.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.komarov.lab5.producer.service.CatKafkaProducer;
import ru.komarov.lab5.producer.service.CatService;
import ru.komarov.lab5.producer.service.UserService;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/cats")
public class CatController {

    private final CatKafkaProducer catKafkaProducer;
    private final UserService userService;
    private final CatService catService;
    ObjectMapper objectMapper = new ObjectMapper();

    //approved
    @PostMapping("/create")
    public ResponseEntity<CatDto> create(@RequestBody CatDto CatDto, Principal principal) throws JsonProcessingException {

        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN){
            String json = objectMapper.writeValueAsString(CatDto);
            var reply = catKafkaProducer.kafkaRequestReply("add", json).toString();

            return new ResponseEntity<>(objectMapper.readValue(reply, CatDto.class), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    //approved
    @GetMapping
    public ResponseEntity<List<CatDto>> findAll(Principal principal) throws JsonProcessingException {
        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN){
            var reply = catKafkaProducer.kafkaRequestReply("find-all", "").toString();
            List<CatDto> catDtos = objectMapper.readValue(reply, new TypeReference<>() {});
            return new ResponseEntity<>(catDtos, HttpStatus.OK);
        }
        else{
            var reply = catKafkaProducer.kafkaRequestReply("find-all", "").toString();
            List<CatDto> catDtos = objectMapper.readValue(reply, new TypeReference<>() {});
            List<CatDto> catsDtosReply = new ArrayList<>();

            for(var cat : catDtos){
                if(catService.isCatUser(principal.getName(), cat.getId())){
                    catsDtosReply.add(cat);
                }
            }
            return new ResponseEntity<>(catsDtosReply, HttpStatus.OK);
        }
    }

    //approved
    @GetMapping("/id/{id}")
    public ResponseEntity<CatDto> getById(@PathVariable Long id, Principal principal) throws JsonProcessingException {
        if(catService.isCatUser(principal.getName(), id)){
            String json = objectMapper.writeValueAsString(id);
            var reply = catKafkaProducer.kafkaRequestReply("find-by-id", json).toString();

            return new ResponseEntity<>(objectMapper.readValue(reply, CatDto.class), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    //approved
    @GetMapping("/color/{color}")
    public ResponseEntity<List<CatDto>> getByColor(@PathVariable String color, Principal principal) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(new FindCatByColorRequestDto(CatColor.valueOf(color.toUpperCase())));

        if (userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN) {
            var reply = catKafkaProducer.kafkaRequestReply("find-by-color", json).toString();
            List<CatDto> catDtos = objectMapper.readValue(reply, new TypeReference<>() {});
            return new ResponseEntity<>(catDtos, HttpStatus.OK);
        } else {
            var reply = catKafkaProducer.kafkaRequestReply("find-by-color", json).toString();
            List<CatDto> catDtos = objectMapper.readValue(reply, new TypeReference<>() {});
            List<CatDto> catsDtosReply = new ArrayList<>();
            for (var cat : catDtos) {
                if (catService.isCatUser(principal.getName(), cat.getId())) {
                    catsDtosReply.add(cat);
                }
            }
            return new ResponseEntity<>(catsDtosReply, HttpStatus.OK);
        }
    }


    //approved
    @GetMapping("/breed/{breed}")
    public ResponseEntity<List<CatDto>> getByBreed(@PathVariable String breed, Principal principal) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(new FindCatsByBreedRequestDto(CatBreed.valueOf(breed.toUpperCase())));

        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN){
            var reply = catKafkaProducer.kafkaRequestReply("find-by-breed", json).toString();
            List<CatDto> catDtos = objectMapper.readValue(reply, new TypeReference<>() {});
            return new ResponseEntity<>(catDtos, HttpStatus.OK);
        }
        else {
            var reply = catKafkaProducer.kafkaRequestReply("find-by-breed", json).toString();
            List<CatDto> catDtos = objectMapper.readValue(reply, new TypeReference<>() {});
            List<CatDto> catsDtosReply = new ArrayList<>();
            for (var cat : catDtos) {
                if (catService.isCatUser(principal.getName(), cat.getId())) {
                    catsDtosReply.add(cat);
                }
            }
            return new ResponseEntity<>(catsDtosReply, HttpStatus.OK);
        }
    }

    //approved
    @GetMapping("/friendship/{id}")
    public ResponseEntity<List<CatDto>> getFriend(@PathVariable long id, Principal principal) throws JsonProcessingException {
        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN){
            String json = objectMapper.writeValueAsString(id);
            var reply = catKafkaProducer.kafkaRequestReply("find-friends", json).toString();
            List<CatDto> catDtos = objectMapper.readValue(reply, new TypeReference<>() {});
            return new ResponseEntity<>(catDtos, HttpStatus.OK);
        }
        else if(catService.isCatUser(principal.getName(), id)){
            String json = objectMapper.writeValueAsString(id);
            var reply = catKafkaProducer.kafkaRequestReply("find-friends", json).toString();
            List<CatDto> catDtos = objectMapper.readValue(reply, new TypeReference<>() {});
            return new ResponseEntity<>(catDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    //approved
    @GetMapping("ownerId/{ownerId}")
    public ResponseEntity<List<CatDto>> getByOwnerId(@PathVariable long ownerId, Principal principal) throws JsonProcessingException {
        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN){
            String json = objectMapper.writeValueAsString(ownerId);
            var reply = catKafkaProducer.kafkaRequestReply("find-by-owner", json).toString();
            List<CatDto> catDtos = objectMapper.readValue(reply, new TypeReference<>() {});
            return new ResponseEntity<>(catDtos, HttpStatus.OK);
        }
        else if(userService.findByUsername(principal.getName()).getOwner().getId() == ownerId){
            String json = objectMapper.writeValueAsString(ownerId);
            var reply = catKafkaProducer.kafkaRequestReply("find-by-owner", json).toString();
            List<CatDto> catDtos = objectMapper.readValue(reply, new TypeReference<>() {});
            return new ResponseEntity<>(catDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    //approved
    @PostMapping("/friendships")
    public HttpStatus postFriendship(@RequestParam long id, @RequestParam long cat1Id, @RequestParam long cat2Id, Principal principal) throws JsonProcessingException {
        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN) {
            var addFriendshipRequestDto = new AddFriendshipRequestDto(cat1Id, cat2Id);
            String json = objectMapper.writeValueAsString(addFriendshipRequestDto);
            catKafkaProducer.kafkaRequestReply("add-friendship", json);

            return HttpStatus.OK;
        }
        else if(catService.isCatUser(principal.getName(), cat1Id) || catService.isCatUser(principal.getName(), cat2Id)){
            var addFriendshipRequestDto = new AddFriendshipRequestDto(cat1Id, cat2Id);
            String json = objectMapper.writeValueAsString(addFriendshipRequestDto);
            catKafkaProducer.kafkaRequestReply("add-friendship", json);

            return HttpStatus.OK;
        }
        return HttpStatus.FORBIDDEN;
    }

    //approved
    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id, Principal principal) throws JsonProcessingException {
        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN){
            String json = objectMapper.writeValueAsString(id);
            catKafkaProducer.kafkaRequestReply("delete", json);

            return HttpStatus.OK;
        }
        else if(catService.findCatById(id).getOwnerId() == userService.findByUsername(principal.getName()).getOwner().getId()){
            String json = objectMapper.writeValueAsString(id);
            catKafkaProducer.kafkaRequestReply("delete", json);

            return HttpStatus.OK;
        }
        return HttpStatus.FORBIDDEN;
    }

    //approved
    @PutMapping("/update")
    public ResponseEntity<CatDto> update(@RequestBody CatDto CatDto, Principal principal) throws JsonProcessingException {
        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN){
            String json = objectMapper.writeValueAsString(CatDto);
            String reply = catKafkaProducer.kafkaRequestReply("update", json).toString();

            return new ResponseEntity<>(objectMapper.readValue(reply, CatDto.class), HttpStatus.OK);
        }
        else if(catService.findCatById(CatDto.getId()).getOwnerId() == userService.findByUsername(principal.getName()).getOwner().getId()){
            String json = objectMapper.writeValueAsString(CatDto);
            String reply = catKafkaProducer.kafkaRequestReply("update", json).toString();

            return new ResponseEntity<>(objectMapper.readValue(reply, CatDto.class), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
