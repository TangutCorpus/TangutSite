<template>
  <div>
    <h2 class="text-lg font-semibold mb-2">Контактные данные</h2>
    <p class="text-gray-600">Email: {{ email }}</p>
    <p class="text-gray-600">Ф.И.О.: {{ displayName }}</p>
  </div>
</template>

<script setup>
import { defineProps } from 'vue'

defineProps({
  email: String,
  displayName: String
})
</script>
