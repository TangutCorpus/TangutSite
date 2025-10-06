<template>
  <div class="page-flex-container">
    <h1 class="header-first-level text-center">{{ $t('HomePage.title') }}</h1>
    <SearchBar />
  </div>
</template>

<script lang="ts" setup>
import SearchBar from '@/components/SearchBarComponent/SearchBar.vue'
</script>
