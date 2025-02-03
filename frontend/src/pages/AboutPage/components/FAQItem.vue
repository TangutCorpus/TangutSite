<template>
  <details class="mb-2">
    <summary class="font-semibold">{{ question }}</summary>
    <p class="mt-2">{{ answer }}</p>
  </details>
</template>

<script setup>
import { defineProps } from 'vue';
const props = defineProps({ question: String, answer: String });
</script>