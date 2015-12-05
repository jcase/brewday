package com.novust.shared.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Recipe {
    private String key;
    private String name;
    private String description;
    private Collection<Action> actions;

    public Recipe() {
        actions = newArrayList();
    }

    public Recipe(String key, String name, String description) {
        this();
        this.key = key;
        this.name = name;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Action> getActions() {
        return actions;
    }

    public void setActions(Collection<Action> actions) {
        this.actions = actions;
    }

    public void addAction(Action action) {
        actions.add(action);
    }
}
