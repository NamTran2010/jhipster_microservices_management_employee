package com.leap.employee.service.mapper;

import com.leap.employee.domain.*;
import com.leap.employee.service.dto.DepartmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {
    @Named("departmentName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "departmentName", source = "departmentName")
    DepartmentDTO toDtoDepartmentName(Department department);
}
