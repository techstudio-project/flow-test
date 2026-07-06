package com.example.flow.engine;

import com.example.flow.domain.ProcessInstance;
import com.example.flow.model.ProcessDefinition;

/** Business extension point used by conditional flow steps. Return the next node id or "end". */
public interface BranchCondition {
    String decide(ProcessDefinition definition, ProcessInstance instance, ApprovalCommand command);
}
