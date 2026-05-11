<template>
  <div class="narrow-container">
    <SearchBar v-model:mode="searchMode" v-model:query="searchQuery" class="mb-6"/>
    <div class="grid grid-cols-1 gap-6">
      <SearchResultCard
          v-for="result in results"
          :key="result.id"
          :result="result"
          :mode="displayedMode"
      /></div>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref, watch} from 'vue'
import {useRoute} from 'vue-router'
import SearchBar from '@/components/SearchBarComponent/SearchBar.vue'
import SearchResultCard from '@/pages/SearchResultPage/components/SearchResultCard.vue'
import {search} from "@/helpers/http/sessions.js";

const route = useRoute()
const searchQuery = ref(route.query.query?.toString() || '')
const searchMode = ref<'texts' | 'dict'>((route.query.mode as 'texts' | 'dict') || 'texts')
const results = ref([])
const displayedMode = ref<'texts' | 'dict'>(searchMode.value)
const fetchResults = async () => {
  const currentQuery = route.query.query?.toString() || ""
  const currentMode = (route.query.mode as 'texts' | 'dict') || 'texts'

  try {
    let data;
    if (currentQuery.includes('=in=') || currentQuery.includes('==') || currentQuery.includes('!=')) {
      data = await search(currentQuery, currentMode)
    } else if (currentMode === 'texts') {
      data = await search(currentQuery ? `pureText=in=${encodeURIComponent(currentQuery)}` : "", currentMode)
    } else if (currentMode === 'dict') {
      data = await search(currentQuery ? `character=in=${encodeURIComponent(currentQuery)}` : "", currentMode)
    }

    results.value = data;
    displayedMode.value = currentMode;

  } catch (e) {
    console.error("Search fetch failed:", e)
    results.value = []
  }
}

onMounted(() => {
  fetchResults()
})

watch(
    () => route.query,
    async (newQuery) => {
      searchQuery.value = newQuery.query?.toString() || '';
      searchMode.value = (newQuery.mode as 'texts' | 'dict') || 'texts';
      await fetchResults();
    },
    { deep: true }
);
</script>