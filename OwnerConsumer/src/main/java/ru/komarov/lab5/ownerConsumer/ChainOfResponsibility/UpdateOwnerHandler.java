package ru.komarov.lab5.ownerConsumer.ChainOfResponsibility;

import ru.komarov.lab5.ownerConsumer.dto.OwnerDto;
import lombok.SneakyThrows;
import ru.komarov.lab5.ownerConsumer.services.OwnerService;

public class UpdateOwnerHandler extends HandlerBase{
    public UpdateOwnerHandler(OwnerService ownerService){
        super(ownerService);
    }
    @SneakyThrows
    @Override
    public String handle(String key, String value) {
        String name = "update";
        if(key.equals(name)){
            var request = objectMapper.readValue(value, OwnerDto.class);
            var response = this.ownerService.updateOwner(request);

            return objectMapper.writeValueAsString(response);
        }

        return next.handle(key, value);
    }
}
