package ru.komarov.lab5.ownerConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.komarov.lab5.ownerConsumer.dto.OwnerDto;
import lombok.SneakyThrows;
import ru.komarov.lab5.ownerConsumer.services.OwnerService;

public class AddOwnerHandler extends HandlerBase{

    public AddOwnerHandler(OwnerService ownerService){
        super(ownerService);
    }
    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "add";
        if(key.equals(name)){
            var request = objectMapper.readValue(value, OwnerDto.class);
            var response = this.ownerService.addOwner(request);

            return objectMapper.writeValueAsString(response);
        }
        return next.handle(key, value);
    }
}
