package com.example.flow.engine;

import java.util.HashMap;
import java.util.Map;

public class StartProcessCommand {
    private String processCode;
    private String businessId;
    private String applicant;
    private String dynamicApprover;
    private Map<String, Object> variables = new HashMap<>();

    public String getProcessCode() { return processCode; }
    public void setProcessCode(String processCode) { this.processCode = processCode; }
    public String getBusinessId() { return businessId; }
    public void setBusinessId(String businessId) { this.businessId = businessId; }
    public String getApplicant() { return applicant; }
    public void setApplicant(String applicant) { this.applicant = applicant; }
    public String getDynamicApprover() { return dynamicApprover; }
    public void setDynamicApprover(String dynamicApprover) { this.dynamicApprover = dynamicApprover; }
    public Map<String, Object> getVariables() { return variables; }
    public void setVariables(Map<String, Object> variables) { this.variables = variables; }
}
