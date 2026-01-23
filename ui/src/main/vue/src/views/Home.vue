<script setup lang="tsx">
import ToolMenu from '@/components/ToolMenu'
import FileTree from '@/components/FileTree.vue'
import ContentTab from '@/components/ContentTab.vue'

const Home = () => {
  return (
    <>
      <div class="w-full h-screen flex flex-col">
        <div class="w-full shrink-0 border-b border-gray-300">
          <ToolMenu />
        </div>
        <div class="w-full h-full flex flex-1 min-h-0">
          <div class="h-full p-1 border-r border-gray-300 w-2/6 min-h-0">
            <FileTree />
          </div>
          <div class="h-full p-1 w-4/6 min-h-0">
            <ContentTab />
          </div>
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
