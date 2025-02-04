<template>
  <div class="container mx-auto p-6">
    <SearchBar v-model:mode="searchMode" v-model:query="searchQuery" class="mb-6" />
    <div class="grid grid-cols-1 gap-6">
      <SearchResultCard v-for="result in results" :key="result.id" :result="result" />
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import SearchBar from '@/components/SearchBarComponent/SearchBar.vue'
import SearchResultCard from '@/pages/SearchResultPage/components/SearchResultCard.vue'
import api from '@/helpers/http/http'

const route = useRoute()
const searchQuery = ref(route.query.query || '')
const searchMode = ref('text')
const results = ref([])
const errorMessage = ref('')

const fetchResults = async () => {
  try {
    const query = (route.query.query ?? []).toString();
    const response = await api.get(`/search?query=pureText=in=${encodeURIComponent(query)}`)

    if (!response.ok) {
      throw new Error(`Server error: ${response.status} ${response.statusText}`)
    }

    const contentType = response.headers.get('content-type')
    if (!contentType || !contentType.includes('application/json')) {
      const text = await response.text() // Get response as text for debugging
      throw new Error(`Unexpected response format: ${text}`)
    }

    results.value = await response.json()
  } catch (error) {
    console.error('Error fetching search results:', error.message)
    errorMessage.value = `Failed to load search results: ${error.message}`
  }
}

onMounted(() => {
  if (searchQuery.value) {
    fetchResults(searchQuery.value)
  }
})

watch(() => route.query.query, (newQuery) => {
  if (newQuery) {
    searchQuery.value = newQuery
    fetchResults(newQuery)
  }
})
</script>