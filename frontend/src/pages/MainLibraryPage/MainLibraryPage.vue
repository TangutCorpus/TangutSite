<template>
  <div class="narrow-container grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
    <LibraryCard
      v-for="text in texts"
      :key="text.id"
      :text="text"
      :maxPerRow="10"
      @select="goToText"
    />
  </div>
  <h1 v-if="texts.length === 0" class="text-center not-found-text">Тексты не найдены. <br> Обратитесь к администратору</h1>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import LibraryCard from '@/pages/MainLibraryPage/components/LibraryCard.vue'
import api from '@/helpers/http/http'

const router = useRouter()
const texts = ref([])

const fetchTexts = async () => {
  try {
    const response = await api.get('/texts')
    if (response.status != 200) throw new Error('Ошибка загрузки текстов')
    texts.value = await response.data
  } catch (error) {
    console.error('Ошибка:', error)
  }
}

onMounted(fetchTexts)

const goToText = (id) => {
  router.push(`/text/${id}`)
}
</script>

<style>
.not-found-text {
    font-size: 50px;
}
</style>