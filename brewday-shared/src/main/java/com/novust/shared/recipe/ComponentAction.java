package com.novust.shared.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novust.shared.data.VerbData;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponentAction extends Action {
    private Component component;

    public ComponentAction() {
        super();
    }

    public ComponentAction(VerbData verb, Component component, long actionTime) {
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
