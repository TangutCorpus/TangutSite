<template>
  <div class="flex flex-col items-center">
    <img :src="avatarPreview" alt="User Avatar" class="w-32 h-32 rounded-full shadow mb-2">
    <input type="file" @change="handleFileUpload" accept="image/*" class="hidden" ref="fileInput">
    <BaseButton @click="triggerFileInput">Изменить аватар</BaseButton>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';
import BaseButton from '@/components/BaseButton.vue';

const props = defineProps({ modelValue: String });
const emit = defineEmits(['update:modelValue']);
const fileInput = ref(null);
const avatarPreview = ref(props.modelValue);

const triggerFileInput = () => fileInput.value.click();

const handleFileUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = () => {
      avatarPreview.value = reader.result;
      emit('update:modelValue', reader.result);
    };
    reader.readAsDataURL(file);
  }
};

watch(() => props.modelValue, (newValue) => {
  avatarPreview.value = newValue;
});
</script>
