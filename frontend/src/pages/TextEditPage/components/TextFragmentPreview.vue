<template>
  <div>
    <div v-if="!isUnavailable" class="mb-6 left-border-card shadow">
      <h2 class="header-semibold-text text-gray-700">Страница #{{ fragment.pageNumber }}</h2>
      <BlockquoteComponent :text="fragment.pureText" />
      <hr class="my-4 border-gray-300" />

      <h2 class="header-semibold-text flexbox-center cursor-pointer text-blue-700" @click="toggleTranslation">
        Переводы
        <span class="ml-2">{{ showTranslation ? '▴' : '▿' }}</span>
      </h2>

      <div v-if="showTranslation" class="mt-4">
        <div v-for="translation in fragment.translationsXML" class="mt-2">
          <div v-if="translation['text']">
            <h3 class="font-bold text-gray-700">{{ getLanguageName(translation['lang']) }}</h3>
            <p class="text-gray-700">{{ translation['text'] }}</p>
          </div>
        </div>
      </div>

      <BaseButton class="mt-2 w-full" @click="$emit('edit', fragment)">Редактировать</BaseButton>
    </div>
    <div v-else class="error-card">
      <h2 class="header-error-text">Ошибка</h2>
      <p class="text-error">Страница недоступна...</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'
import BlockquoteComponent from "@/components/BlockquoteComponent/BlockquoteComponent.vue";

const props = defineProps({
  fragment: Object
})

const showTranslation = ref(false)
const toggleTranslation = () => showTranslation.value = !showTranslation.value

const isUnavailable = ref(false)
const getLanguageName = lang => ({ ru: 'Русский', en: 'English' }[lang] || lang)
</script>