<template>
  <div class="text-center">
    <img class="w-32 h-32 rounded-full mx-auto mb-2" :src="image" />
    <h3 class="text-xl font-semibold">{{ name }}</h3>
    <p class="text-gray-600">{{ role }}</p>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';
const props = defineProps({ name: String, role: String, image: String });
</script>
