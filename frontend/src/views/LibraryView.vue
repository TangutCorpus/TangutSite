<template>
  <div class="bg-white min-h-screen flex flex-col">
    <h1 class="text-3xl font-bold text-center p-4">{{ componentName }}</h1>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const componentName = ref('Library')
</script>
