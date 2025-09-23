<template>
  <div>
    <h2 class="header-semibold-text">Контактные данные</h2>
    <p class="text-gray-700">Email: {{ email }}</p>
    <p class="text-gray-700">Ф.И.О.: {{ displayName }}</p>
  </div>
</template>

<script setup>
const props = defineProps({
  email: String,
  displayName: String
})
</script>
