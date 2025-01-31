<template>
  <div class="bg-white min-h-screen flex flex-col">
    <HeaderNav />
    <main class="flex flex-col items-center flex-grow">
      <router-view />
    </main>
  </div>
</template>

<script setup lang="ts">
import HeaderNav from './components/HeaderNav.vue'
</script>
