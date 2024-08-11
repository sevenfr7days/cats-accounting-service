package ru.komarov.lab5.catConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import ru.komarov.lab5.catConsumer.services.CatService;

public class FindAllCatsHandler extends HandlerBase{
    public FindAllCatsHandler(CatService catService){
        super(catService);
    }

    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "find-all";
        if(key.equals(name)){
            var response = this.catService.findAllCats();

            return objectMapper.writeValueAsString(response);
        }

        return next.handle(key, value);
    }
}
