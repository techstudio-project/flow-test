# flow-test

轻量级流程审批示例，基于 Java 8、Spring Boot 2、MyBatis-Flex。

## 设计补充

原始表结构缺少流程实例与模板的直接关联，以及动态用户审批人字段；示例实现补充了 `process_code`、`current_approver` 和常用索引。分支节点通过 JSON 的 `condition` 字段绑定 Spring Bean，让业务代码实现 `BranchCondition` 返回下一个节点 id 或 `end`。

## 请假流程定义示例

```json
{
  "code": "leave",
  "name": "请假审批流程",
  "flow": [
    {"id": "officer"},
    {"id": "leader", "hasCondition": true, "condition": "leaveApprovalCondition"},
    {"id": "dep_leader"}
  ],
  "nodes": [
    {"id": "officer", "name": "请假专员审批", "approver": [{"type": "USER", "value": ""}]},
    {"id": "leader", "name": "交付组长审批", "approver": [{"type": "ROLE", "value": "ROLE_TEAM_LEADER"}]},
    {"id": "dep_leader", "name": "部门经理审批", "approver": [{"type": "USER", "value": "XXXX"}, {"type": "ROLE", "value": "ROLE_DEP_LEADER"}]}
  ]
}
```

`LeaveApprovalCondition` 演示请假天数大于 3 天流转到部门经理，否则直接结束。
