package ru.komarov.lab5.catConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.komarov.lab5.catConsumer.dto.FindFriendsRequestDto;
import lombok.SneakyThrows;
import ru.komarov.lab5.catConsumer.services.CatService;

public class FindFriendsHandler extends HandlerBase{

    public FindFriendsHandler(CatService catService){
        super(catService);
    }

    @Override
    public String handle(String key, String value) throws JsonProcessingException {
        String name = "find-friends";
        if(key.equals(name)){
            var request = objectMapper.readValue(value, FindFriendsRequestDto.class);

            var response = this.catService.findFriendsByCatId(request.getId());
            return objectMapper.writeValueAsString(response);
        }

        return next.handle(key, value);
    }
}
