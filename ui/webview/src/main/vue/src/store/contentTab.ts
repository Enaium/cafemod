import { defineStore } from 'pinia'
import type { ZipEntry } from '@/types/bridge'
import { computed, ref } from 'vue'

export const useContentTab = defineStore('contentTab', () => {
  const tabState = ref<ZipEntry[]>([])

  const tab = computed(() => tabState.value)

  function setContentTab(tab: ZipEntry[]) {
    tabState.value = tab
  }

  return { tab, setContentTab }
})
