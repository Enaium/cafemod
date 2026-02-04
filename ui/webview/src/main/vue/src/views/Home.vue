<script setup lang="tsx">
import ToolMenu from '@/components/ToolMenu'
import FileTree from '@/components/FileTree.vue'
import ContentTab from '@/components/ContentTab.vue'
import { NSplit } from 'naive-ui'

const Home = () => {
  return (
    <>
      <div class="w-full h-full flex flex-col">
        <div class="w-full shrink-0 border-b border-gray-300">
          <ToolMenu />
        </div>
        <div class="w-full h-full flex flex-1 min-h-0">
          <NSplit
            direction="horizontal"
            max={0.75}
            min={0.25}
            defaultSize={0.25}
            v-slots={{ 1: () => <FileTree />, 2: () => <ContentTab /> }}
          />
        </div>
      </div>
    </>
  )
}
</script>

<template>
  <Home />
</template>

<style scoped></style>
