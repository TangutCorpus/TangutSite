<template>
  <div class="bg-white min-h-screen flex flex-col">
    <NotFound v-if="isNotFound" :image404="image404" />
    <main v-else class="flex flex-col items-center flex-grow main-content-main">
      <router-view />
    </main>
  </div>
</template>

<script lang="ts" setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import NotFound from '@/pages/NotFoundPage/components/NotFoundPage.vue'
import image404 from '@/assets/images/404.svg'

const route = useRoute()

const isNotFound = computed(() => {
  return route.matched.length === 0
})
</script>

<style>
</style>
