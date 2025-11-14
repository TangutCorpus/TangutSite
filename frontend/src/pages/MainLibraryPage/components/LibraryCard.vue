<template>
  <div class="card-container">
    <router-link :to="`/text/${text.id}`" class="text-xl font-bold text-blue-700 hover:underline">
      {{ text.title }}
    </router-link>

    <div class="mt-2">
      <div v-for="(chunk, index) in chunkedChapters" :key="index" class="flex flex-wrap justify-center gap-2">
        <router-link
            v-for="chapter in chunk"
            :key="chapter"
            :to="`/page/${chapter}`"
            class="compact-padding mt-2 bg-gray-200 text-black rounded text-sm hover:bg-gray-400 transition"
        >
          {{ ++index }}
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {computed} from 'vue'

const props = defineProps({
  text: Text,
  maxPerRow: {
    type: Number,
    default: 8
  }
})

const chunkedChapters = computed(() => {
  const result = []
  for (let i = 0; i < props.text.pageIds.length; i += props.maxPerRow) {
    result.push(props.text.pageIds.slice(i, i + props.maxPerRow))
  }
  return result
})
</script>
