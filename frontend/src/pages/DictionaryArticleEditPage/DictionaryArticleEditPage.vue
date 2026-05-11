<template>
  <div class="narrow-container py-6">
    <div v-if="loading" class="text-center">{{ $t('common.loading') }}</div>

    <div v-else class="flex flex-col gap-6">
      <h1 class="header-md-text">
        {{ isNew ? $t('DictionaryEdit.createTitle') : $t('DictionaryEdit.editTitle') }}
      </h1>

      <div class="card-container">
        <h2 class="section-label mb-4">{{ $t('DictionaryEdit.sections.basic') }}</h2>
        <div class="grid grid-cols-12 gap-4">
          <div class="col-span-4">
            <label class="form-label">{{ $t('DictionaryEdit.character') }}</label>
            <input v-model="article.character" class="form-input text-4xl h-16 text-center" type="text" />
          </div>
          <div class="col-span-8 grid grid-cols-2 gap-4">
            <div>
              <label class="form-label">{{ $t('DictionaryEdit.unicodeCode') }}</label>
              <input v-model="article.unicodeCode" class="form-input" type="text" placeholder="U+XXXX" />
            </div>
            <div>
              <label class="form-label">{{ $t('DictionaryEdit.unicodeKey') }}</label>
              <input v-model="article.unicodeKey" class="form-input" type="text" />
            </div>
            <div>
              <label class="form-label">{{ $t('DictionaryEdit.strokeCountUnicode') }}</label>
              <input v-model.number="article.strokeCountUnicode" class="form-input" type="number" />
            </div>
            <div>
              <label class="form-label">{{ $t('DictionaryEdit.strokeCountTotal') }}</label>
              <input v-model.number="article.strokeCountTotal" class="form-input" type="number" />
            </div>
          </div>
        </div>
      </div>

      <div class="card-container">
        <CharacterComponentsEdit v-model:components="article.components" />
      </div>

      <div class="card-container">
        <h2 class="section-label mb-4">{{ $t('DictionaryEdit.sections.phonology') }}</h2>
        <div class="grid grid-cols-2 gap-6">
          <div>
            <label class="form-label">{{ $t('DictionaryEdit.tone') }}</label>
            <input v-model="article.tone" class="form-input" type="text" />
          </div>
          <div>
            <label class="form-label">{{ $t('DictionaryEdit.seaOfWriting') }}</label>
            <input v-model="article.seaOfWritingAnalysis" class="form-input" type="text" maxlength="4" />
          </div>
        </div>
      </div>

      <div class="card-container">
        <CompoundWordsEdit v-model:compounds="article.compoundWords" />
      </div>

      <div class="flex gap-4">
        <BaseButton
            class="flex-1"
            primary
            :disabled="isSaving"
            @click="handleSave"
        >
          {{ isSaving ? $t('common.saving') : $t('common.save') }}
        </BaseButton>

        <BaseButton class="w-32" @click="router.back()">
          {{ $t('common.cancel') }}
        </BaseButton>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, Ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'
import CharacterComponentsEdit from '@/pages/DictionaryArticleEditPage/components/CharacterComponentsEdit.vue'
import CompoundWordsEdit from '@/pages/DictionaryArticleEditPage/components/CompoundWordsEdit.vue'
import {
  getDictionaryArticle,
  updateDictionaryArticle,
  createDictionaryArticle
} from '@/helpers/http/sessions'
import type { DictionaryArticle } from '@/helpers/http/interfaces'

const route = useRoute()
const router = useRouter()
const loading = ref(true)
const isSaving = ref(false)

const currentId = (route.params.id as string) || ''

const isNew = computed(() => !currentId || currentId === 'new' || currentId === 'undefined')

const article: Ref<DictionaryArticle> = ref({
  id: '',
  character: '',
  unicodeCode: '',
  unicodeKey: '',
  strokeCountUnicode: 0,
  strokeCountTotal: 0,
  components: [],
  reconstructions: [],
  dictionaryReferences: [],
  initials: [],
  tone: '',
  rhymes: [],
  fanqie: [],
  seaOfWritingAnalysis: '',
  chineseCharacters: [],
  tibetanSyllables: [],
  sanskritSyllables: [],
  imageGroups: [],
  compoundWords: [],
  relatedWords: [],
  corpusExamples: []
})

const fetchArticleData = async () => {
  if (!isNew.value) {
    try {
      const data = await getDictionaryArticle(currentId)
      if (data) {
        article.value = data
      }
    } catch (e) {
      console.error("Error fetching dictionary article:", e)
    }
  }
  loading.value = false
}

onMounted(fetchArticleData)

const handleSave = async () => {
  if (isSaving.value) return
  isSaving.value = true

  try {
    if (isNew.value) {
      const { id, ...payload } = article.value
      const result = await createDictionaryArticle(payload as any)

      if (result) {
        const newId = typeof result === 'object' ? result.id : result
        await router.push(`/dict/${newId}`)
      }
    } else {
      const success = await updateDictionaryArticle(currentId, article.value)
      if (success) {
        await router.push(`/dict/${currentId}`)
      }
    }
  } catch (e) {
    console.error("Save failed:", e)
  } finally {
    isSaving.value = false
  }
}
</script>