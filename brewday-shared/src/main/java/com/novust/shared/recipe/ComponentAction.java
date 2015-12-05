package com.novust.shared.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponentAction extends Action {
    private Component component;

    public ComponentAction() {
        super();
    }

    public ComponentAction(String verb, Component component, long actionTime) {
        super(verb, actionTime);
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
