package com.leap.employee.service;

import com.leap.employee.domain.JobHistory;
import com.leap.employee.repository.JobHistoryRepository;
import com.leap.employee.service.dto.JobHistoryDTO;
import com.leap.employee.service.mapper.JobHistoryMapper;
import java.util.Optional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link JobHistory}.
 */
@Service
@Transactional
public class JobHistoryService {

    private final Logger log = LoggerFactory.getLogger(JobHistoryService.class);

    private final JobHistoryRepository jobHistoryRepository;

    private final JobHistoryMapper jobHistoryMapper;

    public JobHistoryService(JobHistoryRepository jobHistoryRepository, JobHistoryMapper jobHistoryMapper) {
        this.jobHistoryRepository = jobHistoryRepository;
        this.jobHistoryMapper = jobHistoryMapper;
    }

    /**
     * Save a jobHistory.
     *
     * @param jobHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public JobHistoryDTO save(JobHistoryDTO jobHistoryDTO) {
        log.debug("Request to save JobHistory : {}", jobHistoryDTO);
        JobHistory jobHistory = jobHistoryMapper.toEntity(jobHistoryDTO);
        jobHistory = jobHistoryRepository.save(jobHistory);
        return jobHistoryMapper.toDto(jobHistory);
    }

    /**
     * Partially update a jobHistory.
     *
     * @param jobHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobHistoryDTO> partialUpdate(JobHistoryDTO jobHistoryDTO) {
        log.debug("Request to partially update JobHistory : {}", jobHistoryDTO);

        return jobHistoryRepository
                .findById(jobHistoryDTO.getId())
                .map(existingJobHistory -> {
                    jobHistoryMapper.partialUpdate(existingJobHistory, jobHistoryDTO);

                    return existingJobHistory;
                })
                .map(jobHistoryRepository::save)
                .map(jobHistoryMapper::toDto);
    }

    /**
     * Get all the jobHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobHistories");
        return jobHistoryRepository.findAll(pageable).map(jobHistoryMapper::toDto);
    }

    /**
     * Get one jobHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobHistoryDTO> findOne(Long id) {
        log.debug("Request to get JobHistory : {}", id);
        return jobHistoryRepository.findById(id).map(jobHistoryMapper::toDto);
    }

    // Tìm kiếm JobHistory theo employeeId
    public List<JobHistory> findByEmployeeId(Long employeeId) {
        return jobHistoryRepository.findByEmployeeId(employeeId);
    }

    /**
     * Delete the jobHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobHistory : {}", id);
        jobHistoryRepository.deleteById(id);
    }
}
