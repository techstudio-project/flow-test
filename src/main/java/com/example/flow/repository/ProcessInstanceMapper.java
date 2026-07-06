package com.example.flow.repository;

import com.example.flow.domain.ProcessInstance;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProcessInstanceMapper extends BaseMapper<ProcessInstance> {
}
