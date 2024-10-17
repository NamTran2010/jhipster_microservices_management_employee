jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { JobHistoryService } from '../service/job-history.service';
import { IJobHistory, JobHistory } from '../job-history.model';
import { IJob } from 'app/entities/employee/job/job.model';
import { JobService } from 'app/entities/employee/job/service/job.service';
import { IEmployee } from 'app/entities/employee/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/employee/service/employee.service';
import { IDepartment } from 'app/entities/employee/department/department.model';
import { DepartmentService } from 'app/entities/employee/department/service/department.service';

import { JobHistoryUpdateComponent } from './job-history-update.component';

describe('Component Tests', () => {
  describe('JobHistory Management Update Component', () => {
    let comp: JobHistoryUpdateComponent;
    let fixture: ComponentFixture<JobHistoryUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let jobHistoryService: JobHistoryService;
    let jobService: JobService;
    let employeeService: EmployeeService;
    let departmentService: DepartmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [JobHistoryUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(JobHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobHistoryUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      jobHistoryService = TestBed.inject(JobHistoryService);
      jobService = TestBed.inject(JobService);
      employeeService = TestBed.inject(EmployeeService);
      departmentService = TestBed.inject(DepartmentService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Job query and add missing value', () => {
        const jobHistory: IJobHistory = { id: 456 };
        const job: IJob = { id: 42770 };
        jobHistory.job = job;

        const jobCollection: IJob[] = [{ id: 75633 }];
        jest.spyOn(jobService, 'query').mockReturnValue(of(new HttpResponse({ body: jobCollection })));
        const additionalJobs = [job];
        const expectedCollection: IJob[] = [...additionalJobs, ...jobCollection];
        jest.spyOn(jobService, 'addJobToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ jobHistory });
        comp.ngOnInit();

        expect(jobService.query).toHaveBeenCalled();
        expect(jobService.addJobToCollectionIfMissing).toHaveBeenCalledWith(jobCollection, ...additionalJobs);
        expect(comp.jobsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Employee query and add missing value', () => {
        const jobHistory: IJobHistory = { id: 456 };
        const employee: IEmployee = { id: 94649 };
        jobHistory.employee = employee;

        const employeeCollection: IEmployee[] = [{ id: 89297 }];
        jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
        const additionalEmployees = [employee];
        const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
        jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ jobHistory });
        comp.ngOnInit();

        expect(employeeService.query).toHaveBeenCalled();
        expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(employeeCollection, ...additionalEmployees);
        expect(comp.employeesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Department query and add missing value', () => {
        const jobHistory: IJobHistory = { id: 456 };
        const department: IDepartment = { id: 6911 };
        jobHistory.department = department;

        const departmentCollection: IDepartment[] = [{ id: 12924 }];
        jest.spyOn(departmentService, 'query').mockReturnValue(of(new HttpResponse({ body: departmentCollection })));
        const additionalDepartments = [department];
        const expectedCollection: IDepartment[] = [...additionalDepartments, ...departmentCollection];
        jest.spyOn(departmentService, 'addDepartmentToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ jobHistory });
        comp.ngOnInit();

        expect(departmentService.query).toHaveBeenCalled();
        expect(departmentService.addDepartmentToCollectionIfMissing).toHaveBeenCalledWith(departmentCollection, ...additionalDepartments);
        expect(comp.departmentsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const jobHistory: IJobHistory = { id: 456 };
        const job: IJob = { id: 96728 };
        jobHistory.job = job;
        const employee: IEmployee = { id: 88001 };
        jobHistory.employee = employee;
        const department: IDepartment = { id: 42374 };
        jobHistory.department = department;

        activatedRoute.data = of({ jobHistory });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(jobHistory));
        expect(comp.jobsSharedCollection).toContain(job);
        expect(comp.employeesSharedCollection).toContain(employee);
        expect(comp.departmentsSharedCollection).toContain(department);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<JobHistory>>();
        const jobHistory = { id: 123 };
        jest.spyOn(jobHistoryService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ jobHistory });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: jobHistory }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(jobHistoryService.update).toHaveBeenCalledWith(jobHistory);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<JobHistory>>();
        const jobHistory = new JobHistory();
        jest.spyOn(jobHistoryService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ jobHistory });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: jobHistory }));
        saveSubject.complete();

        // THEN
        expect(jobHistoryService.create).toHaveBeenCalledWith(jobHistory);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<JobHistory>>();
        const jobHistory = { id: 123 };
        jest.spyOn(jobHistoryService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ jobHistory });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(jobHistoryService.update).toHaveBeenCalledWith(jobHistory);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackJobById', () => {
        it('Should return tracked Job primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackJobById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackEmployeeById', () => {
        it('Should return tracked Employee primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEmployeeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackDepartmentById', () => {
        it('Should return tracked Department primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDepartmentById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
