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
import { ACC_PRIVATE, ACC_PROTECTED, ACC_PUBLIC, ACC_STATIC } from '@/types/opcodes.tsx'

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
    title: 'Icon',
    key: 'icon',
    render: (row: Member) => (
      <NIcon size={16}>
        {row.desc.startsWith('(') ? <Method /> : <Field />}
        {(row.access & ACC_PUBLIC) !== 0 ? (
          <AccessPublic />
        ) : (row.access & ACC_PRIVATE) !== 0 ? (
          <AccessPrivate />
        ) : (row.access & ACC_PROTECTED) !== 0 ? (
          <AccessProtected />
        ) : (row.access & ACC_STATIC) !== 0 ? (
          <StaticMark />
        ) : (
          <></>
        )}
      </NIcon>
    ),
    width: 24,
  },
  {
    title: 'Name',
    key: 'name',
  },
  {
    title: 'Descriptor',
    key: 'desc',
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
      <NDataTable
        columns={columns}
        data={members.value}
        bordered={false}
        size="small"
        virtualScroll
        headerHeight={0}
      />
    </>
  )
}
</script>

<template>
  <ClassMembers />
</template>

<style scoped></style>
