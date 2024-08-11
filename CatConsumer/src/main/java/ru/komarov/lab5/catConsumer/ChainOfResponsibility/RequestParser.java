package ru.komarov.lab5.catConsumer.ChainOfResponsibility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class RequestParser {
    @SneakyThrows
    public static String getRequest(Object request) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(request.toString());
        return actualObj.toString();
    }
}
