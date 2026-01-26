<script setup lang="tsx">
import type { ZipEntry } from '@/types/bridge'
import { onMounted, ref } from 'vue'
import { type DataTableColumns, NDataTable, NIcon, NScrollbar, useMessage } from 'naive-ui'
import Method from '@/assets/icons/Method.svg?component'
import Field from '@/assets/icons/Field.svg?component'
import AccessPublic from '@/assets/icons/AccessPublic.svg?component'
import AccessPrivate from '@/assets/icons/AccessPrivate.svg?component'
import AccessProtected from '@/assets/icons/AccessProtected.svg?component'
import StaticMark from '@/assets/icons/StaticMark.svg?component'
import FinalMark from '@/assets/icons/FinalMark.svg?component'
import { ACC_FINAL, ACC_PRIVATE, ACC_PROTECTED, ACC_PUBLIC, ACC_STATIC } from '@/types/opcodes.tsx'

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
  },
  {
    key: 'access',
    render: (row: Member) =>
      (row.access & ACC_PUBLIC) !== 0 ? (
        <AccessPublic />
      ) : (row.access & ACC_PRIVATE) !== 0 ? (
        <AccessPrivate />
      ) : (row.access & ACC_PROTECTED) !== 0 ? (
        <AccessProtected />
      ) : (
        <></>
      ),
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

const ClassMembers = () => {
  return (
    <>
      <NScrollbar class="w-full h-full">
        <NDataTable columns={columns} data={members.value} />
      </NScrollbar>
    </>
  )
}
</script>

<template>
  <ClassMembers />
</template>

<style scoped></style>
