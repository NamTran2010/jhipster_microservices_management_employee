package com.leap.employee.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.leap.employee.domain.Department} entity.
 */
public class DepartmentDTO implements Serializable {

    private Long id;

    @NotNull
    private String departmentName;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartmentDTO)) {
            return false;
        }

        DepartmentDTO departmentDTO = (DepartmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, departmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartmentDTO{" +
                "id=" + getId() +
                ", departmentName='" + getDepartmentName() + "'" +
                ", description='" + getDescription() + "'" +
                "}";
    }
}
