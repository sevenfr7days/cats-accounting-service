package ru.komarov.lab5.catConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.komarov.lab5.catConsumer.dto.FindCatsByBreedRequestDto;
import lombok.SneakyThrows;
import ru.komarov.lab5.catConsumer.services.CatService;

public class FindCatsByBreedHandler extends HandlerBase{

    public FindCatsByBreedHandler(CatService catService){
        super(catService);
    }

    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "find-by-breed";
        if(key.equals(name)){
            var request = objectMapper.readValue(value, FindCatsByBreedRequestDto.class);

            var response = this.catService.findCatsByBreed(request.getCatBreed());
            return objectMapper.writeValueAsString(response);
        }

        return next.handle(key, value);
    }
}
