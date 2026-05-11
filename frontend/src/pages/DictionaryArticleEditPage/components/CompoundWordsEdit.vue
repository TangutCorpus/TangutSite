<template>
  <div class="flex flex-col gap-4">
    <h2 class="section-label">{{ $t('DictionaryEdit.sections.compoundWords') }}</h2>

    <div class="flex flex-col gap-3">
      <div
          v-for="(word, index) in compounds"
          :key="index"
          class="p-4 bg-gray-50 rounded-lg border border-gray-200 relative group"
      >
        <button
            class="absolute -right-2 -top-2 bg-white border border-red-200 text-red-500 rounded-full w-6 h-6 flex items-center justify-center shadow-sm hover:bg-red-50 transition-colors"
            @click="removeWord(index)"
        >
          ✕
        </button>

        <div class="grid grid-cols-12 gap-4">
          <div class="col-span-4">
            <label class="text-[10px] uppercase text-gray-400 font-bold ml-1">
              {{ $t('DictionaryEdit.compoundText') }}
            </label>
            <input
                v-model="word.text"
                class="form-input font-medium text-lg"
                type="text"
                placeholder="𗼇𗟲"
            />
          </div>

          <div class="col-span-8">
            <label class="text-[10px] uppercase text-gray-400 font-bold ml-1">
              {{ $t('DictionaryEdit.compoundMeaning') }}
            </label>
            <input
                v-model="word.meaning"
                class="form-input"
                type="text"
                :placeholder="$t('DictionaryEdit.enterMeaning')"
            />
          </div>

          <div class="col-span-6">
            <label class="text-[10px] uppercase text-gray-400 font-bold ml-1">
              {{ $t('DictionaryEdit.linkedArticleId') }}
            </label>
            <input
                v-model="word.articleId"
                class="form-input text-xs font-mono"
                type="text"
                placeholder="UUID / slug"
            />
          </div>

          <div class="col-span-6">
            <label class="text-[10px] uppercase text-gray-400 font-bold ml-1">
              {{ $t('DictionaryEdit.note') }}
            </label>
            <input
                v-model="word.note"
                class="form-input text-xs"
                type="text"
            />
          </div>
        </div>
      </div>

      <button
          class="border-2 border-dashed border-gray-300 rounded-lg p-3 text-gray-500 hover:border-blue-400 hover:text-blue-600 transition-all flex items-center justify-center gap-2"
          @click="addWord"
      >
        <span class="text-xl">+</span>
        {{ $t('DictionaryEdit.addCompoundWord') }}
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { CompoundWord } from '@/helpers/http/interfaces'

const props = defineProps<{
  compounds: CompoundWord[]
}>()

const emit = defineEmits(['update:compounds'])

const addWord = () => {
  const newList = [
    ...props.compounds,
    { text: '', meaning: '', articleId: '', note: '' }
  ]
  emit('update:compounds', newList)
}

const removeWord = (index: number) => {
  const newList = [...props.compounds]
  newList.splice(index, 1)
  emit('update:compounds', newList)
}
</script>