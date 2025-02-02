<template>
  <div>
    <div v-for="(text, index) in texts" :key="index" class="bg-gray-100 p-6 rounded-lg mb-6 border-l-4 border-blue-500">
      <blockquote class="italic text-gray-800">{{ text.content }}</blockquote>
      <hr class="my-4 border-gray-300" />

      <h2 class="text-lg font-semibold flex items-center cursor-pointer text-blue-600" @click="toggleTranslation(index)">
        Переводы
        <span class="ml-2">{{ text.showTranslation ? '▴' : '▿' }}</span>
      </h2>

      <div v-if="text.showTranslation" class="mt-4">
        <div v-for="(translation, lang) in text.translation" :key="lang" class="mt-2">
          <h3 class="font-bold text-gray-700">{{ getLanguageName(lang).toLocaleUpperCase() }}</h3>
          <p class="text-gray-600">{{ translation }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';
const props = defineProps({ texts: Array });
const toggleTranslation = index => props.texts[index].showTranslation = !props.texts[index].showTranslation;
const getLanguageName = lang => ({ ru: 'Русский', en: 'English' }[lang] || lang);
</script>