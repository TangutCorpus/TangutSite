<template>
  <div v-if="filteredTranslations.length">
    <blockquote class="italic text-gray-800 mt-2">{{ pureText }}</blockquote>
    <hr class="my-4 border-gray-300" />

    <h2 class="text-lg font-semibold flex items-center cursor-pointer text-blue-600" @click="toggleAll">
      Переводы
      <span class="ml-2">{{ showAll ? '▴' : '▿' }}</span>
    </h2>

    <div v-if="showAll" class="mt-4">
      <div v-for="(translation, index) in filteredTranslations" :key="translation.lang" class="mt-2">
        <h3 class="font-bold text-gray-700 cursor-pointer flex items-center" @click="toggleTranslation(index)">
          {{ getLanguageName(translation.lang).toUpperCase() }}
          <span class="ml-2">{{ shownTranslations[index] ? '▴' : '▿' }}</span>
        </h3>
        <p v-if="shownTranslations[index]" class="text-gray-600">{{ translation.text }}</p>
      </div>
    </div>
  </div>
  <div v-else class="bg-red-100 p-6 rounded-lg border-l-4 border-red-500 text-center">
    <h2 class="text-lg font-bold text-red-600">Ошибка</h2>
    <p class="w-full p-4 border rounded bg-white">Нет доступных переводов...</p>
  </div>
</template>

<script setup>
import { defineProps, ref, computed } from 'vue'

const props = defineProps({
  translations: Array,
  pureText: String
})

const filteredTranslations = computed(() =>
  props.translations.filter(t => t.text.trim() !== "")
)

const showAll = ref(false)
const shownTranslations = ref(filteredTranslations.value.map(() => false))

const toggleAll = () => {
  showAll.value = !showAll.value
}

const toggleTranslation = index => {
  shownTranslations.value[index] = !shownTranslations.value[index]
}

const getLanguageName = lang => ({ ru: 'Русский', en: 'English' }[lang] || lang)
</script>
