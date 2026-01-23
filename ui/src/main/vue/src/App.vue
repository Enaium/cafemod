<script setup lang="tsx">
import { RouterView } from 'vue-router'
import {
  NConfigProvider,
  NDialogProvider,
  NMessageProvider,
  NModalProvider,
  NNotificationProvider,
} from 'naive-ui'

const App = () => {
  return (
    <>
      <NConfigProvider>
        <NMessageProvider>
          <NDialogProvider>
            <NModalProvider>
              <NNotificationProvider>
                <div class="w-screen h-screen">
                  <RouterView />
                </div>
              </NNotificationProvider>
            </NModalProvider>
          </NDialogProvider>
        </NMessageProvider>
      </NConfigProvider>
    </>
  )
}
</script>
<template>
  <App />
</template>
