<template>
  <div class="bg-white min-h-screen flex flex-col">
    <Header :currentPage="currentPage" @navigate="navigateTo" @toggleAuthModal="toggleAuthModal" />
    <h1 v-if="currentPage !== 'home' || isSecondMode" class="second-title absolute top-4 left-4 text-3xl font-bold">
      Tangut
    </h1>
    <NotFound v-if="show404" :image404="image404" />
    <main class="flex flex-col items-center flex-grow main-content-main">
      <component :is="currentComponent" />
    </main>
    <AuthModal v-if="showAuthModal" @close="toggleAuthModal" />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUpdated } from 'vue';
import Header from './HeaderNav.vue';
import NotFound from './NotFoundPage.vue';
import AuthModal from './AuthModal.vue';
import Home from '../views/HomeView.vue';

import image404 from '../assets/404.svg';

const currentPage = ref<'home' | 'auth' | 'article'>('home');
const selectedSuggestion = ref<string | null>(null);
const showAuthModal = ref(false);
const show404 = ref(false);
const isSecondMode = ref(false);

const currentComponent = computed(() => {
  if (selectedSuggestion.value) return SearchResults;
  switch (currentPage.value) {
    case 'home': return Home;
    case 'auth': return AuthModal;
    default: return NotFound;
  }
});

const navigateTo = (page: typeof currentPage.value) => {
  currentPage.value = page;
  selectedSuggestion.value = null;
  isSecondMode.value = false;
  show404.value = page !== 'home';
};

const toggleAuthModal = () => {
  showAuthModal.value = !showAuthModal.value;
};

onMounted(() => {});
onUpdated(() => {});
</script>

<style>
.second-title {
  position: absolute;
  top: 1rem;
  left: 1rem;
}
</style>
