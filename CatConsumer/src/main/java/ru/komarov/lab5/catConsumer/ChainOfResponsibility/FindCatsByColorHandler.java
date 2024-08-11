package ru.komarov.lab5.catConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.komarov.lab5.catConsumer.dto.FindCatByColorRequestDto;
import lombok.SneakyThrows;
import ru.komarov.lab5.catConsumer.services.CatService;

public class FindCatsByColorHandler extends HandlerBase {

    public FindCatsByColorHandler(CatService catService) {
        super(catService);
    }

    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "find-by-color";
        if (key.equals(name)) {
            FindCatByColorRequestDto request = objectMapper.readValue(value, FindCatByColorRequestDto.class);
            var response = this.catService.findCatsByColor(request.getColor());
            return objectMapper.writeValueAsString(response);
        }
        return next.handle(key, value);
    }
}

