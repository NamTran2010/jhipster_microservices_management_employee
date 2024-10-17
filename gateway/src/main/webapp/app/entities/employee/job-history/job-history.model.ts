import * as dayjs from 'dayjs';
import { IJob } from 'app/entities/employee/job/job.model';
import { IEmployee } from 'app/entities/employee/employee/employee.model';

export interface IJobHistory {
  id?: number;
  startDate?: dayjs.Dayjs;
  salary?: number;
  job?: IJob | null;
  employee?: IEmployee | null;
}

export class JobHistory implements IJobHistory {
  constructor(
    public id?: number,
    public startDate?: dayjs.Dayjs,
    public salary?: number,
    public job?: IJob | null,
    public employee?: IEmployee | null
  ) {}
}

export function getJobHistoryIdentifier(jobHistory: IJobHistory): number | undefined {
  return jobHistory.id;
}
