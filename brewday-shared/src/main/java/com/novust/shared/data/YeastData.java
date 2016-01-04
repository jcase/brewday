package com.novust.shared.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "yeastData")
public class YeastData implements AppData {
    @Id
    public String id;
    public String name;
    public int flocculation;

    @Override
    public String toString() {
        return "YeastData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", flocculation=" + flocculation +
                '}';
    }
}
