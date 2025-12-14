<template>
  <div class="narrow-container">
    <SearchBar v-model:mode="searchMode" v-model:query="searchQuery" class="mb-6"/>
    <div class="grid grid-cols-1 gap-6">
      <SearchResultCard v-for="result in results" :key="result.id" :result="result"/>
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref, watch} from 'vue'
import {useRoute} from 'vue-router'
import SearchBar from '@/components/SearchBarComponent/SearchBar.vue'
import SearchResultCard from '@/pages/SearchResultPage/components/SearchResultCard.vue'
import {search} from "@/helpers/http/sessions.js";

const route = useRoute()
const searchQuery: String = ref(route.query.query || '')
const searchMode: "texts" | "dict" = ref(route.query.mode || 'texts')
const results = ref([])

const fetchResults = async () => {
  if (searchQuery.value.includes('=in=') || searchQuery.value.includes('==') || searchQuery.value.includes('!=')) {
    results.value = await search(searchQuery.value, searchMode.value)
  } else if (searchMode.value == 'texts') {
    results.value = await search(searchQuery.value ? `pureText=in=${encodeURIComponent(searchQuery)}` : "", searchMode.value)
  } else if (searchMode.value == 'dict') {
    //TODO: add dict
  } else {
    throw new Error('Invalid query')
  }
}

onMounted(() => {
  fetchResults(searchQuery.value)
})

watch(() => route.query.query, (newQuery) => {
  if (newQuery) {
    searchQuery.value = newQuery
    fetchResults(newQuery)
  }
})
</script>