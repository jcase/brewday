package com.novust.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperSource {
    ObjectMapper objectMapper;

    public ObjectMapperSource() {
        objectMapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
