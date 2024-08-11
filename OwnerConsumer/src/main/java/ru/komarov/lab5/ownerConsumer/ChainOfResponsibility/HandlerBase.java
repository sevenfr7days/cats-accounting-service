package ru.komarov.lab5.ownerConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.komarov.lab5.ownerConsumer.services.OwnerService;

public abstract class HandlerBase {
    protected OwnerService ownerService;
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected HandlerBase next = null;

    public HandlerBase(OwnerService ownerService) {
        this.ownerService = ownerService;
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
