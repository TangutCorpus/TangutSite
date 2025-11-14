<template>
  <div class="left-border-card">
    <h2 class="header-semibold-text">{{ $t('UserBiography.biography') }}</h2>
    <p class="text-gray-700 mt-2">{{ biography }}</p>
  </div>
</template>

<script setup>
const props = defineProps({
  biography: String
})
</script>
