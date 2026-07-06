package com.example.flow.repository;

import com.example.flow.domain.ProcessTemplate;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProcessTemplateMapper extends BaseMapper<ProcessTemplate> {
    @Select("select * from process_template where process_code = #{processCode} limit 1")
    ProcessTemplate findByProcessCode(String processCode);
}
