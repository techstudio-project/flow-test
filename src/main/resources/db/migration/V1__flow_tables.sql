CREATE TABLE IF NOT EXISTS process_template (
  id char(36) NOT NULL,
  process_code varchar(100) DEFAULT NULL COMMENT '流程code',
  process_name varchar(100) DEFAULT NULL COMMENT '流程名称',
  process_definition json DEFAULT NULL COMMENT '流程json定义',
  PRIMARY KEY (id),
  UNIQUE KEY uk_process_code (process_code)
) COMMENT='流程模板表';

CREATE TABLE IF NOT EXISTS process_instance (
  id char(36) NOT NULL,
  business_id char(36) DEFAULT NULL COMMENT '业务id',
  process_code varchar(100) DEFAULT NULL COMMENT '流程code',
  applicant varchar(100) DEFAULT NULL COMMENT '流程发起者',
  status varchar(100) DEFAULT NULL COMMENT '流程状态：RUNNING/FINISHED/REJECTED/CANCELED',
  current_node_index int DEFAULT NULL COMMENT '当前节点index',
  current_node_status varchar(100) DEFAULT NULL COMMENT '审批状态：PENDING/APPROVED/REJECTED',
  current_node_code varchar(100) DEFAULT NULL COMMENT '当前节点code',
  current_node_name varchar(100) DEFAULT NULL COMMENT '当前节点名称',
  current_approver varchar(100) DEFAULT NULL COMMENT '当前审批人',
  current_approver_role varchar(100) DEFAULT NULL COMMENT '当前审批人角色',
  PRIMARY KEY (id),
  KEY idx_business_process (business_id, process_code)
) COMMENT='流程实例表';

CREATE TABLE IF NOT EXISTS process_approval_record (
  id char(36) NOT NULL,
  instance_id char(36) DEFAULT NULL COMMENT '流程实例id',
  node_code varchar(100) DEFAULT NULL COMMENT '节点code',
  node_name varchar(100) DEFAULT NULL COMMENT '节点名称',
  approver varchar(100) DEFAULT NULL COMMENT '审批人',
  status varchar(100) DEFAULT NULL COMMENT '审批状态：PENDING/APPROVED/REJECTED',
  comment varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_instance_id (instance_id)
) COMMENT='审批记录表';
