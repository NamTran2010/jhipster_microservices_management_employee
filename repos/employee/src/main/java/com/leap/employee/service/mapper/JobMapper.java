package com.leap.employee.service.mapper;

import com.leap.employee.domain.*;
import com.leap.employee.service.dto.JobDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Job} and its DTO {@link JobDTO}.
 */
@Mapper(componentModel = "spring", uses = { DepartmentMapper.class })
public interface JobMapper extends EntityMapper<JobDTO, Job> {
    @Mapping(target = "department", source = "department", qualifiedByName = "departmentName")
    JobDTO toDto(Job s);

    @Named("jobName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "jobName", source = "jobName")
    JobDTO toDtoJobName(Job job);
}
