<script setup lang="tsx">
import type { ZipEntry } from '@/types/bridge'
import ClassFileContent from './ClassFileContent.vue'

const props = defineProps<{ entry: ZipEntry }>()

const ContentPane = () => {
  return (
    <>
      {props.entry.name.endsWith('.class') ? (
        <ClassFileContent entry={props.entry} class="w-full h-full" />
      ) : null}
    </>
  )
}
</script>

<template>
  <ContentPane />
</template>

<style scoped></style>
