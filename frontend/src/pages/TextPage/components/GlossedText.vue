<template>
  <div>
    <div v-for="(block, index) in textBlocks" :key="index"
         class="bg-gray-100 p-6 rounded-lg mb-6 border-l-4 border-blue-500">
      <h2 class="text-lg font-bold"><a :href="`/fragment/${block.link}`">{{ block.title }}</a></h2>

      <pre class="bg-white p-4 border rounded">{{ block.text }}</pre>

      <button class="bg-green-500 text-white px-3 py-1 rounded mt-2" @click="toggleTranslations(index)">
        {{ block.showTranslations ? 'Скрыть комментарии' : 'Показать комментарии' }}
      </button>

      <div v-if="block.showTranslations" class="mt-4">
        <p class="bg-yellow-100 p-2 rounded">{{ block.gloss }}</p>
        <p class="bg-green-100 p-2 rounded mt-2">{{ block.comment }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue'

const props = defineProps({ textBlocks: Array })

const toggleTranslations = (index) => {
  props.textBlocks[index].showTranslations = !props.textBlocks[index].showTranslations
}
</script>
