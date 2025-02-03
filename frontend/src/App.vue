<template>
  <div class="bg-white min-h-screen flex flex-col">
    <HeaderNav @toggle-auth-form="toggleAuthForm" />
    <MainContent
      :currentPage="currentPage"
      :navigateTo="navigateTo"
      @toggle-auth-modal="toggleAuthForm"
    />
    <AuthComponent v-if="showAuth" @close="toggleAuthForm" />
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import HeaderNav from '@/components/HeadernavComponent/HeaderNavComponent.vue'
import MainContent from '@/components/MainContentComponent/MainContentComponent.vue'
import AuthComponent from '@/components/AuthComponent/AuthComponent.vue'

const showAuth = ref(false)
const currentPage = ref<'home' | 'auth' | 'article'>('home')

const toggleAuthForm = () => {
  showAuth.value = !showAuth.value
}

const navigateTo = (page: 'home' | 'auth' | 'article') => {
  currentPage.value = page
}
</script>
