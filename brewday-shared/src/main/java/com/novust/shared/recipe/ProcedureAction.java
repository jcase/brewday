package com.novust.shared.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcedureAction extends Action {
    public ProcedureAction() {
    }

    public ProcedureAction(String verb, long timeOffset) {
        super(verb, timeOffset);
    }
}
