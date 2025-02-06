<template>
  <div class="container mx-auto p-6 grid grid-cols-1 gap-6">
    <div class="col-span-4 text-center mb-4 relative">
      <h1 class="text-3xl font-bold inline-block">Текст: {{ title }}</h1>
      <button class="absolute right-0 top-0 mt-2 mr-4 bg-blue-500 text-white px-4 py-2 rounded">Редактировать</button>
    </div>
    <div>
      <ImageGallery :images="images" @open-slider="isSliderOpen = true" />
      <div class="mt-6">
        <TranslationList :translations="translations" />
      </div>
    </div>
    <ImageSlider v-if="isSliderOpen" :images="images" :isSliderOpen="isSliderOpen" @close-slider="isSliderOpen = false" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import ImageGallery from '@/pages/TextFragmentPage/components/ImageGallery.vue';
import ImageSlider from '@/pages/TextFragmentPage/components/ImageSlider.vue';
import TranslationList from '@/pages/TextFragmentPage/components/TranslationList.vue';
import api from '@/helpers/http/http';
import defaultImage from '@/assets/images/404.svg';
import { parseXmlComment } from '@/helpers/xml/xmlParser';

const route = useRoute();
const isSliderOpen = ref(false);
const images = ref([defaultImage]);
const translations = ref([]);
const title = ref('');

onMounted(async () => {
  const { id } = route.params;
  try {
    const response = await api.get(`/fragments/${id}`);
    title.value = `Страница ${response.pageNumber}`;
    images.value = response.imagesIDs.length
      ? response.imagesIDs.map(imgId => `/images/${imgId}.png`)
      : [defaultImage];

    translations.value = [{
      page: response.pageNumber,
      title: title.value,
      content: response.pureText,
      href: `/page/${id}`,
      showTranslation: false,
      translation: parseXmlComment(response.translationsXML)
    }];
  } catch (error) {
    console.error('Ошибка загрузки данных:', error);
  }
});
</script>
