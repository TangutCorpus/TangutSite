<template>
  <div class="container mx-auto p-6 grid grid-cols-4 gap-6">
    <div class="col-span-4 text-center mb-4 relative">
      <h1 class="text-3xl font-bold inline-block">Текст: Lorem Ipsum</h1>
      <button class="absolute right-0 top-0 mt-2 mr-4 bg-blue-500 text-white px-4 py-2 rounded">Редактировать</button>
    </div>

    <div class="col-span-1 bg-white shadow rounded-lg p-6 self-start">
      <h1 class="text-2xl font-bold text-gray-900 flex items-center">
        <span>Lorem Ipsum</span>
        <button class="ml-2 text-gray-500" @click="toggleSection('section1')">▿</button>
      </h1>
      <div v-if="sections.section1">
        <p class="text-gray-600 mt-2">Найден: Ipsum Lorem (2025-02-01)</p>
        <p class="text-gray-600">Опубликован: Lorem Publication</p>
        <p class="text-gray-600">Хранится: Lorem Storage (№12345)</p>
        <p class="text-gray-600">Материал: Lorem Material</p>
        <p class="text-gray-600">Аналоги: Ipsum Dolor, Sit Amet</p>
        <p class="text-gray-600">Доп. информация: Дополнительные сведения об артефакте</p>
      </div>

      <h2 class="text-xl font-semibold mt-4 flex items-center">
        <span>Дополнительные данные</span>
        <button class="ml-2 text-gray-500" @click="toggleSection('section2')">▿</button>
      </h2>
      <div v-if="sections.section2">
        <p class="text-gray-600">Lorem: Ipsum</p>
        <p class="text-gray-600">Lorem: Dolor</p>
        <p class="text-gray-600">Lorem: Sit</p>
      </div>

      <h2 class="text-xl font-semibold mt-4 flex items-center">
        <span>Историческая информация</span>
        <button class="ml-2 text-gray-500" @click="toggleSection('section3')">▿</button>
      </h2>
      <div v-if="sections.section3">
        <p class="text-gray-600">Дата: 2025-02-01</p>
        <p class="text-gray-600">Описание: Исторический контекст Lorem Ipsum.</p>
      </div>
    </div>

    <div class="col-span-3">
      <div
        class="bg-white shadow rounded-lg p-6 relative max-h-[50vh] overflow-hidden flex items-center justify-center">
        <button class="absolute left-2 top-1/2 transform -translate-y-1/2 bg-gray-700 text-white p-2 rounded-full"
                @click="prevImage">◀
        </button>
        <img :src="images[currentImage]" class="w-auto max-h-full mx-auto cursor-pointer rounded-lg"
             @click="openSlider">
        <button class="absolute right-2 top-1/2 transform -translate-y-1/2 bg-gray-700 text-white p-2 rounded-full"
                @click="nextImage">▶
        </button>
      </div>

      <div class="mt-6">
        <div v-for="(text, index) in texts" :key="index" class="bg-gray-100 p-4 rounded-lg mb-4">
          <h2 class="text-xl font-semibold flex items-center">
              <span>
                <a :href="text.href" class="text-blue-500 underline" target="_blank">
                  [Стр. {{ text.page }}]
                </a> {{ text.title }}
              </span>
            <button class="ml-2 text-blue-500 underline" @click="toggleTranslation(index)">▿</button>
          </h2>
          <p class="text-gray-700 mt-2">{{ text.content }}</p>
          <div v-if="text.showTranslation" class="mt-2">
            <p class="text-gray-600">🇷🇺 {{ text.translation.ru }}</p>
            <p class="text-gray-600">🇬🇧 {{ text.translation.en }}</p>
          </div>
        </div>
      </div>
    </div>

    <div v-if="isSliderOpen" class="fixed inset-0 bg-black bg-opacity-80 flex items-center justify-center">
      <div class="relative bg-white p-8 rounded-lg shadow-lg max-w-4xl mx-auto">
        <button class="absolute top-4 right-4 bg-white text-black p-2 rounded" @click="closeSlider">✖</button>
        <button class="absolute top-4 right-16 bg-white text-black p-2 rounded" @click="openOriginalImage">🔗</button>
        <button class="absolute left-4 top-1/2 transform -translate-y-1/2 bg-white text-black p-2 rounded"
                @click="prevImage">◀
        </button>
        <img :src="images[currentImage]" class="max-w-full max-h-full object-contain rounded-lg">
        <button class="absolute right-4 top-1/2 transform -translate-y-1/2 bg-white text-black p-2 rounded"
                @click="nextImage">▶
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import testImage from '../assets/404.svg'

const images = ref([testImage, testImage, testImage])
const currentImage = ref(0)
const isSliderOpen = ref(false)

const sections = ref({ section1: true, section2: true, section3: true })
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
      ru: 'Сед до эйусмод темпор инцидидунт ут лаборате эт долоре магна аликуа.1',
      en: 'Edsay itaevay eolay inyay iamday empersay orttitorpay.'
    }
  }
])

const toggleSection = section => sections.value[section] = !sections.value[section]
const toggleTranslation = index => texts.value[index].showTranslation = !texts.value[index].showTranslation
const prevImage = () => currentImage.value = (currentImage.value - 1 + images.value.length) % images.value.length
const nextImage = () => currentImage.value = (currentImage.value + 1) % images.value.length
const openSlider = () => isSliderOpen.value = true
const closeSlider = () => isSliderOpen.value = false
const openOriginalImage = () => window.open(images.value[currentImage.value], '_blank')

onMounted(() => {
  const handleKeydown = (event) => {
    if (event.key === 'ArrowLeft') {
      prevImage()
    } else if (event.key === 'ArrowRight') {
      nextImage()
    } else if (event.key === 'Escape') {
      closeSlider()
    }
  }

  window.addEventListener('keydown', handleKeydown)

  return () => {
    window.removeEventListener('keydown', handleKeydown)
  }
})
</script>
