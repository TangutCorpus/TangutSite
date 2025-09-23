<template>
  <div v-if="filteredTranslations.length">
    <BlockquoteComponent :text="pureText" />
    <hr class="my-4 border-gray-300" />
    <h2 class="header-semibold-text flexbox-center cursor-pointer text-blue-700" @click="toggleAll">
      Переводы
      <span class="ml-2">{{ showAll ? '▴' : '▿' }}</span>
    </h2>

    <div v-if="showAll" class="mt-4">
      <div v-for="(translation, index) in filteredTranslations" :key="translation.lang" class="mt-2">
        <h3 class="font-bold text-gray-700 cursor-pointer flexbox-center" @click="toggleTranslation(index)">
          {{ getLanguageName(translation.lang).toUpperCase() }}
          <span class="ml-2">{{ shownTranslations[index] ? '▴' : '▿' }}</span>
        </h3>
        <p v-if="shownTranslations[index]" class="text-gray-700">{{ translation.text }}</p>
      </div>
    </div>
  </div>
  <div v-else class="error-card">
    <h2 class="header-error-text">Ошибка</h2>
    <p class="text-error">Нет доступных переводов...</p>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import BlockquoteComponent from "@/components/BlockquoteComponent/BlockquoteComponent.vue";

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
