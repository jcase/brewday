package com.novust.shared.data;


import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class HopData {

    public List<String> getHopVarieties() {
        return Arrays.asList("Cascade","Chinook","Fuggle");
    }

}
