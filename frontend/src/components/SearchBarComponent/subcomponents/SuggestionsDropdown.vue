<template>
  <div
      v-if="show"
      :style="{ width: width }"
      class="absolute top-full mt-2 bg-white rounded-md shadow-md history-container"
  >
    <div
        v-for="(suggestion, index) in suggestions"
        :key="'suggestion-' + index"
        class="dropdown-item"
        @click="$emit('select', suggestion)"
    >
      {{ suggestion.title }}, {{ suggestion.author }}
    </div>
    <div
        v-for="(history, index) in history"
        :key="'history-' + index"
        class="dropdown-item"
        @click="$emit('select-history', history)"
    >
      {{ history }}
    </div>
  </div>
</template>

<script lang="ts" setup>
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
