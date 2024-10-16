import * as dayjs from 'dayjs';
import { IJob } from 'app/entities/employee/job/job.model';
import { IDepartment } from 'app/entities/employee/department/department.model';

export interface IEmployee {
  id?: number;
  name?: string;
  hireDate?: dayjs.Dayjs;
  salary?: number;
  job?: IJob | null;
  department?: IDepartment | null;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public name?: string,
    public hireDate?: dayjs.Dayjs,
    public salary?: number,
    public job?: IJob | null,
    public department?: IDepartment | null
  ) {}
}

export function getEmployeeIdentifier(employee: IEmployee): number | undefined {
  return employee.id;
}
