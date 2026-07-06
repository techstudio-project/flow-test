package com.example.flow.model;

import java.util.ArrayList;
import java.util.List;

public class ProcessDefinition {
    private String code;
    private String name;
    private List<FlowStep> flow = new ArrayList<>();
    private List<NodeDefinition> nodes = new ArrayList<>();

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<FlowStep> getFlow() { return flow; }
    public void setFlow(List<FlowStep> flow) { this.flow = flow; }
    public List<NodeDefinition> getNodes() { return nodes; }
    public void setNodes(List<NodeDefinition> nodes) { this.nodes = nodes; }
}
