package com.leap.employee.service;

import com.leap.employee.domain.Employee;
import com.leap.employee.domain.JobHistory;
import com.leap.employee.repository.EmployeeRepository;
import com.leap.employee.repository.JobHistoryRepository;
import com.leap.employee.service.dto.EmployeeDTO;
import com.leap.employee.service.mapper.EmployeeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final JobHistoryRepository jobHistoryRepository;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper,
            JobHistoryRepository jobHistoryRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.jobHistoryRepository = jobHistoryRepository;
    }

    /**
     * Save a employee.
     *
     * @param employeeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);

        boolean isUpdate = employee.getId() != null;
        if (isUpdate) {
            Optional<Employee> existingEmployeeOpt = employeeRepository.findById(employee.getId());
            if (existingEmployeeOpt.isPresent()) {
                Employee existingEmployee = existingEmployeeOpt.get();

                // Check if relevant fields have changed
                if (!existingEmployee.getHireDate().equals(employee.getHireDate()) ||
                        !existingEmployee.getJob().equals(employee.getJob()) ||
                        !existingEmployee.getDepartment().equals(employee.getDepartment()) ||
                        existingEmployee.getSalary().compareTo(employee.getSalary()) != 0) {

                    // Create a new JobHistory entry
                    JobHistory jobHistory = new JobHistory();
                    jobHistory.setStartDate(existingEmployee.getHireDate());
                    jobHistory.setSalary(existingEmployee.getSalary());
                    jobHistory.setJob(existingEmployee.getJob());
                    jobHistory.setEmployee(existingEmployee);
                    jobHistory.setDepartment(existingEmployee.getDepartment());

                    // Save the new JobHistory entry
                    jobHistoryRepository.save(jobHistory);
                }
            }
        }

        // Save the updated Employee entity
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    /**
     * Partially update a employee.
     *
     * @param employeeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeDTO> partialUpdate(EmployeeDTO employeeDTO) {
        log.debug("Request to partially update Employee : {}", employeeDTO);

        return employeeRepository
                .findById(employeeDTO.getId())
                .map(existingEmployee -> {
                    // Create a new JobHistory entry if relevant fields are updated
                    if ((employeeDTO.getHireDate() != null
                            && !employeeDTO.getHireDate().equals(existingEmployee.getHireDate())) ||
                            (employeeDTO.getJob() != null && !employeeDTO.getJob().equals(existingEmployee.getJob())) ||
                            (employeeDTO.getDepartment() != null
                                    && !employeeDTO.getDepartment().equals(existingEmployee.getDepartment()))
                            ||
                            (employeeDTO.getSalary() != null
                                    && existingEmployee.getSalary().compareTo(employeeDTO.getSalary()) != 0)) {

                        JobHistory jobHistory = new JobHistory();
                        jobHistory.setStartDate(existingEmployee.getHireDate());
                        jobHistory.setSalary(existingEmployee.getSalary());
                        jobHistory.setJob(existingEmployee.getJob());
                        jobHistory.setEmployee(existingEmployee);
                        jobHistory.setDepartment(existingEmployee.getDepartment());

                        jobHistoryRepository.save(jobHistory);
                    }

                    employeeMapper.partialUpdate(existingEmployee, employeeDTO);
                    return existingEmployee;
                })
                .map(employeeRepository::save)
                .map(employeeMapper::toDto);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id).map(employeeMapper::toDto);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
