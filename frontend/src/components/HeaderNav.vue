<template>
  <header class="flex justify-between items-center p-4">
    <h1 class="font-bold text-lg mr-6 cursor-pointer" @click="navigateTo('/')">
      Tangut Corpus
    </h1>
    <nav>
      <ul class="flex space-x-4">
        <li v-for="item in menuItems" :key="item.name"
            :class="['cursor-pointer', { 'font-bold': isActive(item.route) }]"
            @click="handleMenuItemClick(item.route)">
          {{ item.name }}
        </li>
      </ul>
    </nav>
  </header>
</template>

<script lang="ts" setup>
import { useRoute, useRouter } from 'vue-router'
import { defineEmits } from 'vue'

const route = useRoute()
const router = useRouter()
const emit = defineEmits(['toggleAuthForm'])

const menuItems = [
  { name: 'Библиотека', route: '/library' },
  { name: 'О нас', route: '/about' },
  { name: 'Вход', route: '/auth' }
]

const handleMenuItemClick = (path: string) => {
  if (path === '/auth') {
    emit('toggleAuthForm')
  } else {
    navigateTo(path)
  }
}

const navigateTo = (path: string) => {
  router.push(path)
}

const isActive = (path: string) => {
  return route.path === path
}
</script>
