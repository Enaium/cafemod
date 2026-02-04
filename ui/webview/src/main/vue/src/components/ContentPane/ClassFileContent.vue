<script setup lang="tsx">
import type { ZipEntry } from '@/types/bridge'
import { NTabPane, NTabs } from 'naive-ui'
import ClassMembers from './ClassMembers.vue'
import TracePrinted from './TracePrinted.vue'

const props = defineProps<{ entry: ZipEntry }>()

const ClassFileContent = () => {
  return (
    <>
      <NTabs placement="bottom" size="small" class="w-full h-full">
        <NTabPane key="trace-printed" name="Trace Printed" class="w-full h-full min-h-0">
          <TracePrinted entry={props.entry} />
        </NTabPane>
        <NTabPane key="members" name="Members" class="w-full h-full min-h-0">
          <ClassMembers entry={props.entry} />
        </NTabPane>
      </NTabs>
    </>
  )
}
</script>

<template>
  <ClassFileContent />
</template>

<style scoped></style>
