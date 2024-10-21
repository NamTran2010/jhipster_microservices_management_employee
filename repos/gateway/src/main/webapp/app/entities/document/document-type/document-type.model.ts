export interface IDocumentType {
  id?: number;
  documentTypeName?: string;
}

export class DocumentType implements IDocumentType {
  constructor(public id?: number, public documentTypeName?: string) {}
}

export function getDocumentTypeIdentifier(documentType: IDocumentType): number | undefined {
  return documentType.id;
}
