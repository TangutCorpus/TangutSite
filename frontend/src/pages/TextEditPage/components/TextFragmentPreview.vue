<template>
  <div>
    <div v-if="!isUnavailable" class="bg-gray-100 p-6 rounded-lg mb-6 border-l-4 border-blue-500 shadow">
      <h2 class="text-lg font-semibold text-gray-900">Страница #{{ fragment.pageNumber }}</h2>

      <blockquote class="italic text-gray-800 mt-2">{{ fragment.pureText }}</blockquote>
      <hr class="my-4 border-gray-300" />

      <h2 class="text-lg font-semibold flex items-center cursor-pointer text-blue-600" @click="toggleTranslation">
        Переводы
        <span class="ml-2">{{ showTranslation ? '▴' : '▿' }}</span>
      </h2>

      <div v-if="showTranslation" class="mt-4">
        <div v-for="translation in fragment.translationsXML" class="mt-2">
          <div v-if="translation['text']">
            <h3 class="font-bold text-gray-700">{{ getLanguageName(translation['lang']) }}</h3>
            <p class="text-gray-600">{{ translation['text'] }}</p>
          </div>
        </div>
      </div>

      <BaseButton class="mt-2 w-full" @click="$emit('edit', fragment)">Редактировать</BaseButton>
    </div>
    <div v-else class="bg-red-100 p-6 rounded-lg border-l-4 border-red-500 text-center shadow">
      <h2 class="text-lg font-bold text-red-600">Ошибка</h2>
      <p class="w-full p-4 border rounded bg-white">Страница недоступна...</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref } from 'vue'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'

const props = defineProps({
  fragment: Object
})

const showTranslation = ref(false)
const toggleTranslation = () => showTranslation.value = !showTranslation.value

const isUnavailable = ref(false)
const getLanguageName = lang => ({ ru: 'Русский', en: 'English' }[lang] || lang)
</script>