package ru.komarov.lab5.catConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.komarov.lab5.catConsumer.dto.FindCatDtoKafka;
import lombok.SneakyThrows;
import ru.komarov.lab5.catConsumer.services.CatService;

public class FindCatHandler extends HandlerBase{

    public FindCatHandler(CatService catService){
        super(catService);
    }

    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "find-by-id";
        if(key.equals(name)){
            var request = objectMapper.readValue(value, FindCatDtoKafka.class);

            var response = this.catService.findCatById(request.getId());
            return objectMapper.writeValueAsString(response);
        }

        return next.handle(key, value);
    }
}
