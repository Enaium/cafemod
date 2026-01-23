import type { Menu } from '@/components/CMenu.vue'
import { NHighlight, useDialog } from 'naive-ui'

const text =
  'UI: Naive UI, Based on Saucer, GitHub: Enaium, Repository: https://github.com/Enaium/cafemod'

export const HelpMenu: () => Menu = () => {
  const dialog = useDialog()
  return {
    name: 'Help',
    items: [
      {
        name: 'About',
        action: () => {
          dialog.success({
            title: 'About Cafemod',
            content: () => (
              <>
                <NHighlight text={text} patterns={['Naive UI', 'Saucer', 'GitHub', 'Repository']} caseSensitive/>
              </>
            ),
          })
        },
      },
    ],
  }
}
