<template>
  <div class="bg-gray-50 border-l-4 border-blue-500 p-4">
    <h2 class="text-lg font-semibold">О пользователе</h2>
    <textarea v-model="biographyProxy" class="w-full p-2 border rounded mt-2"></textarea>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, computed } from 'vue';

const props = defineProps({ biography: String });
const emit = defineEmits(['update:biography']);

const biographyProxy = computed({
  get: () => props.biography,
  set: (value) => emit('update:biography', value)
});
</script>
