<template>
  <div
    v-if="show"
    :style="{ width: width }"
    class="absolute top-full mt-2 bg-white rounded-md shadow-md history-container"
  >
    <div
      v-for="(suggestion, index) in suggestions"
      :key="'suggestion-' + index"
      class="w-full px-4 py-3 border-b border-gray-200 last:border-b-0 hover:bg-gray-100 cursor-pointer block text-black"
      @click="$emit('select', suggestion)"
    >
      {{ suggestion.title }}, {{ suggestion.author }}
    </div>
    <div
      v-for="(history, index) in history"
      :key="'history-' + index"
      class="w-full px-4 py-3 border-b border-gray-200 last:border-b-0 hover:bg-gray-100 cursor-pointer block text-black"
      @click="$emit('select-history', history)"
    >
      {{ history }}
    </div>
  </div>
</template>

<script lang="ts" setup>
import { defineProps } from 'vue'

const props = defineProps<{
  history: string[];
  suggestions: { title: string; author: string; id: number }[];
  show: boolean;
  width: string;
}>()
</script>

<style scoped>
.history-container {
    z-index: 10;
}
</style>
