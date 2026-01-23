export {}

export interface ZipEntry {
  name: string
  path: string
  type: 'FILE' | 'DIRECTORY'
  parent?: ZipEntry
  children: Array<ZipEntry>
}

export interface Field {
  name: string
  desc: string
  access: number
}

export interface Method {
  name: string
  desc: string
  access: number
}

interface Bridge {
  loadFile(): Promise<ZipEntry>
  findEntry(path: string, depth: number): Promise<ZipEntry>
  getTracePrinted(path: string): Promise<string>
  getFields(path: string): Promise<Field[]>
  getMethods(path: string): Promise<Method[]>
}

declare global {
  const bridge: Bridge
}
