package ru.komarov.lab5.ownerConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.komarov.lab5.ownerConsumer.dto.FindOwnerRequestDto;
import lombok.SneakyThrows;
import ru.komarov.lab5.ownerConsumer.services.OwnerService;

public class FindOwnerHandler extends HandlerBase{

    public FindOwnerHandler(OwnerService ownerService){
        super(ownerService);
    }
    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "find";
        if(key.equals(name)){
            var request = objectMapper.readValue(value, FindOwnerRequestDto.class);
            var response = this.ownerService.findOwnerById(request.getId());

            return objectMapper.writeValueAsString(response);
        }

        return next.handle(key, value);
    }
}
