package com.example.flow.model;

import java.util.ArrayList;
import java.util.List;

public class NodeDefinition {
    private String id;
    private String name;
    private List<ApproverDefinition> approver = new ArrayList<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<ApproverDefinition> getApprover() { return approver; }
    public void setApprover(List<ApproverDefinition> approver) { this.approver = approver; }
}
