<template>
  <div class="w-full flex flex-col mt-[15%] items-center">
    <h1 class="first-title font-bold text-6xl mb-2">Tangut</h1>
    <p class="text-gray-600 mb-4 text-center">
      Онлайн-библиотека и словарь тангутского языка
    </p>
    <SearchBar />
  </div>
</template>

<script setup lang="ts">
import SearchBar from '../components/SearchBar.vue'
</script>
