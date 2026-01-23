<script setup lang="tsx">
import { NTabPane, NTabs } from 'naive-ui'
import { useContentTab } from '@/store/contentTab.ts'
import ContentPane from '@/components/ContentPane'
import { ref, watch } from 'vue'

const contentTab = useContentTab()

const handleClose = (name: string) => {
  contentTab.tab.splice(
    contentTab.tab.findIndex((item) => item.name === name),
    1,
  )
}

const currentTab = ref<string>()

watch(contentTab, () => {
  currentTab.value = contentTab.tab[contentTab.tab.length - 1]?.name
})

const ContentTab = () => {
  return (
    <>
      <NTabs
        type="card"
        closable
        onClose={handleClose}
        v-model:value={currentTab.value}
        class="w-full h-full"
        size="small"
      >
        {contentTab.tab.map((item) => (
          <NTabPane key={item.path} name={item.name} class="w-full h-full min-h-0">
            <ContentPane entry={item} />
          </NTabPane>
        ))}
      </NTabs>
    </>
  )
}
</script>

<template>
  <ContentTab />
</template>

<style scoped></style>
