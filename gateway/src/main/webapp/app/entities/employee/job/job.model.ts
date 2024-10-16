import { IDepartment } from 'app/entities/employee/department/department.model';

export interface IJob {
  id?: number;
  jobName?: string;
  description?: string | null;
  department?: IDepartment | null;
}

export class Job implements IJob {
  constructor(public id?: number, public jobName?: string, public description?: string | null, public department?: IDepartment | null) {}
}

export function getJobIdentifier(job: IJob): number | undefined {
  return job.id;
}
