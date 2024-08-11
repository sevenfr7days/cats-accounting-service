package ru.komarov.lab5.catConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.komarov.lab5.catConsumer.dto.CatDto;
import ru.komarov.lab5.catConsumer.services.CatService;

public class UpdateCatHandler extends HandlerBase{
    public UpdateCatHandler(CatService catService){
        super(catService);
    }

    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "update";
        if(key.equals(name)){
            var request = objectMapper.readValue(value, CatDto.class);

            var response = this.catService.updateCat(request);
            return objectMapper.writeValueAsString(response);
        }

        return next.handle(key, value);
    }
}
