<template>
  <div class="container mx-auto p-6 grid grid-cols-1 gap-6">
    <div class="col-span-4 text-center mb-4 relative">
      <h1 class="text-3xl font-bold inline-block">Текст: Lorem Ipsum</h1>
      <button class="absolute right-0 top-0 mt-2 mr-4 bg-blue-500 text-white px-4 py-2 rounded">Редактировать</button>
    </div>
    <div class="">
      <ImageGallery :images="images" @open-slider="isSliderOpen = true" />
      <div class="mt-6">
        <TranslationList :texts="texts" />
      </div>
    </div>
    <ImageSlider v-if="isSliderOpen" :images="images" :isSliderOpen="isSliderOpen"
                 @close-slider="isSliderOpen = false" />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ImageGallery from '@/pages/TextFragmentPage/components/ImageGallery.vue'
import ImageSlider from '@/pages/TextFragmentPage/components/ImageSlider.vue'
import TranslationList from '@/pages/TextFragmentPage/components/TranslationList.vue'
import testImage from '@/assets/images/404.svg'

const images = ref([testImage, testImage, testImage])
const isSliderOpen = ref(false)

const texts = ref([
  {
    page: 1,
    title: 'Lorem Ipsum',
    content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    href: '/fragment/1',
    showTranslation: false,
    translation: {
      ru: 'Лорем ипсум долор сит амет, консектетур адиписцинг элит.',
      en: 'Oremlay ipsumyay olorday itsay ametyay, onsectetuercay adipiscingyay elityay'
    }
  },
  {
    page: 2,
    title: 'Sed Eiusmod',
    content: 'Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
    href: '/fragment/2',
    showTranslation: false,
    translation: {
      ru: 'Сед до эйусмод темпор инцидидунт ут лаборате эт долоре магна аликуа.',
      en: 'Edsay itaevay eolay inyay iamday empersay orttitorpay.'
    }
  }
])
</script>
