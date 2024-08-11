package ru.komarov.lab5.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.komarov.lab5.producer.dto.OwnerDto;
import ru.komarov.lab5.producer.entity.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.komarov.lab5.producer.service.OwnerKafkaProducer;
import ru.komarov.lab5.producer.service.UserService;

import java.security.Principal;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path ="/owners")
public class OwnerController {

    private final OwnerKafkaProducer ownerKafkaProducer;
    private final UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();

    //approved
    @PostMapping("/create")
    public ResponseEntity<OwnerDto> create(@RequestBody OwnerDto OwnerDto, Principal principal) throws JsonProcessingException {
        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN) {
            String json = objectMapper.writeValueAsString(OwnerDto);
            var response = ownerKafkaProducer.kafkaRequestReply("add", json).toString();

            return new ResponseEntity<>(objectMapper.readValue(response, OwnerDto.class), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    //approved
    @GetMapping("/get/{id}")
    public ResponseEntity<OwnerDto> getById(@PathVariable Long id, Principal principal) throws JsonProcessingException {
        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN){
            String json = objectMapper.writeValueAsString(id);
            var response = ownerKafkaProducer.kafkaRequestReply("find", json).toString();

            return new ResponseEntity<>(objectMapper.readValue(response, OwnerDto.class), HttpStatus.OK);
        }
        else if(userService.findByUsername(principal.getName()).getOwner().getId() == id){
            String json = objectMapper.writeValueAsString(id);
            var response = ownerKafkaProducer.kafkaRequestReply("find", json).toString();

            return new ResponseEntity<>(objectMapper.readValue(response, OwnerDto.class), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    //approved
    @GetMapping
    public ResponseEntity<List<OwnerDto>> findAll(Principal principal) throws JsonProcessingException {
        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN){
            var reply = ownerKafkaProducer.kafkaRequestReply("find-all", "").toString();
            List<OwnerDto> ownerDtos = objectMapper.readValue(reply, new TypeReference<>() {});

            return new ResponseEntity<>(ownerDtos, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    //approved
    @PutMapping("/update")
    public ResponseEntity<OwnerDto> update(@RequestBody OwnerDto OwnerDto, Principal principal) throws JsonProcessingException {
        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN) {
            String json = objectMapper.writeValueAsString(OwnerDto);
            var reply = ownerKafkaProducer.kafkaRequestReply("update", json).toString();
            return new ResponseEntity<>(objectMapper.readValue(reply, OwnerDto.class), HttpStatus.OK);
        }
        else if(userService.findByUsername(principal.getName()).getOwner().getId() == OwnerDto.getId()){
            String json = objectMapper.writeValueAsString(OwnerDto);
            var reply = ownerKafkaProducer.kafkaRequestReply("update", json).toString();
            return new ResponseEntity<>(objectMapper.readValue(reply, OwnerDto.class), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    //approved
    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable long id, Principal principal) throws JsonProcessingException {
        if(userService.findByUsername(principal.getName()).getRole().getName() == UserRole.ROLE_ADMIN){
            String json = objectMapper.writeValueAsString(id);
            ownerKafkaProducer.kafkaRequestReply("delete", json);
            return HttpStatus.OK;
        }
        else if(userService.findByUsername(principal.getName()).getOwner().getId() == id){
            String json = objectMapper.writeValueAsString(id);
            ownerKafkaProducer.kafkaRequestReply("delete", json);
            return HttpStatus.OK;
        }
        return HttpStatus.FORBIDDEN;
    }
}