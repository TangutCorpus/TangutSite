<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
    <div class="bg-white p-6 rounded-lg shadow-lg max-w-screen-lg w-full">
      <TextFragmentEdit v-model:fragment="tempFragment" />
      <BaseButton class="mt-4 w-full" @click="saveTempFragment">Сохранить</BaseButton>
      <BaseButton class="mt-4 w-full" @click="$emit('close')">Закрыть</BaseButton>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import TextFragmentEdit from '@/components/TextFragmentEdit/TextFragmentEdit.vue';
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue';

const props = defineProps({
  fragment: Object,
});
const emit = defineEmits(['close', 'saved']);
const tempFragment = ref({ ...props.fragment });

const saveTempFragment = () => {
  emit('saved', tempFragment.value);
  emit('close');
};
</script>