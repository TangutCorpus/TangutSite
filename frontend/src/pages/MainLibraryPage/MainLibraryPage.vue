<template>
  <div class="narrow-container">
    <div class="flex justify-between items-center mb-8 mt-4">
      <h1 class="header-md-text">{{ $t('HeaderNavComponent.library') }}</h1>
      <button
          class="button-primary flex items-center space-x-2"
          @click="router.push('/text/add')"
      >
        <span class="text-xl">+</span>
        <span>{{ $t('HeaderNavComponent.addText') }}</span>
      </button>
    </div>

    <div v-if="texts.length > 0" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
      <LibraryCard
          v-for="text in texts"
          :key="text.id"
          :maxPerRow="10"
          :text="text"
          @select="goToText"
      />
    </div>

    <h1 v-else class="text-center not-found-text mt-20">
      {{ $t('MainLibraryPage.contactAdministrator') }}<br>
      {{ $t('MainLibraryPage.textsNotFound') }}
    </h1>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import LibraryCard from '@/pages/MainLibraryPage/components/LibraryCard.vue'
import { getAllTexts } from "@/helpers/http/sessions.js"

const router = useRouter()
const texts = ref([])

onMounted(async () => {
  texts.value = await getAllTexts()
})

const goToText = (id) => {
  router.push(`/text/${id}`)
}
</script>

<style scoped>
.not-found-text {
  font-size: 32px;
  color: #9ca3af;
}
</style>