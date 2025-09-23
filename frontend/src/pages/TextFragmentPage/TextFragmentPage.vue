<template>
  <div class="narrow-container grid grid-cols-1 gap-6">
    <div class="col-span-4 text-center mb-4 relative">
      <h1 class="header-md-text inline-block">Текст: {{ textTitle }}. Страница #{{ page.pageNumber }}</h1>
      <button @click="editTextFragment" class="absolute right-0 top-0 mt-2 mr-4 button-helper">Редактировать</button>
    </div>

    <div>
      <ImageGallery :images="images" @open-slider="isSliderOpen = true" />
      <div class="mt-6">
        <TranslationList :translations="page.translationsXML" :pureText="page.pureText" />
      </div>
    </div>

    <ImageSlider v-if="isSliderOpen" :images="images" :isSliderOpen="isSliderOpen" @close-slider="isSliderOpen = false" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import ImageGallery from '@/pages/TextFragmentPage/components/ImageGallery.vue';
import ImageSlider from '@/pages/TextFragmentPage/components/ImageSlider.vue';
import TranslationList from '@/pages/TextFragmentPage/components/TranslationList.vue';
import api from '@/helpers/http/http';
import defaultImage from '@/assets/images/404.svg';

const route = useRoute();
const router = useRouter()
const isSliderOpen = ref(false);
const images = ref([]);
const page = ref({
  textId: "",
  pageNumber: 0,
  pureText: "",
  translationsXML: [],
  imagesIDs: []
});
const textTitle = ref('')

onMounted(async () => {
  const { id } = route.params;
  try {
    const { data } = await api.get(`/pages/${id}`);
    page.value = data;
    textTitle.value = (await api.get(`/texts/${data.textId}`)).data.title
    page.value.translationsXML = JSON.parse(data.translationsXML);
    if (data.imagesIDs && data.imagesIDs.length > 0) {
      const imageRequests = data.imagesIDs.map(async (imageId) => {
        try {
          const imageURL = `/images/${imageId}`
          await api.get(imageURL)
          return 'http://0.0.0.0:8080' + imageURL;
        } catch {
          return defaultImage;
        }
      });

      images.value = await Promise.all(imageRequests);
    } else {
      images.value = [defaultImage];
    }
    console.log(images.value)
  } catch (error) {
    console.error('Ошибка загрузки данных:', error);
    images.value = [defaultImage];
  }
});

const editTextFragment = () => {
  router.push(`${route.params.id}/edit`)
}
</script>
