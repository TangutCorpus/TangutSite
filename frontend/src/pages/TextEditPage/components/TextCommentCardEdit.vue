<template>
  <div>
    <h2 class="text-lg font-bold mb-4">Редактировать комментарии</h2>
    <div v-for="(comment, index) in comments" :key="index" class="mb-2">
      <div class="flex items-center gap-2">
        <b>{{ index + 1 }}.</b>
        <input v-model="comments[index]" class="w-full p-2 border rounded" type="text" value="this.comment" />
      </div>
      <hr v-if="index < comments.length - 1" class="my-4 border-t border-gray-300">
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue'

const props = defineProps({ comments: Array })
</script>