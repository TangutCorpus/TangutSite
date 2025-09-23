<template>
  <blockquote class="italic text-gray-700 mt-2">{{ props.text }}</blockquote>
</template>

<script setup lang="ts">
const props = defineProps({text: String})
</script>
