package com.novust.shared.data;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hopData")
public class HopData implements AppData {
    @Id
    public String id;
    public String name;
    public String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
