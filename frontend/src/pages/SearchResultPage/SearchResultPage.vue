<template>
  <div class="container mx-auto p-6">
    <SearchBar v-model:mode="searchMode" v-model:query="searchQuery" class="mb-6" @all-results="updateResults" />
    <div class="grid grid-cols-1 gap-6">
      <SearchResultCard v-for="result in results" :key="result.id" :result="result" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import SearchBar from '@/components/SearchBarComponent/SearchBar.vue'
import SearchResultCard from '@/pages/SearchResultPage/components/SearchResultCard.vue'

const searchQuery = ref('')
const searchMode = ref('text')
const results = ref([])
const updateResults = (query) => {
  searchQuery.value = query
  results.value = fetchResults(query)
}
const fetchResults = (query) => {
  return [
    { id: 1, title: 'Результат 1', description: 'Описание результата 1', href: '/text/1' },
    { id: 2, title: 'Результат 2', description: 'Описание результата 2', href: '/text/2' }
  ]
}
</script>