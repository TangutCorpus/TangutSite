<template>
  <div class="card-container">
    <h2 class="section-label">{{ $t('CompoundWordsCard.title') }}</h2>

    <div class="flex flex-col mt-2">
      <div
          v-for="(compound, idx) in compounds"
          :key="idx"
          class="flex items-start gap-4 py-2 border-b border-gray-50 last:border-0 hover:bg-gray-50 cursor-pointer rounded px-1 transition-colors"
          @click="$emit('articleClick', compound.articleId)"
      >
        <span class="text-3xl leading-none w-16 shrink-0">{{ compound.text }}</span>
        <div class="flex flex-col">
          <span class="text-sm font-medium text-gray-800">{{ compound.meaning }}</span>
          <span v-if="compound.note" class="text-xs text-gray-400 mt-0.5">{{ compound.note }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { CompoundWord } from '@/helpers/http/interfaces'

defineProps<{
  character: string
  compounds: CompoundWord[]
}>()

defineEmits<{ (e: 'articleClick', id: string): void }>()
</script>