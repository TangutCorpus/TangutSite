<template>
  <header class="flex justify-end p-4">
    <nav>
      <ul class="flex space-x-4">
        <li v-for="item in menuItems" :key="item.name"
            @click="navigateTo(item.route)"
            :class="['cursor-pointer', { 'font-bold': isActive(item.route) }]">
          {{ item.name }}
        </li>
      </ul>
    </nav>
  </header>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const menuItems = [
  { name: 'Главная', route: '/' },
  { name: 'Библиотека', route: '/library' },
  { name: 'Грамматика', route: '/grammar' },
  { name: 'О нас', route: '/about' },
  { name: 'Вход / Регистрация', route: '/auth' }
]

const navigateTo = (path: string) => {
  router.push(path)
}

const isActive = (path: string) => {
  return route.path === path
}
</script>
