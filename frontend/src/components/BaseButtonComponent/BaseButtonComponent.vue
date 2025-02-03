<template>
  <button
    :class="['px-5 py-3 font-medium rounded-md', primary ? 'bg-indigo-600 text-white hover:bg-indigo-700' : 'bg-gray-200 text-black hover:bg-gray-300']"
  >
    <slot />
  </button>
</template>

<script setup>
import { defineProps } from 'vue'

const props = defineProps({ primary: Boolean })
</script>