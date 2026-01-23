import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import type { ZipEntry } from '@/types/bridge'

export const useFileTree = defineStore('fileTree', () => {
  const treeState = ref<ZipEntry>({
    name: '',
    path: '',
    type: 'DIRECTORY',
    parent: undefined,
    children: [],
  })
  const tree = computed(() => treeState.value)

  function setFileTree(tree: ZipEntry) {
    treeState.value = tree
  }

  return { tree, setFileTree }
})
