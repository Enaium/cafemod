<script setup lang="tsx">
import { type MenuOption, NButton, NMenu, NPopselect } from 'naive-ui'

export interface Menu {
  name: string
  items: ReadonlyArray<MenuItem>
}

export interface MenuItem {
  name: string
  action: () => void
}

interface MenuProps {
  options: ReadonlyArray<Menu>
  mode?: 'horizontal' | 'vertical'
}

const props = withDefaults(defineProps<MenuProps>(), { mode: 'horizontal' })

const options: MenuOption[] = props.options.map((menu) => ({
  label: () => (
    <>
      <NPopselect
        trigger="click"
        options={menu.items.map((item) => ({ label: item.name, value: item.name }))}
        onUpdate:value={(name) => {
          menu.items.find((it) => it.name === name)?.action()
        }}
      >
        <NButton type="primary" text>
          {menu.name}
        </NButton>
      </NPopselect>
    </>
  ),
  key: menu.name,
}))

const CMenu = () => {
  return (
    <>
      <NMenu options={options} mode={props.mode} />
    </>
  )
}
</script>

<template>
  <CMenu />
</template>

<style scoped></style>
