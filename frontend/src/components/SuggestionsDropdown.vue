<template>
  <div
    v-if="show"
    class="absolute top-full mt-2 bg-white rounded-md shadow-md history-container"
    :style="{ width: width }"
  >
    <!-- Suggestions -->
    <div
      v-for="(suggestion, index) in suggestions"
      :key="'suggestion-' + index"
      @click="$emit('select', suggestion)"
      class="w-full px-4 py-3 border-b border-gray-200 last:border-b-0 hover:bg-gray-100 cursor-pointer block text-black"
    >
      {{ suggestion.title }}, {{ suggestion.author }}
    </div>
    <!-- History -->
    <div
      v-for="(history, index) in history"
      :key="'history-' + index"
      @click="$emit('select-history', history)"
      class="w-full px-4 py-3 border-b border-gray-200 last:border-b-0 hover:bg-gray-100 cursor-pointer block text-black"
    >
      {{ history }}
    </div>
  </div>
</template>

<script lang="ts" setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps<{
  history: string[];
  suggestions: { title: string; author: string; id: number }[];
  show: boolean;
  width: string;
}>();

const emit = defineEmits(['select', 'select-history']);
</script>

<style scoped>
.history-container {
  z-index: 10;
}
</style>
