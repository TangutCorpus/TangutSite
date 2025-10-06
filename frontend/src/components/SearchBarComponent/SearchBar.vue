<template>
  <div ref="searchContainer" class="search-container">
    <div class="flex w-1/2 mb-5 mx-auto mt-4">
      <input
          ref="searchInput"
          v-model="userQuery"
          class="search-input"
          :placeholder="$t('SearchBar.placeholder')"
          type="text"
          @focus="showHistory = true"
          @keydown.enter="handleSearch"
      />
      <button
          ref="searchButton"
          class="button-search"
          @click="handleSearch"
      >
        <svg class="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" stroke-linecap="round" stroke-linejoin="round"
                stroke-width="2"/>
        </svg>
      </button>
    </div>

    <div class="flexbox-center gap-4 relative">
      <BaseButtonComponent @click="toggleTangutPopup">
        {{ t('SearchBar.chooseRadicals') }}
      </BaseButtonComponent>
      <h2>{{ $t('SearchBar.searchArea') }}</h2>
      <BaseButtonComponent
          :primary="searchMode === 'text'"
          @click="setSearchMode('text')"
      >
        {{ $t('SearchBar.textMode') }}
      </BaseButtonComponent>
      <BaseButtonComponent
          :primary="searchMode === 'article'"
          @click="setSearchMode('article')"
      >
        {{ $t('SearchBar.dictionaryMode') }}
      </BaseButtonComponent>
      <div v-if="showTangutPopup" class="popup-container">
        <button class="button-popup-close" @click="toggleTangutPopup">X</button>
        <div class="grid grid-cols-8 gap-2 max-h-64 overflow-y-auto">
          <div v-for="(radical, index) in radicals" :key="index" class="radical-item"
               @click="selectRadical(radical)">
            {{ radical }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script lang="ts" setup>
import {computed, ref, watch} from 'vue'
import {useRouter} from 'vue-router'
import BaseButtonComponent from "@/components/BaseButtonComponent/BaseButtonComponent.vue";
import {useI18n} from "vue-i18n";

const props = defineProps<{ query?: string; mode?: string }>()
const emit = defineEmits(['update:query', 'update:mode'])
const searchInput = ref<HTMLInputElement | null>(null)
const searchButton = ref<HTMLButtonElement | null>(null)
const searchContainer = ref<HTMLDivElement | null>(null)
const userQuery = ref(props.query || '')
const searchMode = ref<'text' | 'article'>('text')
const showHistory = ref(false)
const showTangutPopup = ref(false)
const { t } = useI18n()
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
  if (searchMode.value == 'text') {
    showHistory.value = false
    emit('update:query', userQuery.value)
    router.push({path: '/search', query: {query: userQuery.value}})
  }
}
</script>
