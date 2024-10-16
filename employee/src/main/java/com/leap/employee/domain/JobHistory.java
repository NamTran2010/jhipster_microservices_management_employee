package com.leap.employee.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobHistory.
 */
@Entity
@Table(name = "job_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @NotNull
    @Column(name = "salary", precision = 21, scale = 2, nullable = false)
    private BigDecimal salary;

    @ManyToOne
    @JsonIgnoreProperties(value = { "department" }, allowSetters = true)
    private Job job;

    @ManyToOne
    @JsonIgnoreProperties(value = { "job", "department" }, allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JobHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return this.startDate;
    }

    public JobHistory startDate(ZonedDateTime startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return this.endDate;
    }

    public JobHistory endDate(ZonedDateTime endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public JobHistory salary(BigDecimal salary) {
        this.setSalary(salary);
        return this;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Job getJob() {
        return this.job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobHistory job(Job job) {
        this.setJob(job);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public JobHistory employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobHistory)) {
            return false;
        }
        return id != null && id.equals(((JobHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobHistory{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", salary=" + getSalary() +
            "}";
    }
}
