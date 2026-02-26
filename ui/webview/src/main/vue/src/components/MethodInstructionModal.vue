<script setup lang="tsx">
import {
  type DataTableColumns,
  NCard,
  NDataTable,
  NGradientText,
  NModal,
  NScrollbar,
  NSpin,
} from 'naive-ui'
import { useQuery } from '@tanstack/vue-query'
import type {
  FieldInstructionNode,
  IincInstructionNode,
  InstructionNode,
  LineNumberInstructionNode,
  IntInstructionNode,
  InvokeDynamicInstructionNode,
  JumpInstructionNode,
  LdcInstructionNode,
  LookupSwitchInstructionNode,
  TableSwitchInstructionNode,
  MethodInstructionNode,
  TypeInstructionNode,
  VarInstructionNode,
  MultiANewArrayInstructionNode,
  FrameInstructionNode,
} from '@/types/bridge'
import {
  LABEL,
  LINE,
  FRAME,
  FIELD_INSN,
  IINC_INSN,
  INT_INSN,
  INVOKE_DYNAMIC_INSN,
  JUMP_INSN,
  LDC_INSN,
  LOOKUPSWITCH_INSN,
  METHOD_INSN,
  opcodes,
  TABLESWITCH_INSN,
  TYPE_INSN,
  VAR_INSN,
  MULTIANEWARRAY_INSN,
  F_NEW,
  F_FULL,
  F_APPEND,
  F_CHOP,
  F_SAME,
  F_SAME1,
} from '@/types/opcodes'
import type { LabelInstructionNode } from '@/types/bridge'

const show = defineModel<boolean>('show')
const props = defineProps<{ path: string; nd: string }>()

const columns: DataTableColumns<InstructionNode> = [
  {
    key: 'index',
    render: (_: InstructionNode, index: number) => <div>{`${index}`.padStart(5, '0')}</div>,
    width: 72,
  },
  {
    key: 'name',
    render: (row: InstructionNode) => (
      <div class="flex gap-1 text-nowrap">
        <NGradientText type="primary">
          {row.opcode === -1
            ? row.type === LABEL
              ? 'LABEL'
              : row.type === LINE
                ? 'LINE'
                : row.type === FRAME
                  ? 'FRAME'
                  : ''
            : opcodes[row.opcode]}
        </NGradientText>
        <div>
          {(() => {
            switch (row.type) {
              case FIELD_INSN: {
                const node = row as FieldInstructionNode
                return `${node.owner}.${node.name}${node.desc}`
              }
              case FRAME: {
                const node = row as FrameInstructionNode
                switch (node.frame) {
                  case F_NEW:
                  case F_FULL:
                    return 'FULL'
                  case F_APPEND:
                    return 'APPEND'
                  case F_CHOP:
                    return 'CHOP'
                  case F_SAME:
                    return 'SAME'
                  case F_SAME1:
                    return 'SAME1'
                  default:
                    return ''
                }
              }
              case IINC_INSN: {
                const node = row as IincInstructionNode
                return `${node.varIndex} ${node.increment}`
              }
              case INT_INSN: {
                const node = row as IntInstructionNode
                return `${node.operand}`
              }
              case INVOKE_DYNAMIC_INSN: {
                const node = row as InvokeDynamicInstructionNode
                return `${node.name}${node.desc}`
              }
              case JUMP_INSN: {
                const node = row as JumpInstructionNode
                return `LABEL ${node.labelIndex}`
              }
              case LABEL: {
                return (row as LabelInstructionNode).labelIndex
              }
              case LDC_INSN: {
                const node = row as LdcInstructionNode
                if (typeof node.cst === 'string') {
                  return <NGradientText type="primary">{node.cst}</NGradientText>
                } else {
                  return node.cst
                }
              }
              case LINE: {
                const node = row as LineNumberInstructionNode
                return `${node.line} LABEL ${node.startLabelIndex}`
              }
              case LOOKUPSWITCH_INSN: {
                return `LABEL ${(row as LookupSwitchInstructionNode).dfltLabelIndex}`
              }
              case METHOD_INSN: {
                const node = row as MethodInstructionNode
                return `${node.owner}.${node.name}${node.desc}`
              }
              case MULTIANEWARRAY_INSN: {
                const node = row as MultiANewArrayInstructionNode
                return `${node.desc} ${node.dims}`
              }
              case TABLESWITCH_INSN: {
                const node = row as TableSwitchInstructionNode
                return `${node.min} ${node.max} LABEL ${node.dfltLabelIndex} ${node.labelIndexes
                  .map((label) => `LABEL ${label}`)
                  .join(' ')}`
              }
              case TYPE_INSN:
                return (row as TypeInstructionNode).desc
              case VAR_INSN:
                return `${(row as VarInstructionNode).var}`
              default:
                return ''
            }
          })()}
        </div>
      </div>
    ),
  },
]

const { data } = useQuery({
  queryKey: ['getMethodInstructions'],
  queryFn: async () => bridge.getMethodInstructions(props.path, props.nd),
})

const Content = () => {
  return (
    <>
      <div class="w-full h-full flex justify-center items-center">
        <div class="w-90/100 h-90/100">
          <NCard
            class="w-full h-full"
            contentClass="w-full h-full min-h-0"
            closable
            onClose={() => (show.value = false)}
            title={() => (
              <NScrollbar xScrollable>
                <div class="text-nowrap">{props.nd}</div>
              </NScrollbar>
            )}
          >
            <NScrollbar xScrollable>
              <NDataTable columns={columns} data={data.value} />
            </NScrollbar>
          </NCard>
        </div>
      </div>
    </>
  )
}

const MethodInstructionModal = () => {
  return (
    <>
      <NModal v-model:show={show.value} maskClosable={false}>
        <div class="w-screen h-screen">{data.value ? <Content /> : <NSpin />}</div>
      </NModal>
    </>
  )
}
</script>

<template>
  <MethodInstructionModal />
</template>

<style scoped></style>
