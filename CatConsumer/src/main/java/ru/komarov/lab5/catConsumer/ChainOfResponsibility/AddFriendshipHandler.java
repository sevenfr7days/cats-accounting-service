package ru.komarov.lab5.catConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.komarov.lab5.catConsumer.dto.AddFriendshipRequestDto;
import lombok.SneakyThrows;
import ru.komarov.lab5.catConsumer.services.CatService;

public class AddFriendshipHandler extends HandlerBase{

    public AddFriendshipHandler(CatService catService){
        super(catService);
    }

    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "add-friendship";
        if(key.equals(name)){
            var request = objectMapper.readValue(value, AddFriendshipRequestDto.class);
            this.catService.makeFriendship(request.getId(), request.getCat1Id(), request.getCat2Id());
            return "";
        }

        return next.handle(key, value);
    }
}
