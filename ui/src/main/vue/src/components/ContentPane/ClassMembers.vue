<script setup lang="tsx">
import type { ZipEntry } from '@/types/bridge'
import { onMounted, ref } from 'vue'
import { type DataTableColumns, NDataTable, NScrollbar, useMessage } from 'naive-ui'

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
    title: 'Access',
    key: 'access',
    width: 100,
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
      <NScrollbar xScrollable>
        <NDataTable columns={columns} data={members.value} singleLine={false} />
      </NScrollbar>
    </>
  )
}
</script>

<template>
  <ClassMembers />
</template>

<style scoped></style>
