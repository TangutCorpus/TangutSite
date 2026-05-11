<template>
  <div class="card-container">
    <h2 class="section-label">{{ $t('CorpusExamplesCard.title') }}</h2>

    <div class="flex flex-col gap-6 mt-4">
      <div v-for="ex in examples" :key="ex.id" class="example-item">
        <div class="text-2xl mb-2 flex flex-wrap gap-1">
          <span
              v-for="(char, cIdx) in ex.originalText"
              :key="cIdx"
              :class="{ 'text-blue-600 font-bold': char === character }"
          >{{ char }}</span>
        </div>

        <div class="text-sm text-gray-600 italic border-l-2 border-gray-200 pl-3">
          {{ ex.translation }}
        </div>

        <div class="mt-2 flex items-center justify-between text-[11px]">
          <button
              class="text-blue-700 hover:underline"
              @click="$emit('textClick', ex.textId)"
          >
            {{ ex.sourceTitle }}
          </button>
          <span class="text-gray-400">{{ ex.location }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { CorpusExample } from '@/helpers/http/interfaces'

defineProps<{
  character: string
  examples: CorpusExample[]
}>()

defineEmits<{ (e: 'textClick', textId: string): void }>()
</script>