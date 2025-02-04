<template>
  <div ref="searchContainer" class="relative w-full flex flex-col items-center">
    <div class="flex w-1/2 mb-5 mx-auto mt-4">
      <input
        ref="searchInput"
        v-model="userQuery"
        class="flex-1 px-4 py-2 border rounded-l focus:outline-none focus:ring-2 focus:ring-blue-500"
        placeholder="Введите запрос..."
        type="text"
        @focus="showHistory = true"
        @keydown.enter="handleSearch"
      />
      <button
        ref="searchButton"
        class="bg-black text-white px-4 py-2 rounded-r hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500"
        @click="handleSearch"
      >
        <svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" stroke-linecap="round" stroke-linejoin="round"
                stroke-width="2" />
        </svg>
      </button>
    </div>

    <div class="flex items-center gap-4 relative">
      <button
        class="px-4 py-2 border rounded hover:bg-gray-100 focus:outline-none"
        @click="toggleTangutPopup"
      >
        {{ tangutIcon }}
      </button>
      <h2>Область поиска</h2>
      <button
        :class="{ 'bg-blue-500 text-white': searchMode === 'text', 'bg-gray-200': searchMode !== 'text' }"
        class="px-4 py-2 border rounded focus:outline-none"
        @click="setSearchMode('text')"
      >
        Тексты
      </button>
      <button
        :class="{ 'bg-blue-500 text-white': searchMode === 'article', 'bg-gray-200': searchMode !== 'article' }"
        class="px-4 py-2 border rounded focus:outline-none"
        @click="setSearchMode('article')"
      >
        Словарь
      </button>
      <div
        v-if="showTangutPopup"
        class="absolute left-0 top-full mt-1 p-4 bg-white border rounded shadow-lg z-20"
      >
        <button
          class="absolute top-1 right-1 text-sm text-gray-500 hover:text-gray-700 focus:outline-none"
          @click="toggleTangutPopup"
        >
          X
        </button>
        <div class="grid grid-cols-8 gap-2 max-h-64 overflow-y-auto">
          <div
            v-for="(radical, index) in radicals"
            :key="index"
            class="cursor-pointer text-xl hover:bg-gray-200 p-1 text-center"
            @click="selectRadical(radical)"
          >
            {{ radical }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script lang="ts" setup>
import { computed, defineProps, ref, watch } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps<{ query: string; mode: string }>()
const emit = defineEmits(['update:query', 'update:mode'])
const searchInput = ref<HTMLInputElement | null>(null)
const searchButton = ref<HTMLButtonElement | null>(null)
const searchContainer = ref<HTMLDivElement | null>(null)
const userQuery = ref(props.query || '')
const searchMode = ref<'text' | 'article'>('text')
const showHistory = ref(false)
const showTangutPopup = ref(false)
const tangutIcon = 'Выборать компоненты'
const router = useRouter()




const radicals = computed(() => {
  const start = 0x18800
  const end = 0x18AFF
  const arr: string[] = []
  for (let cp = start; cp <= end; cp++) {
    arr.push(String.fromCodePoint(cp))
  }
  return arr
})

const toggleTangutPopup = () => {
  showTangutPopup.value = !showTangutPopup.value
}

const selectRadical = (radical: string) => {
  userQuery.value += radical
}

const setSearchMode = (mode: 'text' | 'article') => {
  searchMode.value = mode
}

watch(() => props.query, (newQuery) => {
  userQuery.value = newQuery || ''
})

const handleSearch = () => {
  if (userQuery.value && searchMode.value == 'text') {
    showHistory.value = false
    emit('update:query', userQuery.value)
    router.push({ path: '/search', query: { query: userQuery.value } })
  }
}
</script>
