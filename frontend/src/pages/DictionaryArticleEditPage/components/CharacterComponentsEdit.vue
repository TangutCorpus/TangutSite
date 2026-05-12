<template>
  <div class="relative">
    <h2 class="section-label mb-4">{{ $t('DictionaryEdit.sections.components') }}</h2>

    <div class="flex flex-col gap-3">
      <div
          v-for="(comp, index) in components"
          :key="index"
          class="flex items-center gap-2 p-3 bg-gray-50 rounded-lg border border-gray-200"
      >
        <input
            v-model="comp.character"
            class="form-input w-16 text-center text-xl"
            placeholder="符"
            @focus="activeInputIndex = index"
        />

        <select v-model="comp.role" class="form-input w-40">
          <option value="semantic">{{ $t('CharacterCard.roles.semantic') }}</option>
          <option value="phonetic">{{ $t('CharacterCard.roles.phonetic') }}</option>
          <option value="chinesePhonetic">{{ $t('CharacterCard.roles.chinesePhonetic') }}</option>
          <option value="other">{{ $t('CharacterCard.roles.other') }}</option>
        </select>

        <button
            type="button"
            class="px-3 py-2 bg-white border rounded hover:bg-gray-100"
            @click="openRadicalPopup(index)"
        >
          {{ $t('SearchBar.chooseRadicals') }}
        </button>

        <button class="text-red-500 p-2" @click="removeComponent(index)">✖</button>
      </div>

      <div v-if="showPopup" class="absolute z-50 mt-2 p-4 bg-white border shadow-xl rounded-lg w-full max-w-md">
        <div class="flex justify-between mb-2 border-b pb-2">
          <span class="font-bold">{{ $t('SearchBar.chooseRadicals') }}</span>
          <button @click="showPopup = false">✖</button>
        </div>
        <div class="grid grid-cols-8 gap-2 max-h-48 overflow-y-auto">
          <div
              v-for="(radical, rIndex) in radicalList"
              :key="rIndex"
              class="p-2 border text-center cursor-pointer hover:bg-blue-100 rounded"
              @click="selectRadical(radical)"
          >
            {{ radical }}
          </div>
        </div>
      </div>

      <button
          class="button-primary self-start mt-2"
          @click="addComponent"
      >
        + {{ $t('DictionaryEdit.addComponent') }}
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import type { CharacterComponent } from '@/helpers/http/interfaces'
import { getRadicals } from "@/helpers/radicals/radicals"

const props = defineProps<{
  components: CharacterComponent[]
}>()

const emit = defineEmits(['update:components'])

const showPopup = ref(false)
const activeInputIndex = ref<number | null>(null)
const radicalList = computed(() => getRadicals())

const openRadicalPopup = (index: number) => {
  activeInputIndex.value = index
  showPopup.value = true
}

const selectRadical = (radical: string) => {
  if (activeInputIndex.value !== null) {
    const newList = [...props.components]
    // Записываем радикал в поле character
    newList[activeInputIndex.value].character = radical
    emit('update:components', newList)
    showPopup.value = false
  }
}

const addComponent = () => {
  const newList = [...props.components, {
    id: crypto.randomUUID(),
    character: '',
    role: 'semantic',
    articleId: ''
  }]
  emit('update:components', newList)
}

const removeComponent = (index: number) => {
  const newList = [...props.components]
  newList.splice(index, 1)
  emit('update:components', newList)
}
</script>