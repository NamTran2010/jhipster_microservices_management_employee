import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployee } from '../employee.model';
import { EmployeeService } from '../service/employee.service';

@Component({
  selector: 'jhi-employee-detail',
  templateUrl: './employee-detail.component.html',
})
export class EmployeeDetailComponent implements OnInit {
  employee: IEmployee | null = null;
  jobHistories: any[] = []; // Biến để lưu dữ liệu job histories
  showJobHistory = false; // Biến để điều khiển hiển thị bảng

  constructor(protected activatedRoute: ActivatedRoute, private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.employee = employee;
    });
  }

  previousState(): void {
    window.history.back();
  }

  handleClick(): void {
    this.showJobHistory = !this.showJobHistory; // Toggle hiển thị bảng
    if (!this.showJobHistory) {
      return; // Nếu bảng đang hiển thị thì ẩn bảng mà không cần gọi API lại
    }

    const employeeId = this.employee?.id;
    if (employeeId) {
      this.employeeService.getJobHistoriesForEmployee(employeeId).subscribe(jobHistory => {
        this.jobHistories = jobHistory;
      });
    }
  }
}
