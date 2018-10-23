package com.novust.shared.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novust.shared.data.VerbData;
import com.novust.shared.time.TimeType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Action {
    public VerbData verb;
    protected TimeType actionTimeType;
    private Action relativeTo; // if timeType is relativeTo something
    protected long actionTime; // seconds offset
    protected long actionDuration; // how long this thing takes in seconds

    public Action() {
    }

    public Action(VerbData verb, long actionTime) {
        this.verb = verb;
        this.actionTimeType = TimeType.ABSOLUTE;
        this.actionTime = actionTime;
    }

    public VerbData getVerb() {
        return verb;
    }

    public void setVerb(VerbData verb) {
        this.verb = verb;
    }

    public TimeType getActionTimeType() {
        return actionTimeType;
    }

    public void setActionTimeType(TimeType actionTimeType) {
        this.actionTimeType = actionTimeType;
    }

    public Action getRelativeTo() {
        return relativeTo;
    }

    public void setRelativeTo(Action relativeTo) {
        this.relativeTo = relativeTo;
    }

    public long getActionTime() {
        return actionTime;
    }

    public void setActionTime(long actionTime) {
        this.actionTime = actionTime;
    }

    public long getActionDuration() {
        return actionDuration;
    }

    public void setActionDuration(long actionDuration) {
        this.actionDuration = actionDuration;
    }
}
