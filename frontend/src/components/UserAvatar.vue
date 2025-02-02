<template>
  <img :src="src" alt="User Avatar" class="w-32 h-32 rounded-full shadow">
</template>

<script setup>
import { defineProps } from 'vue';

defineProps({
  src: {
    type: String,
    required: true
  }
});
</script>
