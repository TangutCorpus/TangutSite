<template>
  <div class="bg-gray-100 shadow rounded-lg p-6">
    <router-link :to="`/text/${text.id}`" class="text-xl font-bold text-blue-600 hover:underline">
      {{ text.title }}
    </router-link>

    <div class="mt-2">
      <div v-for="(chunk, index) in chunkedChapters" :key="index" class="flex flex-wrap justify-center gap-2">
        <router-link
          v-for="chapter in chunk"
          :key="chapter"
          :to="`/page/${chapter}`"
          class="px-3 py-1 mt-2 bg-blue-200 text-blue-700 rounded text-sm hover:bg-blue-300 transition"
        >
          {{ chapter }}
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, defineProps } from 'vue'

const props = defineProps({
  text: Object,
  maxPerRow: {
    type: Number,
    default: 8
  }
})

const chunkedChapters = computed(() => {
  const result = []
  for (let i = 0; i < props.text.lineIds.length; i += props.maxPerRow) {
    result.push(props.text.lineIds.slice(i, i + props.maxPerRow))
  }
  return result
})
</script>
