<script setup lang="tsx">
import CMenu, { type Menu } from '@/components/CMenu.vue'
import FileMenu from '@/components/ToolMenu/FileMenu.tsx'
import { HelpMenu } from '@/components/ToolMenu/HelpMenu.tsx'

const menus: Menu[] = [FileMenu(), HelpMenu()]

const ToolMenu = () => {
  return (
    <>
      <CMenu options={menus} />
    </>
  )
}
</script>

<template>
  <ToolMenu />
</template>

<style scoped></style>
