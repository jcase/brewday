package com.novust.shared.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novust.shared.data.VerbData;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcedureAction extends Action {
    String description;

    public ProcedureAction() {
    }

    public ProcedureAction(VerbData verb, String description, long timeOffset) {
        super(verb, timeOffset);
        this.description = description;
    }
}
