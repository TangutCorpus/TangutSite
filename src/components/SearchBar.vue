<template>
  <div class="relative w-full" ref="searchContainer">
    <div class="flex items-center">
      <input
        type="text"
        placeholder="Введите запрос..."
          v-model="search"
        class="w-1/2 px-4 ml-[24%] py-2 border rounded-l rounded-r focus:outline-none focus:ring-2 focus:ring-blue-500"
        @keydown.enter="handleSearch"
        @focus="showHistory = true"
        ref="searchInput"
      />
      <button
        class="bg-black text-white px-4 py-2.5 rounded-r rounded-l hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500 ml-5"
        ref="searchButton"
        @click="handleSearch"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-5 w-5"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
          />
        </svg>
      </button>
    </div>
      <div
          v-if="showHistory && (searchHistory.length > 0  || filteredSuggestions.length > 0 )"
          class="absolute ml-[24%] top-full mt-2 bg-white rounded-md shadow-md history-container"
          :style="{ width: suggestionWidth }"
      >
           <div
              v-for="(suggestion, index) in filteredSuggestions"
               :key="index"
              @click="selectSuggestion(suggestion)"
               class="w-full px-4 py-3 border-b border-gray-200 last:border-b-0 hover:bg-gray-100 cursor-pointer block text-black"
           >
               {{ suggestion.title }}, {{ suggestion.author }}
           </div>
           <div
                v-for="(history, index) in searchHistory"
                :key="index"
                @click="selectHistory(history)"
                class="w-full px-4 py-3 border-b border-gray-200 last:border-b-0 hover:bg-gray-100 cursor-pointer block text-black"
           >
                {{ history }}
           </div>
      </div>
  </div>
</template>

<script setup lang="ts">
import { ref, defineEmits, onMounted, onBeforeUnmount, onUpdated, computed, defineProps } from 'vue';

const showSuggestions = ref(false);
const emit = defineEmits(['select-suggestion', 'all-results']);
const searchInput = ref<HTMLInputElement | null>(null);
const searchButton = ref<HTMLButtonElement | null>(null);
const suggestionWidth = ref('0px');
const searchContainer = ref<HTMLDivElement | null>(null);
const search = ref('')
const props = defineProps<{
  suggestions: { title: string; author: string, id:number }[]
}>()
const searchHistory = ref<string[]>(JSON.parse(localStorage.getItem('searchHistory') || '[]'))
const showHistory = ref(false)


const selectSuggestion = (suggestion: { title: string; author: string, id:number }) => {
    emit('select-suggestion', suggestion);
};

const selectHistory = (history:string) => {
  search.value = history;
    showHistory.value = false
    handleSearch()
}

const filteredSuggestions = computed(() => {
  if (!search.value) {
    return []
  }
  return props.suggestions.filter(suggestion =>
  suggestion.title.toLowerCase().includes(search.value.toLowerCase()) || suggestion.author.toLowerCase().includes(search.value.toLowerCase())
  )
})
const handleSearch = () => {
    if (search.value && !searchHistory.value.includes(search.value)) {
    if (searchHistory.value.length >= 5) {
        searchHistory.value.pop()
    }
    searchHistory.value.unshift(search.value);
    localStorage.setItem('searchHistory', JSON.stringify(searchHistory.value));
  }
    showHistory.value = false;
    emit('all-results', filteredSuggestions.value)
};
onMounted(() => {
    updateSuggestionWidth()
    document.addEventListener('click', onClickOutside);
});
onUpdated(() => {
    updateSuggestionWidth()
});
const updateSuggestionWidth = () => {
    if (searchInput.value && searchButton.value) {
        const inputLeft = searchInput.value.getBoundingClientRect().left;
        const buttonRight = searchButton.value.getBoundingClientRect().right;
        suggestionWidth.value = `${buttonRight - inputLeft}px`;
    }
}

onBeforeUnmount(() => {
    document.removeEventListener('click', onClickOutside);
});

const onClickOutside = (event: MouseEvent) => {
  if (searchContainer.value && !searchContainer.value.contains(event.target as Node)) {
    showSuggestions.value = false;
    showHistory.value = false
  }
};

</script>

<style>
.history-container {
  z-index: 10;
}
</style>
