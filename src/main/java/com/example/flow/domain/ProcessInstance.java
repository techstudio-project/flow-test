package com.example.flow.domain;

import com.example.flow.model.ApprovalStatus;
import com.example.flow.model.ProcessStatus;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;

@Table("process_instance")
public class ProcessInstance {
    @Id
    private String id;
    private String businessId;
    private String processCode;
    private String applicant;
    private ProcessStatus status;
    private Integer currentNodeIndex;
    private ApprovalStatus currentNodeStatus;
    private String currentNodeCode;
    private String currentNodeName;
    private String currentApprover;
    private String currentApproverRole;

    public boolean isRunning() { return ProcessStatus.RUNNING == status; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBusinessId() { return businessId; }
    public void setBusinessId(String businessId) { this.businessId = businessId; }
    public String getProcessCode() { return processCode; }
    public void setProcessCode(String processCode) { this.processCode = processCode; }
    public String getApplicant() { return applicant; }
    public void setApplicant(String applicant) { this.applicant = applicant; }
    public ProcessStatus getStatus() { return status; }
    public void setStatus(ProcessStatus status) { this.status = status; }
    public Integer getCurrentNodeIndex() { return currentNodeIndex; }
    public void setCurrentNodeIndex(Integer currentNodeIndex) { this.currentNodeIndex = currentNodeIndex; }
    public ApprovalStatus getCurrentNodeStatus() { return currentNodeStatus; }
    public void setCurrentNodeStatus(ApprovalStatus currentNodeStatus) { this.currentNodeStatus = currentNodeStatus; }
    public String getCurrentNodeCode() { return currentNodeCode; }
    public void setCurrentNodeCode(String currentNodeCode) { this.currentNodeCode = currentNodeCode; }
    public String getCurrentNodeName() { return currentNodeName; }
    public void setCurrentNodeName(String currentNodeName) { this.currentNodeName = currentNodeName; }
    public String getCurrentApprover() { return currentApprover; }
    public void setCurrentApprover(String currentApprover) { this.currentApprover = currentApprover; }
    public String getCurrentApproverRole() { return currentApproverRole; }
    public void setCurrentApproverRole(String currentApproverRole) { this.currentApproverRole = currentApproverRole; }
}
