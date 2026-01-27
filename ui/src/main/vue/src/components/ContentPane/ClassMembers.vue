<script setup lang="tsx">
import type { ZipEntry } from '@/types/bridge'
import { nextTick, onMounted, ref } from 'vue'
import { type DataTableColumns, NDataTable, NIcon, NScrollbar, useMessage } from 'naive-ui'
import Method from '@/assets/icons/Method.svg?component'
import Field from '@/assets/icons/Field.svg?component'
import AccessPublic from '@/assets/icons/AccessPublic.svg?component'
import AccessPrivate from '@/assets/icons/AccessPrivate.svg?component'
import AccessProtected from '@/assets/icons/AccessProtected.svg?component'
import StaticMark from '@/assets/icons/StaticMark.svg?component'
import FinalMark from '@/assets/icons/FinalMark.svg?component'
import { ACC_FINAL, ACC_PRIVATE, ACC_PROTECTED, ACC_PUBLIC, ACC_STATIC } from '@/types/opcodes'
import CDropdown from '@/components/ContentPane/CDropdown.vue'
import MethodInstructionModal from '@/components/MethodInstructionModal.vue'

const props = defineProps<{ entry: ZipEntry }>()

const message = useMessage()

interface Member {
  name: string
  desc: string
  access: number
}

const members = ref<Member[]>([])

const columns: DataTableColumns<Member> = [
  {
    key: 'type',
    render: (row: Member) => (
      <NIcon size={16}>
        <div class="absolute">{row.desc.startsWith('(') ? <Method /> : <Field />}</div>
        <div class="absolute">{(row.access & ACC_STATIC) !== 0 ? <StaticMark /> : <></>}</div>
        <div class="absolute">{(row.access & ACC_FINAL) !== 0 ? <FinalMark /> : <></>}</div>
      </NIcon>
    ),
    width: 32,
  },
  {
    key: 'access',
    render: (row: Member) => (
      <NIcon size={16}>
        {(row.access & ACC_PUBLIC) !== 0 ? (
          <AccessPublic />
        ) : (row.access & ACC_PRIVATE) !== 0 ? (
          <AccessPrivate />
        ) : (row.access & ACC_PROTECTED) !== 0 ? (
          <AccessProtected />
        ) : (
          <></>
        )}
      </NIcon>
    ),
    width: 32,
  },
  {
    key: 'name',
    render: (row: Member) => <div class="text-nowrap">{row.name}</div>,
  },
  {
    key: 'desc',
    render: (row: Member) => <div class="text-nowrap">{row.desc}</div>,
  },
]

onMounted(() => {
  bridge
    .getFields(props.entry.path)
    .then((fields) => {
      members.value.push(
        ...fields.map((item) => ({ name: item.name, desc: item.desc, access: item.access })),
      )

      bridge
        .getMethods(props.entry.path)
        .then((methods) => {
          members.value.push(
            ...methods.map((item) => ({ name: item.name, desc: item.desc, access: item.access })),
          )
        })
        .catch((e) => {
          message.error(e)
        })
    })
    .catch((e) => {
      message.error(e)
    })
})

const showDropdown = ref<boolean>(false)
const x = ref<number>(0)
const y = ref<number>(0)
const showMethodInstructionModal = ref<boolean>(false)
const currentNd = ref<string>('')

const rowProps = (row: Member) => {
  return {
    onContextmenu: (e: MouseEvent) => {
      e.preventDefault()
      showDropdown.value = false
      currentNd.value = ''
      nextTick().then(() => {
        currentNd.value = `${row.name}:${row.desc}`
        showDropdown.value = true
        x.value = e.clientX
        y.value = e.clientY
      })
    },
  }
}

const ClassMembers = () => {
  return (
    <>
      <NScrollbar xScrollable>
        <NDataTable columns={columns} data={members.value} rowProps={rowProps} />
      </NScrollbar>
      <CDropdown
        x={x.value}
        y={y.value}
        show={showDropdown.value}
        options={[
          {
            name: 'Show Instructions',
            action: () => {
              showMethodInstructionModal.value = true
            },
          },
        ]}
      />
      {showMethodInstructionModal.value ? (
        <MethodInstructionModal
          v-model:show={showMethodInstructionModal.value}
          path={props.entry.path}
          nd={currentNd.value}
        />
      ) : null}
    </>
  )
}
</script>

<template>
  <ClassMembers />
</template>

<style scoped></style>
