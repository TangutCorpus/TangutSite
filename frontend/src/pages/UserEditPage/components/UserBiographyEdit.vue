<template>
  <div class="left-border-card">
    <h2 class="header-semibold-text">О пользователе</h2>
    <textarea v-model="biographyProxy" class="form-textarea" />
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({ biography: String })
const emit = defineEmits(['update:biography'])

const biographyProxy = computed({
  get: () => props.biography,
  set: (value) => emit('update:biography', value)
})
</script>
