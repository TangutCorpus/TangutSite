<template>
  <button
      :class="primary ? 'button-primary' : 'button-secondary'"
  >
    <slot/>
  </button>
</template>

<script setup>
const props = defineProps({primary: Boolean})
</script>