<template>
  <div class="mx-auto p-6 bg-white drop-shadow-lg rounded-lg">
    <h2 class="text-center header-md-text mb-4">Комментарии</h2>

    <div v-for="(comment, index) in comments" :key="index" class="mb-4">
      <div class="flexbox-center gap-2">
        <b>{{ index + 1 }}.</b>
        <p class="w-full p-3 border rounded bg-gray-200">{{ comment }}</p>
      </div>

      <hr v-if="index < comments.length - 1" class="my-4 border-t border-gray-300">
    </div>
  </div>
</template>

<script setup>
const props = defineProps({ comments: Array });
</script>
