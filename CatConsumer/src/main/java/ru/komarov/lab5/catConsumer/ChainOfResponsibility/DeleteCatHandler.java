package ru.komarov.lab5.catConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.komarov.lab5.catConsumer.dto.DeleteCatDtoKafka;
import lombok.SneakyThrows;
import ru.komarov.lab5.catConsumer.services.CatService;

public class DeleteCatHandler extends HandlerBase{

    public DeleteCatHandler(CatService catService){
        super(catService);
    }

    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "delete";
        if(key.equals(name)){
            var request = objectMapper.readValue(value, DeleteCatDtoKafka.class);
            this.catService.deleteCatById(request.getId());

            return "";
        }

        return next.handle(key, value);
    }
}
