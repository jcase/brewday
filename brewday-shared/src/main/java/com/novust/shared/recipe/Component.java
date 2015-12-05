package com.novust.shared.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by johncase on 12/4/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Component {
    private String name;
    private int amount;
    private String measurementUnits;

    public Component() {
    }

    public Component(String name, int amount, String measurementUnits) {
        this.name = name;
        this.amount = amount;
        this.measurementUnits = measurementUnits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMeasurementUnits() {
        return measurementUnits;
    }

    public void setMeasurementUnits(String measurementUnits) {
        this.measurementUnits = measurementUnits;
    }
}
