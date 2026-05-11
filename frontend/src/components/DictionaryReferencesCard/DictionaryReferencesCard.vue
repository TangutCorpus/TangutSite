<template>
  <div class="card-container">
    <h2 class="section-label">{{ $t('DictionaryReferencesCard.title') }}</h2>

    <div class="flex flex-col gap-2 mt-2">
      <div
          v-for="ref in references"
          :key="ref.dictionaryId"
          class="flex items-center justify-between gap-2 text-sm"
      >
        <span class="text-gray-500 min-w-0 truncate">{{ ref.dictionaryName }}</span>
        <span class="font-mono text-gray-800">{{ ref.pageOrNumber }}</span>
        <button
            class="button-helper text-red-400 hover:text-red-600 shrink-0"
            :title="$t('DictionaryReferencesCard.remove')"
            @click="$emit('removeReference', ref.dictionaryId)"
        >✕</button>
      </div>
    </div>

    <div class="mt-3">
      <button
          class="text-xs text-blue-700 hover:underline flex items-center gap-1"
          @click="addFormOpen = !addFormOpen"
      >
        {{ addFormOpen ? '▾' : '▸' }}
        {{ $t('DictionaryReferencesCard.add') }}
      </button>

      <div v-if="addFormOpen" class="mt-2 flex flex-col gap-2">
        <select
            v-model="selectedDictId"
            class="text-sm border border-gray-300 rounded px-2 py-1"
        >
          <option value="" disabled>{{ $t('DictionaryReferencesCard.selectDict') }}</option>
          <option
              v-for="dict in availableDictionaries"
              :key="dict.id"
              :value="dict.id"
          >
            {{ dict.name }} ({{ dict.year }})
          </option>
        </select>

        <input
            v-model="pageOrNumber"
            type="text"
            class="text-sm border border-gray-300 rounded px-2 py-1"
            :placeholder="$t('DictionaryReferencesCard.pageOrNumber')"
        />

        <button
            class="button-helper self-start"
            :disabled="!selectedDictId || !pageOrNumber.trim()"
            @click="submitReference"
        >
          {{ $t('DictionaryReferencesCard.save') }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { DictionaryReference, DictionaryInfo } from '@/helpers/http/interfaces'

const props = defineProps<{
  references: DictionaryReference[]
  availableDictionaries: DictionaryInfo[]
}>()

const emit = defineEmits<{
  (e: 'addReference', ref: DictionaryReference): void
  (e: 'removeReference', dictionaryId: string): void
}>()

const addFormOpen = ref(false)
const selectedDictId = ref('')
const pageOrNumber = ref('')

const submitReference = () => {
  const dict = props.availableDictionaries.find(d => d.id === selectedDictId.value)
  if (!dict) return
  emit('addReference', {
    dictionaryId: selectedDictId.value,
    dictionaryName: dict.name,
    pageOrNumber: pageOrNumber.value.trim(),
  })
  selectedDictId.value = ''
  pageOrNumber.value = ''
  addFormOpen.value = false
}
</script>