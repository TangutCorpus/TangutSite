<template>
  <div v-if="isOpen" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
    <div class="bg-white p-6 rounded-lg shadow-lg max-w-lg w-full">
      <h2 class="text-xl font-bold mb-4">Редактирование фрагмента</h2>
      <TextFragmentEdit :fragmentId="fragmentId" @saved="onSaved" />
      <BaseButton class="mt-4 w-full" @click="$emit('close')">Закрыть</BaseButton>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';
import TextFragmentEdit from '@/pages/TextFragmentEditPage/TextFragmentEditPage.vue';
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue';

const props = defineProps({
  isOpen: Boolean,
  fragmentId: Number
});

const emit = defineEmits(['close', 'saved']);

const onSaved = (id) => {
  emit('saved', id);
  emit('close');
};
</script>