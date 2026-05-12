<template>
  <div class="border rounded-lg p-4 shadow-md bg-white hover:border-blue-300 transition-colors">
    <div v-if="mode === 'dict' && result.character">
      <div class="flex items-center gap-4">
        <span class="text-4xl font-serif text-slate-800">{{ result.character }}</span>
        <div>
          <h3 class="text-lg font-semibold text-slate-900">{{ result.unicodeCode }}</h3>
          <p class="text-sm text-slate-500">{{ result.unicodeKey }}</p>
        </div>
      </div>
      <div class="mt-3 flex gap-2">
        <span v-if="result.tone" class="px-2 py-1 bg-slate-100 rounded text-xs"> {{ $t('RawDataCard.tone') }} : {{ result.tone }}</span>
      </div>
      <router-link
          :to="`/dict/${result.id}`"
          class="text-blue-700 mt-4 inline-block font-medium hover:underline"
      >
        {{ $t('SearchResultCard.readFarther') }} →
      </router-link>
    </div>

    <div v-else-if="mode === 'texts' && (result.title || result.pureText)">
      <h3 class="font-bold text-lg mb-2 text-slate-900">
        {{ result.title }}
      </h3>
      <p class="text-gray-700 line-clamp-3 leading-relaxed">
        {{ result.pureText }}
      </p>
      <router-link
          :to="`/text/${result.textId || result.id}`"
          class="text-blue-700 mt-3 inline-block font-medium hover:underline"
      >
        {{ $t('SearchResultCard.readFarther') }} →
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  result: any,
  mode: 'texts' | 'dict'
}>()
</script>