<template>
  <div>
    <div v-if="texts.length > 0">
      <div v-for="(text, index) in texts" :key="index" class="bg-gray-100 p-6 rounded-lg mb-6 border-l-4 border-blue-500">
        <h2 class="text-lg font-semibold text-gray-900">Страница {{ text.pageNumber }}</h2>
        <blockquote class="italic text-gray-800 mt-2">{{ text.pureText }}</blockquote>
        <hr class="my-4 border-gray-300" />

        <h2 class="text-lg font-semibold flex items-center cursor-pointer text-blue-600"
            @click="toggleTranslation(index)">
          Переводы
          <span class="ml-2">{{ text.showTranslation ? '▴' : '▿' }}</span>
        </h2>

        <div v-if="text.showTranslation" class="mt-4">
          <div v-for="(translation, lang) in text.translations" :key="lang" class="mt-2">
            <h3 class="font-bold text-gray-700">{{ getLanguageName(lang).toUpperCase() }}</h3>
            <p class="text-gray-600">{{ translation }}</p>
          </div>
        </div>
      </div>
    </div>
    <div v-else class="bg-red-100 p-6 rounded-lg border-l-4 border-red-500 text-center">
      <h2 class="text-lg font-bold text-red-600">Ошибка</h2>
      <p class="w-full p-4 border rounded bg-white">Нет связанных страниц с этим текстом...</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, computed } from 'vue'
import { parseTranslationsXML } from '@/helpers/xml/xmlParser'

const props = defineProps({ translations: Array })
const texts = computed(() => props.translations.map(page => ({
  pageNumber: page.pageNumber,
  pureText: page.pureText,
  translations: parseTranslationsXML(page.translationsXML),
  showTranslation: false
})))

const toggleTranslation = index => texts.value[index].showTranslation = !texts.value[index].showTranslation
const getLanguageName = lang => ({ ru: 'Русский', en: 'English' }[lang] || lang)
</script>
