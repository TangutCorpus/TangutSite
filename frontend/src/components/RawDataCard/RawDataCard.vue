<template>
  <div class="card-container">
    <div class="flex border-b border-gray-200 mb-4 gap-1 text-sm overflow-x-auto">
      <button
          v-for="tab in tabs"
          :key="tab.key"
          class="px-3 py-1.5 whitespace-nowrap transition-colors"
          :class="activeTab === tab.key
          ? 'border-b-2 border-blue-600 text-blue-700 font-medium -mb-px'
          : 'text-gray-500 hover:text-gray-700'"
          @click="activeTab = tab.key"
      >
        {{ $t(`RawDataCard.tabs.${tab.key}`) }}
      </button>
    </div>

    <div v-if="activeTab === 'main'" class="flex flex-col gap-2">
      <DataRow :label="$t('RawDataCard.strokeCountTotal')" :value="String(strokeCountTotal)" />

      <DataRow :label="$t('RawDataCard.initials')">
        <template #value>
          <span
              v-for="init in initials"
              :key="init.romanization"
              class="inline-flex items-center gap-1 mr-2"
          >
            <span class="text-base">{{ init.character }}</span>
            <span class="text-gray-500 text-xs">({{ init.romanization }})</span>
            <SourcesPopup :sources="init.sources" />
          </span>
        </template>
      </DataRow>

      <DataRow :label="$t('RawDataCard.tone')" :value="tone" />

      <DataRow :label="$t('RawDataCard.rhymes')">
        <template #value>
          <span v-for="rhyme in rhymes" :key="rhyme.number" class="mr-2 text-sm">
            {{ $t('RawDataCard.rhymeLabel', { number: rhyme.number }) }}
            <span v-if="rhyme.chapter" class="text-gray-400 text-xs">• {{ rhyme.chapter }}</span>
            <SourcesPopup :sources="rhyme.sources" />
          </span>
        </template>
      </DataRow>

      <DataRow :label="$t('RawDataCard.fanqie')">
        <template #value>
          <span v-for="fq in fanqie" :key="fq.romanizedResult" class="mr-3 text-sm">
            <span class="text-lg">{{ fq.characters.join('') }}</span>
            <span class="text-gray-500 ml-1">→ {{ fq.romanizedResult }}</span>
            <SourcesPopup :sources="fq.sources" />
          </span>
        </template>
      </DataRow>
    </div>

    <div v-else-if="activeTab === 'correspondences'" class="flex flex-col gap-3">
      <div>
        <h3 class="text-xs text-gray-500 uppercase tracking-wide mb-1">
          {{ $t('RawDataCard.chineseCharacters') }}
        </h3>
        <div class="flex flex-wrap gap-2">
          <button
              v-for="ch in chineseCharacters"
              :key="ch.character"
              class="inline-flex items-center gap-1 px-2 py-0.5 rounded bg-amber-50 border border-amber-200
                   text-amber-900 text-sm hover:bg-amber-100 transition-colors"
              @click="ch.textId && $emit('textClick', ch.textId)"
          >
            {{ ch.character }}
            <span v-if="ch.textTitle" class="text-xs text-amber-600">▸ {{ ch.textTitle }}</span>
          </button>
        </div>
      </div>

      <div>
        <h3 class="text-xs text-gray-500 uppercase tracking-wide mb-1">
          {{ $t('RawDataCard.tibetanSyllables') }}
        </h3>
        <div class="flex flex-wrap gap-2">
          <span
              v-for="tib in tibetanSyllables"
              :key="tib.syllable"
              class="px-2 py-0.5 rounded bg-teal-50 border border-teal-200 text-teal-900 text-sm"
          >
            {{ tib.syllable }}
          </span>
        </div>
      </div>

      <div>
        <h3 class="text-xs text-gray-500 uppercase tracking-wide mb-1">
          {{ $t('RawDataCard.sanskritSyllables') }}
        </h3>
        <div class="flex flex-wrap gap-2">
          <span
              v-for="skt in sanskritSyllables"
              :key="skt.syllable"
              class="px-2 py-0.5 rounded bg-gray-100 border border-gray-200 text-gray-800 text-sm"
          >
            {{ skt.syllable }}
            <span class="text-gray-400 text-xs ml-1">({{ skt.language }})</span>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type {
  TangutInitial,
  TangutRhyme,
  Fanqie,
  ChineseCharacterEntry,
  TibetanSyllable,
  SanskritSyllable,
} from '@/helpers/http/interfaces'
import DataRow from '@/components/RawDataCard/subcomponents/DataRow.vue'
import SourcesPopup from '@/components/RawDataCard/subcomponents/SourcesPopup.vue'

defineProps<{
  strokeCountTotal: number
  initials: TangutInitial[]
  tone: string
  rhymes: TangutRhyme[]
  fanqie: Fanqie[]
  chineseCharacters: ChineseCharacterEntry[]
  tibetanSyllables: TibetanSyllable[]
  sanskritSyllables: SanskritSyllable[]
}>()

defineEmits<{ (e: 'textClick', textId: string): void }>()

const tabs = [
  { key: 'main' },
  { key: 'correspondences' },
] as const

const activeTab = ref<'main' | 'correspondences'>('main')
</script>