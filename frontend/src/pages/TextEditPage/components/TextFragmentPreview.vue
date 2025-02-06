<template>
  <div class="border p-4 rounded-lg shadow">
    <h3 class="text-lg font-bold">{{ fragment.title }}</h3>
    <p v-if="fragment.translations.length" class="text-sm text-gray-600">
      {{ fragment.translations[0].content }}
    </p>
    <BaseButton class="mt-2 w-full" @click="$emit('edit', fragment.id)">Редактировать</BaseButton>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue';

const props = defineProps({
  fragment: Object
});
</script>
