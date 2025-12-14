<template>
  <div>
    <div v-if="!isUnavailable" class="mb-6 left-border-card shadow">
      <h2 class="header-semibold-text text-gray-700">{{ $t('TextFragmentPreview.page') }} #{{
          fragment.pageNumber
        }}</h2>
      <BlockquoteComponent :text="fragment.pureText"/>
      <hr class="my-4 border-gray-300"/>

      <h2 class="header-semibold-text flexbox-center cursor-pointer text-blue-700" @click="toggleTranslation">
        Переводы
        <span class="ml-2">{{ showTranslation ? '▴' : '▿' }}</span>
      </h2>

      <div v-if="showTranslation" class="mt-4">
        <div v-for="translation in parseTranslationsXML(fragment.translationsXML)" class="mt-2">
          <div v-if="translation['text']">
            <h3 class="font-bold text-gray-700">{{ getLanguageName(translation['lang']) }}</h3>
            <p class="text-gray-700">{{ translation['text'] }}</p>
          </div>
        </div>
      </div>

      <BaseButton class="mt-2 w-full" @click="$emit('edit', fragment)">{{ $t('TextFragmentPreview.edit') }}</BaseButton>
    </div>
    <div v-else class="error-card">
      <h2 class="header-error-text">{{ $t('TextFragmentPreview.error') }}</h2>
      <p class="text-error">{{ $t('TextFragmentPreview.notAccessible') }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'
import BlockquoteComponent from "@/components/BlockquoteComponent/BlockquoteComponent.vue";
import {useI18n} from "vue-i18n";
import {parseTranslationsXML} from "@/helpers/xml/xmlParser";

const {t} = useI18n()

const props = defineProps({
  fragment: Object
})
const showTranslation = ref(false)
const toggleTranslation = () => showTranslation.value = !showTranslation.value

const isUnavailable = ref(false)
const getLanguageName = lang => ({ru: t('TranslationList.russian'), en: t('TranslationList.english')}[lang] || lang)
</script>