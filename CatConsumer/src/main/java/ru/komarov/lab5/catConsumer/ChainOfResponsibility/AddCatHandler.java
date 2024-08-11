package ru.komarov.lab5.catConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.komarov.lab5.catConsumer.dto.CatDto;
import ru.komarov.lab5.catConsumer.services.CatService;

public class AddCatHandler extends HandlerBase{
    public AddCatHandler(CatService catService){
        super(catService);
    }
    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "add";
        if(key.equals(name)){
            CatDto request = objectMapper.readValue(value, CatDto.class);

            var catResponse = new CatDto(this.catService.addCat(request));
            return objectMapper.writeValueAsString(catResponse);
        }

        return next.handle(key, value);
    }
}
