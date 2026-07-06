package com.example.flow.engine;

import com.example.flow.domain.ProcessApprovalRecord;
import com.example.flow.domain.ProcessInstance;
import com.example.flow.domain.ProcessTemplate;
import com.example.flow.model.*;
import com.example.flow.repository.ProcessApprovalRecordMapper;
import com.example.flow.repository.ProcessInstanceMapper;
import com.example.flow.repository.ProcessTemplateMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Service
public class ProcessRuntime {
    private static final String END = "end";
    private final ProcessTemplateMapper templateMapper;
    private final ProcessInstanceMapper instanceMapper;
    private final ProcessApprovalRecordMapper recordMapper;
    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;

    public ProcessRuntime(ProcessTemplateMapper templateMapper, ProcessInstanceMapper instanceMapper,
                          ProcessApprovalRecordMapper recordMapper, ObjectMapper objectMapper,
                          ApplicationContext applicationContext) {
        this.templateMapper = templateMapper;
        this.instanceMapper = instanceMapper;
        this.recordMapper = recordMapper;
        this.objectMapper = objectMapper;
        this.applicationContext = applicationContext;
    }

    @Transactional
    public ProcessTemplate deploy(String definitionJson) {
        ProcessDefinition definition = parse(definitionJson);
        validate(definition);
        ProcessTemplate template = templateMapper.findByProcessCode(definition.getCode());
        if (template == null) {
            template = new ProcessTemplate();
            template.setId(UUID.randomUUID().toString());
        }
        template.setProcessCode(definition.getCode());
        template.setProcessName(definition.getName());
        template.setProcessDefinition(definitionJson);
        templateMapper.insertOrUpdate(template);
        return template;
    }

    @Transactional
    public ProcessInstance start(StartProcessCommand command) {
        ProcessTemplate template = requireTemplate(command.getProcessCode());
        ProcessDefinition definition = parse(template.getProcessDefinition());
        ProcessInstance instance = new ProcessInstance();
        instance.setId(UUID.randomUUID().toString());
        instance.setProcessCode(command.getProcessCode());
        instance.setBusinessId(command.getBusinessId());
        instance.setApplicant(command.getApplicant());
        instance.setStatus(ProcessStatus.RUNNING);
        moveTo(instance, definition, 0, command.getDynamicApprover());
        instanceMapper.insert(instance);
        createRecord(instance, null, ApprovalStatus.PENDING, "流程发起");
        return instance;
    }

    @Transactional
    public ProcessInstance approve(String instanceId, ApprovalCommand command) {
        ProcessInstance instance = requireRunningInstance(instanceId);
        ProcessDefinition definition = loadDefinition(instance.getProcessCode());
        createRecord(instance, command.getApprover(), ApprovalStatus.APPROVED, command.getComment());
        FlowStep current = definition.getFlow().get(instance.getCurrentNodeIndex());
        Integer next = nextIndex(definition, instance, current, command);
        if (next == null) {
            instance.setStatus(ProcessStatus.FINISHED);
            instance.setCurrentNodeStatus(ApprovalStatus.APPROVED);
        } else {
            moveTo(instance, definition, next, null);
            createRecord(instance, null, ApprovalStatus.PENDING, "流转到节点");
        }
        instanceMapper.update(instance);
        return instance;
    }

    @Transactional
    public ProcessInstance reject(String instanceId, ApprovalCommand command) {
        ProcessInstance instance = requireRunningInstance(instanceId);
        ProcessDefinition definition = loadDefinition(instance.getProcessCode());
        createRecord(instance, command.getApprover(), ApprovalStatus.REJECTED, command.getComment());
        int previous = instance.getCurrentNodeIndex() - 1;
        if (previous < 0) {
            instance.setStatus(ProcessStatus.REJECTED);
            instance.setCurrentNodeStatus(ApprovalStatus.REJECTED);
        } else {
            moveTo(instance, definition, previous, null);
            createRecord(instance, null, ApprovalStatus.PENDING, "驳回后回到上一节点");
        }
        instanceMapper.update(instance);
        return instance;
    }

    private Integer nextIndex(ProcessDefinition definition, ProcessInstance instance, FlowStep current, ApprovalCommand command) {
        String target = current.getTo();
        if (Boolean.TRUE.equals(current.getHasCondition()) && StringUtils.hasText(current.getCondition())) {
            target = applicationContext.getBean(current.getCondition(), BranchCondition.class).decide(definition, instance, command);
        }
        if (END.equalsIgnoreCase(target)) return null;
        if (StringUtils.hasText(target)) return findStepIndex(definition, target);
        int next = instance.getCurrentNodeIndex() + 1;
        return next >= definition.getFlow().size() ? null : next;
    }

    private void moveTo(ProcessInstance instance, ProcessDefinition definition, int index, String dynamicApprover) {
        FlowStep step = definition.getFlow().get(index);
        NodeDefinition node = node(definition, step.getId());
        instance.setCurrentNodeIndex(index);
        instance.setCurrentNodeCode(node.getId());
        instance.setCurrentNodeName(node.getName());
        instance.setCurrentNodeStatus(ApprovalStatus.PENDING);
        instance.setCurrentApprover(dynamicApprover);
        instance.setCurrentApproverRole(firstApproverValue(node, ApproverType.ROLE));
    }

    private String firstApproverValue(NodeDefinition node, ApproverType type) {
        for (ApproverDefinition approver : node.getApprover()) {
            if (type == approver.getType() && StringUtils.hasText(approver.getValue())) return approver.getValue();
        }
        return null;
    }

    private void createRecord(ProcessInstance instance, String approver, ApprovalStatus status, String comment) {
        ProcessApprovalRecord record = new ProcessApprovalRecord();
        record.setId(UUID.randomUUID().toString());
        record.setInstanceId(instance.getId());
        record.setNodeCode(instance.getCurrentNodeCode());
        record.setNodeName(instance.getCurrentNodeName());
        record.setApprover(approver);
        record.setStatus(status);
        record.setComment(comment);
        recordMapper.insert(record);
    }

    private ProcessTemplate requireTemplate(String processCode) {
        ProcessTemplate template = templateMapper.findByProcessCode(processCode);
        if (template == null) throw new IllegalArgumentException("流程模板不存在: " + processCode);
        return template;
    }

    private ProcessInstance requireRunningInstance(String instanceId) {
        ProcessInstance instance = instanceMapper.selectOneById(instanceId);
        if (instance == null || !instance.isRunning()) throw new IllegalStateException("流程实例不存在或非运行中: " + instanceId);
        return instance;
    }

    private ProcessDefinition loadDefinition(String processCode) { return parse(requireTemplate(processCode).getProcessDefinition()); }
    private ProcessDefinition parse(String json) {
        try { return objectMapper.readValue(json, ProcessDefinition.class); }
        catch (IOException e) { throw new IllegalArgumentException("流程定义JSON不合法", e); }
    }
    private NodeDefinition node(ProcessDefinition definition, String id) {
        for (NodeDefinition node : definition.getNodes()) if (id.equals(node.getId())) return node;
        throw new IllegalArgumentException("节点不存在: " + id);
    }
    private int findStepIndex(ProcessDefinition definition, String nodeId) {
        for (int i = 0; i < definition.getFlow().size(); i++) if (nodeId.equals(definition.getFlow().get(i).getId())) return i;
        throw new IllegalArgumentException("流转目标不存在: " + nodeId);
    }
    private void validate(ProcessDefinition definition) {
        if (!StringUtils.hasText(definition.getCode()) || definition.getFlow().isEmpty()) throw new IllegalArgumentException("流程code和flow不能为空");
        for (FlowStep step : definition.getFlow()) node(definition, step.getId());
    }
}
