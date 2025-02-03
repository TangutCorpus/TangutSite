<template>
  <div class=" mx-auto p-6 bg-white drop-shadow-lg rounded-lg">
    <h2 class="text-center text-2xl font-bold mb-4">Комментарии</h2>

    <div v-for="(comment, index) in comments" :key="index" class="mb-4">
      <div class="flex items-center gap-2">
        <b>{{index + 1}}.</b>
        <p class="w-full p-3 border rounded bg-gray-100">{{comment}}</p>
      </div>

      <hr v-if="index < comments.length - 1" class="my-4 border-t border-gray-300">
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({ comments: Array });
</script>
