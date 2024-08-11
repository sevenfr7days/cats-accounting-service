package ru.komarov.lab5.ownerConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.komarov.lab5.ownerConsumer.dto.DeleteOwnerDto;
import lombok.SneakyThrows;
import ru.komarov.lab5.ownerConsumer.services.OwnerService;

public class DeleteOwnerHandler extends HandlerBase{

    public DeleteOwnerHandler(OwnerService ownerService){
        super(ownerService);
    }
    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "delete";
        if(key.equals(name)){
            var request = objectMapper.readValue(value, DeleteOwnerDto.class);
            this.ownerService.deleteOwnerById(request.getId());
            return "";
        }

        return next.handle(key, value);
    }
}
