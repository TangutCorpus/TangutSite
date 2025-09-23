<template>
  <img :src="src" alt="User Avatar" class="profile-picture">
</template>

<script setup>
const props = defineProps({
  src: {
    type: String,
    required: true
  }
})
</script>