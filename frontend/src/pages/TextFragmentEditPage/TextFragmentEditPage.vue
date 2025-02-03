<template>
  <div class="container mx-auto p-6 max-w-4xl">
    <div class="bg-white shadow rounded-lg p-6 relative">
      <label class="block text-lg font-semibold mb-2">Название текста</label>
      <input v-model="text.title" type="text" class="w-full p-2 border rounded mb-4">
      <ImageUpload :images="text.images" @update:images="updateImages" :single="false" class="mb-6" />
      <TranslationListEdit class="mb-6" />
      <BaseButton @click="saveText" class="mt-6 w-full">Сохранить</BaseButton>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue';
import ImageUpload from '@/pages/UserEditPage/components/ImageUpload.vue';
import TranslationListEdit from '@/pages/TextFragmentEditPage/components/TranslationListEdit.vue'

const text = ref({
  title: '',
  metadata: {},
  images: [],
  fragments: []
});

const saveText = () => {
  console.log('Сохранение текста', text.value);
};

const updateImages = (newImages) => {
  console.log(newImages);
  text.value.images = newImages;
};

</script>