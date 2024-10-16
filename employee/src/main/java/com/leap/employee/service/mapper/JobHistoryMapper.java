package com.leap.employee.service.mapper;

import com.leap.employee.domain.*;
import com.leap.employee.service.dto.JobHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobHistory} and its DTO {@link JobHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { JobMapper.class, EmployeeMapper.class })
public interface JobHistoryMapper extends EntityMapper<JobHistoryDTO, JobHistory> {
    @Mapping(target = "job", source = "job", qualifiedByName = "jobName")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "name")
    JobHistoryDTO toDto(JobHistory s);
}
