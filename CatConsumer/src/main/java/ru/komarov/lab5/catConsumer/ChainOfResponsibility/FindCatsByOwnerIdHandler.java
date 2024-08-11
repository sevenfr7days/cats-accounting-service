package ru.komarov.lab5.catConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.komarov.lab5.catConsumer.dto.FindCatsByOwnerIdRequestDto;
import lombok.SneakyThrows;
import ru.komarov.lab5.catConsumer.services.CatService;

public class FindCatsByOwnerIdHandler extends HandlerBase{
    public FindCatsByOwnerIdHandler(CatService catService){
        super(catService);
    }

    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "find-by-owner";
        if(key.equals(name)){
            var request = objectMapper.readValue(value, FindCatsByOwnerIdRequestDto.class);

            var response = this.catService.findCatsByOwnerId(request.getOwnerId());
            return objectMapper.writeValueAsString(response);
        }
        return next.handle(key, value);
    }
}
