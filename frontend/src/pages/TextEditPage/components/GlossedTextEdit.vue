<template>
  <div>
    <div v-for="(block, index) in textBlocks" :key="index" class="m-2 p-6 bg-white border-2 rounded-lg">
      <input v-model="block.title" class="w-full p-2 border rounded mb-2" placeholder="Название" type="text" />
      <textarea v-model="block.text" class="w-full p-2 border rounded mb-2" placeholder="Текст"></textarea>
      <textarea v-model="block.gloss" class="w-full p-2 border rounded mb-2" placeholder="Глосса"></textarea>
      <textarea v-model="block.comment" class="w-full p-2 border rounded" placeholder="Комментарий"></textarea>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue'

const props = defineProps({ textBlocks: Array })
</script>
