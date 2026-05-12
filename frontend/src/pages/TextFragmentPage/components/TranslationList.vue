<template>
  <div class="max-w-4xl mx-auto space-y-8">
    <section class="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
      <div class="px-6 py-4 bg-gray-50 border-b border-gray-100 flex justify-between items-center">
        <span class="text-xs font-bold uppercase tracking-wider text-gray-500">{{ $t('TranslationList.original') || 'Оригинал' }}</span>
      </div>
      <div class="p-8">
        <BlockquoteComponent
            :text="pureText"
            class="text-2xl leading-relaxed text-gray-800 font-serif"
        />
      </div>
    </section>

    <div v-if="filteredTranslations.length" class="space-y-4">
      <div class="flex items-center space-x-4 px-2">
        <hr class="flex-grow border-gray-200"/>
        <button
            @click="toggleAll"
            class="flex items-center space-x-2 text-sm font-medium text-blue-600 hover:text-blue-800 transition-colors uppercase tracking-tight"
        >
          <span>{{ $t('TranslationList.translations') }}</span>
          <span class="transform transition-transform duration-200" :class="{'rotate-180': showAll}">▾</span>
        </button>
        <hr class="flex-grow border-gray-200"/>
      </div>

      <div v-if="showAll" class="grid grid-cols-1 gap-4 transition-all animate-in fade-in slide-in-from-top-4">
        <div
            v-for="(translation, index) in filteredTranslations"
            :key="translation.lang"
            class="group bg-white rounded-lg border border-gray-200 hover:border-blue-300 transition-all shadow-sm hover:shadow-md"
        >
          <div
              @click="toggleTranslation(index)"
              class="px-5 py-3 flex justify-between items-center cursor-pointer select-none"
          >
            <div class="flex items-center space-x-3">
              <span class="w-8 h-px bg-gray-300 group-hover:bg-blue-400 transition-colors"></span>
              <span class="text-xs font-black text-gray-400 group-hover:text-blue-600 uppercase tracking-widest">
                {{ getLanguageName(translation.lang) }}
              </span>
            </div>
            <span class="text-gray-400 group-hover:text-blue-500">
               {{ shownTranslations[index] ? '−' : '+' }}
            </span>
          </div>

          <div
              v-if="shownTranslations[index]"
              class="px-8 pb-6 animate-in zoom-in-95 duration-200"
          >
            <p class="text-lg leading-relaxed text-gray-700 font-sans whitespace-pre-line">
              {{ translation.text }}
            </p>
          </div>
        </div>
      </div>
    </div>

    <div v-else class="bg-amber-50 border border-amber-100 rounded-xl p-8 text-center">
      <div class="inline-flex items-center justify-center w-12 h-12 rounded-full bg-amber-100 text-amber-600 mb-4">
        !
      </div>
      <h2 class="text-amber-800 font-semibold">{{ $t('TranslationList.error') }}</h2>
      <p class="text-amber-600/80 text-sm mt-1">{{ $t('TranslationList.noTranslations') }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import {computed, ref, watch} from 'vue'
import BlockquoteComponent from "@/components/BlockquoteComponent/BlockquoteComponent.vue";
import {useI18n} from "vue-i18n";
import parseTranslationsXML from "@/helpers/xml/xmlParser.js";

const {t} = useI18n()

const props = defineProps({
  translations: String,
  pureText: String,
})

const filteredTranslations = computed(() =>
    parseTranslationsXML(props.translations).filter(t => t.text.trim() !== "")
)

const showAll = ref(false)
const shownTranslations = ref([])

watch(filteredTranslations, (newVal) => {
  shownTranslations.value = newVal.map(() => false)
}, { immediate: true })

const toggleAll = () => {
  showAll.value = !showAll.value
}

const toggleTranslation = index => {
  shownTranslations.value[index] = !shownTranslations.value[index]
}

const getLanguageName = lang => ({
  ru: t('languages.ru'),
  en: t('languages.en'),
  zh_cn: t('languages.zh_cn'),
  zh_tw: t('languages.zh_tw'),
}[lang] || lang)
</script>

<style scoped>
.font-serif {
  font-family: "Tangut", "Noto Serif Tangut", serif;
}
.font-sans {
  font-family: "Inter", "Segoe UI", Roboto, sans-serif;
}
</style>