<template>
  <div>
    <label class="block text-lg font-semibold mb-2">Редактировать переводы</label>

    <div class="bg-gray-100 p-6 rounded-lg mb-6 border-l-4 border-blue-500">
      <textarea v-model="originalText" class="w-full p-2 border rounded" placeholder="Оригинальный текст"></textarea>

      <button class="bg-green-500 text-white px-3 py-1 rounded mt-2" @click="addTranslation">Добавить перевод</button>

      <div v-for="(translation, tIndex) in translations" :key="tIndex" class="mt-2 flex items-center">
        <select v-model="translation.lang" class="p-2 border rounded mr-2">
          <option value="ru">Русский</option>
          <option value="en">English</option>
        </select>
        <textarea v-model="translation.text" class="p-2 border rounded w-full" placeholder="Введите перевод" />
        <button class="ml-2 text-red-500" @click="removeTranslation(index, tIndex)">✖</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, defineEmits, defineProps, ref } from 'vue'

const props = defineProps({
  originalText: String,
  translations: Array
})
const isTranslationPanelVisible = ref(false)
const emit = defineEmits(['update:translations', 'update:originalText'])

const translations = computed({
  get: () => props.translations || [],
  set: (newValue) => {
    emit('update:translations', newValue)
  }
})


const originalText = computed({
  get: () => props.originalText || [],
  set: (newValue) => {
    emit('update:originalText', newValue)
  }
})

const addTranslation = () => {
  translations.value.push({ lang: 'ru', text: '' })
}

const removeTranslation = (cardIndex, translationIndex) => {
  translations.value.splice(translationIndex, 1)
}
</script>
