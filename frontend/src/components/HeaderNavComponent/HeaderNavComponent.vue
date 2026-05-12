<script lang="ts" setup>
import { useRoute, useRouter } from 'vue-router'
import { computed, ref, onMounted } from 'vue'
import { useI18n } from "vue-i18n";
import LanguageSwitch from "@/components/LanguageSwitch/LanguageSwitch.vue";
import { getCurrentUser } from "@/helpers/http/sessions";

const route = useRoute()
const router = useRouter()
const { t } = useI18n()
const emit = defineEmits(['toggleAuthForm'])

const currentUserId = ref("")

const menuItems = computed(() => {
  const items = [
    { name: t('HeaderNavComponent.aboutPage'), route: '/about' },
    { name: t('HeaderNavComponent.dictionary'), route: '/dict' },
    { name: t('HeaderNavComponent.library'), route: '/library' }
  ]

  if (!currentUserId.value) {
    items.push({ name: t('HeaderNavComponent.signIn'), route: '/auth' })
  } else {
    items.push(
        { name: t('HeaderNavComponent.profile'), route: `/user/${currentUserId.value}` }
    )
  }

  return items
})

const handleMenuItemClick = (path: string) => {
  if (path === '/auth') {
    emit('toggleAuthForm')
  } else {
    router.push(path)
  }
}

const navigateTo = (path: string) => {
  router.push(path)
}

const isActive = (path: string) => {
  return route.path === path
}

onMounted(async () => {
  const currentUser = await getCurrentUser()
  if (currentUser) {
    currentUserId.value = currentUser.id
  }
})
</script>

<template>
  <header class="flex justify-between items-center p-4">
    <h1 class="font-bold text-lg mr-6 cursor-pointer" @click="navigateTo('/')">
      Tangut Corpus
    </h1>
    <nav>
      <ul class="flex space-x-4">
        <LanguageSwitch/>
        <li v-for="item in menuItems" :key="item.route"
            :class="['cursor-pointer', { 'font-bold': isActive(item.route) }]"
            @click="handleMenuItemClick(item.route)">
          {{ item.name }}
        </li>
      </ul>
    </nav>
  </header>
</template>