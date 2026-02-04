<script setup lang="tsx">
import { NDropdown, type MenuProps } from 'naive-ui'
import type { DropdownMixedOption } from 'naive-ui/es/dropdown/src/interface'

export interface DropdownItem {
  name: string
  action: () => void
  children?: DropdownItem[]
}

interface DropdownProps {
  options: DropdownItem[]
  x: number
  y: number
}

const show = defineModel<boolean>('show')

const props = defineProps<DropdownProps>()

const item2Options = (item: DropdownItem): DropdownMixedOption => {
  return {
    label: item.name,
    key: item.name,
    children: item.children?.map((item: DropdownItem) => item2Options(item)),
  }
}

const action = (item: DropdownItem, name: string) => {
  if (item.name === name) {
    item.action()
    return
  }

  const find = item.children?.find((item: DropdownItem) => item.name === name)
  if (find) {
    find.action()
  } else {
    item?.children?.forEach((item: DropdownItem) => {
      action(item, name)
    })
  }
}

const options: DropdownMixedOption[] = props.options.map((item) => item2Options(item))

const select = (name: string) => {
  props.options.forEach((item: DropdownItem) => {
    action(item, name)
  })
}

const CDropdown = () => {
  return (
    <>
      <NDropdown
        placement="bottom-start"
        trigger="manual"
        v-model:show={show.value}
        x={props.x}
        y={props.y}
        options={options}
        onSelect={select}
        onClickoutside={() => (show.value = false)}
      />
    </>
  )
}
</script>

<template>
  <CDropdown />
</template>

<style scoped></style>
