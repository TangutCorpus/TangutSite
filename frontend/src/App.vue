<template>
  <HeaderNav @toggle-auth-form="toggleAuthForm"/>
  <MainContent @toggle-auth-modal="toggleAuthForm"/>
  <AuthComponent v-if="showAuth" @close="toggleAuthForm"/>
</template>

<script lang="ts" setup>
import {ref} from 'vue'
import HeaderNav from '@/components/HeaderNavComponent/HeaderNavComponent.vue'
import MainContent from '@/components/MainContentComponent/MainContentComponent.vue'
import AuthComponent from '@/components/AuthComponent/AuthComponent.vue'

const showAuth = ref(false)

const toggleAuthForm = () => {
  showAuth.value = !showAuth.value
}
</script>
