<template>
  <div class="card-container bg-slate-50/50">
    <h2 class="section-label">{{ $t('RelatedWordsCard.title') }}</h2>
    <div class="grid grid-cols-2 gap-x-6 gap-y-1 mt-2">
      <div v-for="word in relatedWords" :key="word.language" class="flex text-sm">
        <span class="text-gray-500 w-20 shrink-0">{{ word.language }}:</span>
        <span class="italic font-medium text-gray-800">{{ word.value }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { RelatedWord } from '@/helpers/http/interfaces'
defineProps<{ relatedWords: RelatedWord[] }>()
</script>