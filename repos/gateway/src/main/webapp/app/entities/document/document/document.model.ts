import { IDocumentType } from 'app/entities/document/document-type/document-type.model';

export interface IDocument {
  id?: number;
  employeeId?: number;
  documentName?: string;
  description?: string | null;
  documentType?: IDocumentType | null;
}

export class Document implements IDocument {
  constructor(
    public id?: number,
    public employeeId?: number,
    public documentName?: string,
    public description?: string | null,
    public documentType?: IDocumentType | null
  ) {}
}

export function getDocumentIdentifier(document: IDocument): number | undefined {
  return document.id;
}
