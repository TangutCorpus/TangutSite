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
import { defineEmits, ref, watchEffect } from 'vue'

const route = useRoute()
const router = useRouter()
const emit = defineEmits(['toggleAuthForm'])
const currentUserId = ref(localStorage.getItem('userId'))
const standardMenuItems = [
  { name: 'О нас', route: '/about' },
  { name: 'Библиотека', route: '/library' }
]
const menuItems = ref(standardMenuItems)

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

const updateMenuItems = () => {
  menuItems.value = []
  menuItems.value.push(...standardMenuItems)
  if (!currentUserId.value) {
    menuItems.value.push({ name: 'Вход', route: '/auth' })
  } else {
    menuItems.value.push(
      { name: 'Добавить текст', route: '/text/add' },
      { name: 'Профиль', route: `/user/${currentUserId.value}` }
    )
  }
}

watchEffect(() => {
  currentUserId.value = localStorage.getItem('userId')
  updateMenuItems()
})

updateMenuItems()
</script>
