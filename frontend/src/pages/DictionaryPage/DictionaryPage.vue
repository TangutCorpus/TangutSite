<template>
  <div class="narrow-container">
    <div class="flex justify-between items-end mb-8 mt-4">
      <div>
        <h1 class="header-md-text">{{ $t('HeaderNavComponent.dictionary') }}</h1>
        <div class="text-sm text-gray-500 mt-1">
          {{ $t('DictionaryIndexPage.totalCount') }}: {{ articles.length }}
        </div>
      </div>

      <button
          class="button-primary flex items-center space-x-2"
          @click="router.push('/dict/add')"
      >
        <span class="text-xl">+</span>
        <span>{{ $t('DictionaryEdit.createTitle') }}</span>
      </button>
    </div>

    <div v-if="loading" class="text-center py-12">
      <div class="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-blue-700"></div>
      <p class="mt-2 text-gray-500">{{ $t('common.loading') }}</p>
    </div>

    <div v-else-if="articles.length > 0">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <SearchResultCard
            v-for="article in articles"
            :key="article.id"
            :result="article"
            mode="dict"
        />
      </div>
    </div>

    <div v-else class="text-center py-12 bg-gray-50 rounded-lg border-2 border-dashed">
      <p class="text-gray-500">{{ $t('DictionaryIndexPage.empty') || 'Статей пока нет' }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router' // Добавили роутер
import SearchResultCard from '@/pages/SearchResultPage/components/SearchResultCard.vue'
import { getAllDictionaryResults } from '@/helpers/http/sessions'
import type { DictionaryArticle } from '@/helpers/http/interfaces'

const router = useRouter()
const articles = ref<DictionaryArticle[]>([])
const loading = ref(true)

onMounted(async () => {
  loading.value = true
  try {
    const data = await getAllDictionaryResults()
    articles.value = data || []
  } catch (e) {
    console.error("Failed to fetch dictionary index:", e)
  } finally {
    loading.value = false
  }
})
</script>