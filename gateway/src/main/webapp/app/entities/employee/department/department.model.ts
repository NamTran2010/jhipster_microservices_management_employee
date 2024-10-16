export interface IDepartment {
  id?: number;
  departmentName?: string;
  description?: string | null;
}

export class Department implements IDepartment {
  constructor(public id?: number, public departmentName?: string, public description?: string | null) {}
}

export function getDepartmentIdentifier(department: IDepartment): number | undefined {
  return department.id;
}
