package com.novust.shared.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novust.shared.time.TimeType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Action {
    public String actionDescription;
    protected TimeType actionTimeType;
    private Action relativeTo; // if timeType is relativeTo something
    protected long actionTime; // seconds offset
    protected long actionDuration; // how long this thing takes.

    public Action() {
    }

    public Action(String description, long actionTime) {
        this.actionDescription = description;
        this.actionTimeType = TimeType.ABSOLUTE;
        this.actionTime = actionTime;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
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
