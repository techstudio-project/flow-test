package com.example.flow.domain;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;

@Table("process_template")
public class ProcessTemplate {
    @Id
    private String id;
    private String processCode;
    private String processName;
    private String processDefinition;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getProcessCode() { return processCode; }
    public void setProcessCode(String processCode) { this.processCode = processCode; }
    public String getProcessName() { return processName; }
    public void setProcessName(String processName) { this.processName = processName; }
    public String getProcessDefinition() { return processDefinition; }
    public void setProcessDefinition(String processDefinition) { this.processDefinition = processDefinition; }
}
