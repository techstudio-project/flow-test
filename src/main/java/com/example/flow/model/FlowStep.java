package com.example.flow.model;

public class FlowStep {
    private String id;
    private Boolean hasCondition = false;
    private String to;
    private String condition; // Spring bean name for business branch decision.

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Boolean getHasCondition() { return hasCondition; }
    public void setHasCondition(Boolean hasCondition) { this.hasCondition = hasCondition; }
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
}
