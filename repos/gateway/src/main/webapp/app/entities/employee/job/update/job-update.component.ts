import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IJob, Job } from '../job.model';
import { JobService } from '../service/job.service';
import { IDepartment } from 'app/entities/employee/department/department.model';
import { DepartmentService } from 'app/entities/employee/department/service/department.service';

@Component({
  selector: 'jhi-job-update',
  templateUrl: './job-update.component.html',
})
export class JobUpdateComponent implements OnInit {
  isSaving = false;

  departmentsSharedCollection: IDepartment[] = [];

  editForm = this.fb.group({
    id: [],
    jobName: [null, [Validators.required]],
    description: [],
    department: [],
  });

  constructor(
    protected jobService: JobService,
    protected departmentService: DepartmentService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ job }) => {
      this.updateForm(job);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const job = this.createFromForm();
    if (job.id !== undefined) {
      this.subscribeToSaveResponse(this.jobService.update(job));
    } else {
      this.subscribeToSaveResponse(this.jobService.create(job));
    }
  }

  trackDepartmentById(index: number, item: IDepartment): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJob>>): void {
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

  protected updateForm(job: IJob): void {
    this.editForm.patchValue({
      id: job.id,
      jobName: job.jobName,
      description: job.description,
      department: job.department,
    });

    this.departmentsSharedCollection = this.departmentService.addDepartmentToCollectionIfMissing(
      this.departmentsSharedCollection,
      job.department
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IJob {
    return {
      ...new Job(),
      id: this.editForm.get(['id'])!.value,
      jobName: this.editForm.get(['jobName'])!.value,
      description: this.editForm.get(['description'])!.value,
      department: this.editForm.get(['department'])!.value,
    };
  }
}
