package com.example.flow.engine;

import java.util.HashMap;
import java.util.Map;

public class ApprovalCommand {
    private String approver;
    private String comment;
    private Map<String, Object> variables = new HashMap<>();

    public String getApprover() { return approver; }
    public void setApprover(String approver) { this.approver = approver; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Map<String, Object> getVariables() { return variables; }
    public void setVariables(Map<String, Object> variables) { this.variables = variables; }
}
