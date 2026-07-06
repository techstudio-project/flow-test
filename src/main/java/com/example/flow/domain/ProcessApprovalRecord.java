package com.example.flow.domain;

import com.example.flow.model.ApprovalStatus;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;

@Table("process_approval_record")
public class ProcessApprovalRecord {
    @Id
    private String id;
    private String instanceId;
    private String nodeCode;
    private String nodeName;
    private String approver;
    private ApprovalStatus status;
    private String comment;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getInstanceId() { return instanceId; }
    public void setInstanceId(String instanceId) { this.instanceId = instanceId; }
    public String getNodeCode() { return nodeCode; }
    public void setNodeCode(String nodeCode) { this.nodeCode = nodeCode; }
    public String getNodeName() { return nodeName; }
    public void setNodeName(String nodeName) { this.nodeName = nodeName; }
    public String getApprover() { return approver; }
    public void setApprover(String approver) { this.approver = approver; }
    public ApprovalStatus getStatus() { return status; }
    public void setStatus(ApprovalStatus status) { this.status = status; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
