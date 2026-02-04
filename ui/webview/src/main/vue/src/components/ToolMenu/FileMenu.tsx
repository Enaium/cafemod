import type { Menu } from '@/components/CMenu.vue'
import type { ZipEntry } from '@/types/bridge'
import { useFileTree } from '@/store/fileTree.ts'
import { useMessage } from 'naive-ui'

const FileMenu: () => Menu = () => {
  const message = useMessage()
  const fileTree = useFileTree()
  return {
    name: 'File',
    items: [
      {
        name: 'Load File',
        action: () => {
          bridge
            .loadFile()
            .then((r: ZipEntry) => {
              fileTree.setFileTree(r)
            })
            .catch((e: string) => {
              message.error(e)
            })
        },
      },
    ],
  }
}
export default FileMenu
