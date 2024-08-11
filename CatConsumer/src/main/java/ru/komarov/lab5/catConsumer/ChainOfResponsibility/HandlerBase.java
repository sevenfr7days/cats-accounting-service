package ru.komarov.lab5.catConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.komarov.lab5.catConsumer.services.CatService;

public abstract class HandlerBase {
    protected CatService catService;
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected HandlerBase next = null;

    public HandlerBase(CatService catService) {
        this.catService = catService;
    }

    public void addNext(HandlerBase next){
        if(this.next == null){
            this.next = next;
            return;
        }
        this.next.addNext(next);
    }

    public abstract String handle(String key, String value) throws JsonProcessingException;
}
