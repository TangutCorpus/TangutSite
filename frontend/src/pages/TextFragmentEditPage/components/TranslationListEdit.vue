<template>
  <div>
    <label class="block header-semibold-text">{{$t('TranslationListEdit.editTranslations')}}</label>

    <div class="rounded-lg mb-6 left-border-card">
      <textarea v-model="originalText" class="form-textarea" placeholder="{{$t('TranslationListEdit.originalText')}}" />

      <button class="button-primary" @click="addTranslation">{{$t('TranslationListEdit.addTranslation')}}</button>

      <div v-for="(translation, tIndex) in translations" :key="tIndex" class="mt-2 flexbox-center">
        <select v-model="translation.lang" class="p-2 border rounded mr-2">
          <option value="ru">{{$t('TranslationListEdit.russian')}}</option>
          <option value="en">{{$t('TranslationListEdit.english')}}</option>
        </select>
        <textarea v-model="translation.text" class="form-textarea" placeholder="{{$t('TranslationListEdit.enterTranslation')}}" />
        <button class="ml-2 text-red-500" @click="removeTranslation(index, tIndex)">✖</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'

const props = defineProps({
  originalText: String,
  translations: Array
})
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
