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

export interface InstructionNode {
  type: number
  opcode: number
}

export interface FieldInstructionNode extends InstructionNode {
  owner: number
  name: number
  desc: number
}

export interface FrameInstructionNode extends InstructionNode {
  frame: number
  local: (string | number)[]
  stack: (string | number)[]
}

export interface IincInstructionNode extends InstructionNode {
  var: number
  incr: number
}

export interface IntInstructionNode extends InstructionNode {
  operand: number
}

export interface InvokeDynamicInstructionNode extends InstructionNode {
  name: string
  desc: string
}

export interface JumpInstructionNode extends InstructionNode {
  labelIndex: number
}

export interface LabelInstructionNode extends InstructionNode {
  labelIndex: number
}

export interface LdcInstructionNode extends InstructionNode {
  cst: any
}

export interface LineNumberInstructionNode extends InstructionNode {
  line: number
  startLabelIndex: number
}

export interface LookupSwitchInstructionNode extends InstructionNode {
  dfltLabelIndex: number
}

export interface MultiANewArrayInstructionNode extends InstructionNode {
  desc: string
  dims: number
}

export interface MethodInstructionNode extends InstructionNode {
  owner: string
  name: string
  desc: string
}

export interface TableSwitchInstructionNode extends InstructionNode {
  min: number
  max: number
  dfltLabelIndex: number
  labelIndexes: number[]
}

export interface TypeInstructionNode extends InstructionNode {
  desc: string
}

export interface VarInstructionNode extends InstructionNode {
  var: number
}

interface Bridge {
  loadFile(): Promise<ZipEntry>
  findEntry(path: string, depth: number): Promise<ZipEntry>
  getTracePrinted(path: string): Promise<string>
  getFields(path: string): Promise<Field[]>
  getMethods(path: string): Promise<Method[]>
  getMethodInstructions(path: string, nd: string): Promise<InstructionNode[]>
}

declare global {
  const bridge: Bridge
}
