<template>
  <div>
    <component :is="tag" class="font-semibold flex items-center">
      <span>{{ title }}</span>
      <button class="ml-2 text-gray-500" @click="isOpen = !isOpen">▿</button>
    </component>
    <div v-if="isOpen" class="mt-2">
      <slot></slot>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref } from 'vue'

const props = defineProps({
  title: String,
  tag: {
    type: String,
    default: 'h2'
  }
})
const isOpen = ref(true)
</script>