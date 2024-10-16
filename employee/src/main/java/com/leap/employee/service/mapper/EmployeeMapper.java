package com.leap.employee.service.mapper;

import com.leap.employee.domain.*;
import com.leap.employee.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = { JobMapper.class, DepartmentMapper.class })
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "job", source = "job", qualifiedByName = "jobName")
    @Mapping(target = "department", source = "department", qualifiedByName = "departmentName")
    EmployeeDTO toDto(Employee s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EmployeeDTO toDtoName(Employee employee);
}
