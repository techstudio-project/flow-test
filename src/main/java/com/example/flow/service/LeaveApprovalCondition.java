package com.example.flow.service;

import com.example.flow.domain.ProcessInstance;
import com.example.flow.engine.ApprovalCommand;
import com.example.flow.engine.BranchCondition;
import com.example.flow.model.ProcessDefinition;
import org.springframework.stereotype.Component;

@Component("leaveApprovalCondition")
public class LeaveApprovalCondition implements BranchCondition {
    @Override
    public String decide(ProcessDefinition definition, ProcessInstance instance, ApprovalCommand command) {
        Object days = command.getVariables().get("days");
        if (days instanceof Number && ((Number) days).intValue() > 3) {
            return "dep_leader";
        }
        return "end";
    }
}
