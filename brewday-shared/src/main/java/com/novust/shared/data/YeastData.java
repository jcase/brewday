package com.novust.shared.data;

public class YeastData implements AppData {
    public String name;
    public int flocculation;

    @Override
    public String toString() {
        return "YeastData{" +
                "name='" + name + '\'' +
                ", flocculation=" + flocculation +
                '}';
    }
}
