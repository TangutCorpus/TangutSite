<template>
  <div class="bg-white min-h-screen flex flex-col">
    <NotFoundPage v-if="isNotFound" :image404="image404"/>
    <main v-else class="standard-flex-container flex-grow main-content-main">
      <router-view :key="$route.fullPath" />
    </main>
  </div>
</template>

<script lang="ts" setup>
import {computed} from 'vue'
import {useRoute} from 'vue-router'
import image404 from '@/assets/images/404.svg'
import NotFoundPage from "@/pages/NotFoundPage/NotFoundPage.vue";

const route = useRoute()

const isNotFound = computed(() => {
  return route.matched.length === 0
})
</script>

<style>
</style>
