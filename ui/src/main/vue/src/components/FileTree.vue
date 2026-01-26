<script setup lang="tsx">
import {
  NIcon,
  NTree,
  type TreeOption,
  type TreeOverrideNodeClickBehavior,
  useMessage,
} from 'naive-ui'
import { Folder, FileTrayFull } from '@vicons/ionicons5'
import { useFileTree } from '@/store/fileTree.ts'
import type { ZipEntry } from '@/types/bridge'
import { ref, watch } from 'vue'
import type { TreeOptions } from 'naive-ui/es/tree/src/interface'
import Class from '@/assets/icons/Class.svg?component'
import { useContentTab } from '@/store/contentTab.ts'

const message = useMessage()
const fileTree = useFileTree()
const contentTab = useContentTab()

const tree = ref<TreeOptions>()

const zipEntry2TreeOption = (zipEntry: ZipEntry): TreeOption => {
  return {
    label: zipEntry.name,
    key: zipEntry.path,
    prefix: () => (
      <NIcon size={16}>
        {zipEntry.type === 'FILE' ? (
          zipEntry.name.endsWith('.class') ? (
            <Class />
          ) : (
            <FileTrayFull />
          )
        ) : (
          <Folder />
        )}
      </NIcon>
    ),
    isLeaf: zipEntry.type === 'FILE',
  }
}

function load(node: TreeOption) {
  return new Promise<void>((resolve, reject) => {
    bridge
      .findEntry(node.key as string, 1)
      .then((r: ZipEntry) => {
        node.children = r.children
          .map((child: ZipEntry) => zipEntry2TreeOption(child))
          .sort((item) => (item.isLeaf ? 1 : -1))
        resolve()
      })
      .catch((e) => {
        message.error(e)
        reject()
      })
  })
}

watch(fileTree, () => {
  tree.value = fileTree.tree.children
    .map((item) => zipEntry2TreeOption(item))
    .sort((item) => (item.isLeaf ? 1 : -1))
})

const nodeProps = ({ option }: { option: TreeOption }) => {
  return {
    onDblclick() {
      if (option.isLeaf) {
        bridge.findEntry(option.key as string, 0).then((r: ZipEntry) => {
          if (contentTab.tab.find((item: ZipEntry) => item.path === r.path) === undefined) {
            contentTab.tab.push(r)
          }
        })
      }
    },
  }
}

const override: TreeOverrideNodeClickBehavior = ({ option }) => {
  if (!option.isLeaf) {
    return 'toggleExpand'
  }
  return 'default'
}

const FileTree = () => {
  return (
    <>
      <NTree
        block-line
        showLine
        data={tree.value}
        onLoad={load}
        nodeProps={nodeProps}
        overrideDefaultNodeClickBehavior={override}
        virtualScroll
      />
    </>
  )
}
</script>

<template>
  <FileTree />
</template>

<style scoped></style>
