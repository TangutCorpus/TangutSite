<template>
  <div class="bg-gray-50 border-l-4 border-blue-500 p-4">
    <h2 class="text-lg font-semibold">О пользователе</h2>
    <p class="text-gray-700 mt-2">{{ biography }}</p>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

defineProps({
  biography: String
});
</script>
