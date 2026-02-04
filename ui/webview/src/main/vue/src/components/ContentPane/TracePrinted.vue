<script setup lang="tsx">
import type { ZipEntry } from '@/types/bridge'
import { CodeEditor } from 'monaco-editor-vue3'
import { useMessage } from 'naive-ui'
import { onMounted, ref } from 'vue'

const props = defineProps<{ entry: ZipEntry }>()

const message = useMessage()

const code = ref('')

onMounted(() => {
  bridge
    .getTracePrinted(props.entry.path)
    .then((r) => {
      code.value = r
    })
    .catch((e) => {
      message.error(e)
    })
})

const TracePrinted = () => {
  return (
    <>
      <CodeEditor
        value={code.value}
        class="w-full h-full"
        options={{ readOnly: true, automaticLayout: true, scrollBeyondLastLine: false }}
      />
    </>
  )
}
</script>

<template>
  <TracePrinted />
</template>

<style scoped></style>
