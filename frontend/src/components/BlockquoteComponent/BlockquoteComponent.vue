<template>
  <blockquote class="italic text-gray-700 mt-2">{{ props.text }}</blockquote>
</template>

<script lang="ts" setup>
const props = defineProps({text: String})
</script>
