<template>
  <div>
    <h1 class="block text-lg font-semibold mb-2 text-center">Страница #{{fragment.pageNumber}}</h1>
    <label class="block text-lg font-semibold mb-2">ID текста</label>
    <input disabled v-model="fragment.textId" class="w-full p-2 border rounded mb-4" type="text">
    <ImageUpload :images="fragment.images" :single="false" class="mb-4" @update:images="updateImages" />
    <TranslationListEdit v-model:translations="fragment.translationsXML" v-model:original-text="fragment.pureText" class="mb-4" />
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';
import ImageUpload from '@/pages/UserEditPage/components/ImageUpload.vue';
import TranslationListEdit from '@/pages/TextFragmentEditPage/components/TranslationListEdit.vue';

const props = defineProps({
  fragment: Object,
});
const emit = defineEmits(['update:fragment']);

const updateImages = (newImages) => {
  emit('update:fragment', { ...props.fragment, images: newImages });
};

</script>