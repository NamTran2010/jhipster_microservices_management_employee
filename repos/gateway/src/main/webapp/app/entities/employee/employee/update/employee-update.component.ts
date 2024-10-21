import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEmployee, Employee } from '../employee.model';
import { EmployeeService } from '../service/employee.service';
import { IJob } from 'app/entities/employee/job/job.model';
import { JobService } from 'app/entities/employee/job/service/job.service';
import { IDepartment } from 'app/entities/employee/department/department.model';
import { DepartmentService } from 'app/entities/employee/department/service/department.service';

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;

  jobsSharedCollection: IJob[] = [];
  departmentsSharedCollection: IDepartment[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    hireDate: [null, [Validators.required]],
    salary: [null, [Validators.required]],
    job: [],
    department: [],
  });

  constructor(
    protected employeeService: EmployeeService,
    protected jobService: JobService,
    protected departmentService: DepartmentService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  trackJobById(index: number, item: IJob): number {
    return item.id!;
  }

  trackDepartmentById(index: number, item: IDepartment): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(employee: IEmployee): void {
    this.editForm.patchValue({
      id: employee.id,
      name: employee.name,
      hireDate: employee.hireDate,
      salary: employee.salary,
      job: employee.job,
      department: employee.department,
    });

    this.jobsSharedCollection = this.jobService.addJobToCollectionIfMissing(this.jobsSharedCollection, employee.job);
    this.departmentsSharedCollection = this.departmentService.addDepartmentToCollectionIfMissing(
      this.departmentsSharedCollection,
      employee.department
    );
  }

  protected loadRelationshipsOptions(): void {
    this.jobService
      .query()
      .pipe(map((res: HttpResponse<IJob[]>) => res.body ?? []))
      .pipe(map((jobs: IJob[]) => this.jobService.addJobToCollectionIfMissing(jobs, this.editForm.get('job')!.value)))
      .subscribe((jobs: IJob[]) => (this.jobsSharedCollection = jobs));

    this.departmentService
      .query()
      .pipe(map((res: HttpResponse<IDepartment[]>) => res.body ?? []))
      .pipe(
        map((departments: IDepartment[]) =>
          this.departmentService.addDepartmentToCollectionIfMissing(departments, this.editForm.get('department')!.value)
        )
      )
      .subscribe((departments: IDepartment[]) => (this.departmentsSharedCollection = departments));
  }

  protected createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      hireDate: this.editForm.get(['hireDate'])!.value,
      salary: this.editForm.get(['salary'])!.value,
      job: this.editForm.get(['job'])!.value,
      department: this.editForm.get(['department'])!.value,
    };
  }
}
