package com.leap.employee.repository;

import com.leap.employee.domain.JobHistory;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the JobHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, Long> {
    List<JobHistory> findByEmployeeId(Long employeeId); // Phương thức tìm kiếm job histories theo employee ID
}