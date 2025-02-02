<template>
  <div class="bg-gray-100 shadow rounded-lg p-6">
    <h2 class="text-xl font-bold text-blue-600 cursor-pointer" @click="$emit('select', text.id)">
      {{ text.title }}
    </h2>
    <div class="mt-2">
      <div v-for="(chunk, index) in chunkedChapters" :key="index" class="flex flex-wrap justify-center gap-2">
        <span v-for="chapter in chunk" :key="chapter" class="px-3 py-1 mt-2 bg-blue-200 text-blue-700 rounded text-sm">
          {{ chapter }}
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, computed } from 'vue';

const props = defineProps({
  text: Object,
  maxPerRow: {
    type: Number,
    default: 8
  }
});

const chunkedChapters = computed(() => {
  const result = [];
  for (let i = 0; i < props.text.chapters.length; i += props.maxPerRow) {
    result.push(props.text.chapters.slice(i, i + props.maxPerRow));
  }
  return result;
});
</script>
