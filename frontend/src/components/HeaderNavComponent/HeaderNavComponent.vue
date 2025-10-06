<template>
  <header class="flex justify-between items-center p-4">
    <h1 class="font-bold text-lg mr-6 cursor-pointer" @click="navigateTo('/')">
      Tangut Corpus
    </h1>
    <nav>
      <ul class="flex space-x-4">
        <LanguageSwitch />
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
import {computed, ref, watchEffect} from 'vue'
import {useI18n} from "vue-i18n";
import LanguageSwitch from "@/components/LanguageSwitch/LanguageSwitch.vue";

const route = useRoute()
const router = useRouter()
const { t } = useI18n()
const emit = defineEmits(['toggleAuthForm'])
const currentUserId = ref(localStorage.getItem('userId'))
const standardMenuItems = [
  { name: computed(() => t('HeaderNavComponent.aboutPage')), route: '/about' },
  { name: computed(() => t('HeaderNavComponent.library')), route: '/library' }
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
    menuItems.value.push({ name: t('HeaderNavComponent.signIn'), route: '/auth' })
  } else {
    menuItems.value.push(
      { name: t('HeaderNavComponent.addText'), route: '/text/add' },
      { name: t('HeaderNavComponent.profile'), route: `/user/${currentUserId.value}` }
    )
  }
}

watchEffect(() => {
  currentUserId.value = localStorage.getItem('userId')
  updateMenuItems()
})

updateMenuItems()
</script>
