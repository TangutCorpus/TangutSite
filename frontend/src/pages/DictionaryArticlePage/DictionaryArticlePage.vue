<template>
  <div v-if="article" class="narrow-container grid grid-cols-12 gap-6">
    <div class="col-span-12 text-center mt-4 relative">
      <h1 class="header-md-text">{{ $t('DictionaryArticlePage.title') }}</h1>
      <BaseButton class="absolute right-0 top-0" @click="goToEdit">
        {{ $t('DictionaryArticlePage.edit') }}
      </BaseButton>
    </div>

    <div class="col-span-4 flex flex-col gap-4">
      <CharacterCard
          :character="article.character"
          :unicode-code="article.unicodeCode"
          :unicode-key="article.unicodeKey"
          :stroke-count-unicode="article.strokeCountUnicode"
          :components="article.components"
          :sea-of-writing-analysis="article.seaOfWritingAnalysis"
          @component-click="navigateToArticle"
      />

      <ReconstructionsCard :reconstructions="article.reconstructions" />

      <DictionaryReferencesCard
          :references="article.dictionaryReferences"
          :available-dictionaries="availableDictionaries"
          @add-reference="handleAddReference"
          @remove-reference="handleRemoveReference"
      />
    </div>

    <div class="col-span-8 flex flex-col gap-4">
      <RawDataCard
          :stroke-count-total="article.strokeCountTotal"
          :initials="article.initials"
          :tone="article.tone"
          :rhymes="article.rhymes"
          :fanqie="article.fanqie"
          :chinese-characters="article.chineseCharacters"
          :tibetan-syllables="article.tibetanSyllables"
          :sanskrit-syllables="article.sanskritSyllables"
      />

      <CharacterImagesCard :image-groups="article.imageGroups" />

      <CompoundWordsCard
          :character="article.character"
          :compounds="article.compoundWords"
          @article-click="navigateToArticle"
      />

      <RelatedWordsCard :related-words="article.relatedWords" />

      <CorpusExamplesCard
          :character="article.character"
          :examples="article.corpusExamples"
          @text-click="navigateToText"
      />
    </div>
  </div>

  <div v-else-if="loading" class="narrow-container text-center mt-12">
    <p class="text-gray-500">{{ $t('DictionaryArticlePage.loading') }}</p>
  </div>

  <div v-else>
    <NotFoundPage />
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import type { Ref } from 'vue'

import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'
import NotFoundPage from '@/pages/NotFoundPage/NotFoundPage.vue'

import CharacterCard from '@/components/CharacterCard/CharacterCard.vue'
import ReconstructionsCard from '@/components/ReconstructionsCard/ReconstructionsCard.vue'
import DictionaryReferencesCard from '@/components/DictionaryReferencesCard/DictionaryReferencesCard.vue'
import RawDataCard from '@/components/RawDataCard/RawDataCard.vue'
import CharacterImagesCard from '@/components/CharacterImagesCard/CharacterImagesCard.vue'
import CompoundWordsCard from '@/components/CompoundWordsCard/CompoundWordsCard.vue'
import RelatedWordsCard from '@/components/RelatedWordsCard/RelatedWordsCard.vue'
import CorpusExamplesCard from '@/components/CorpusExamplesCard/CorpusExamplesCard.vue'

import type {
  DictionaryArticle,
  DictionaryReference,
  DictionaryInfo,
} from '@/helpers/http/interfaces'
import {
  getDictionaryArticle,
  getAvailableDictionaries,
  addDictionaryReference,
  removeDictionaryReference,
} from '@/helpers/http/sessions'

const route = useRoute()
const router = useRouter()
const article: Ref<DictionaryArticle | null> = ref(null)
const availableDictionaries: Ref<DictionaryInfo[]> = ref([])
const loading = ref(true)

const characterId = route.params.id as string

onMounted(async () => {
  loading.value = true
  try {
    const [articleData, dictsData] = await Promise.allSettled([
      getDictionaryArticle(characterId),
      getAvailableDictionaries(),
    ])

    if (articleData.status === 'fulfilled') {
      article.value = articleData.value
    }

    if (dictsData.status === 'fulfilled') {
      availableDictionaries.value = dictsData.value || []
    }
  } catch (e) {
    console.error("Critical fetch error:", e)
  } finally {
    loading.value = false
  }
})

const goToEdit = () => router.push(`/dict/${characterId}/edit`)

const navigateToArticle = (id: string) => router.push(`/dict/${id}`)

const navigateToText = (textId: string) => router.push(`/text/${textId}`)

const handleAddReference = async (ref: DictionaryReference) => {
  if (!article.value) return
  const updated = await addDictionaryReference(characterId, ref)
  article.value.dictionaryReferences = updated
}

const handleRemoveReference = async (dictionaryId: string) => {
  if (!article.value) return
  const updated = await removeDictionaryReference(characterId, dictionaryId)
  article.value.dictionaryReferences = updated
}
</script>