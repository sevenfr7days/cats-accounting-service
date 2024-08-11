package ru.komarov.lab5.ownerConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import ru.komarov.lab5.ownerConsumer.services.OwnerService;

public class FindAllHandler extends HandlerBase{
    public FindAllHandler(OwnerService ownerService){
        super(ownerService);
    }
    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "find-all";
        if(key.equals(name)){
            var response = this.ownerService.findAll();
            return objectMapper.writeValueAsString(response);
        }
        return next.handle(key, value);
    }
}
