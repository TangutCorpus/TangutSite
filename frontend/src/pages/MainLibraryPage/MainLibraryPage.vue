<template>
  <div class="narrow-container grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
    <LibraryCard
        v-for="text in texts"
        :key="text.id"
        :maxPerRow="10"
        :text="text"
        @select="goToText"
    />
  </div>
  <h1 v-if="texts.length === 0" class="text-center not-found-text">
    {{ $t('MainLibraryPage.contactAdministrator') }}<br>
    {{ $t('MainLibraryPage.textsNotFound') }}</h1>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {useRouter} from 'vue-router'
import LibraryCard from '@/pages/MainLibraryPage/components/LibraryCard.vue'
import {getAllTexts} from "@/helpers/http/sessions.js";

const router = useRouter()
const texts = ref([])

onMounted(async () => {
  texts.value = await getAllTexts()
})

const goToText = (id) => {
  router.push(`/text/${id}`)
}
</script>

<style>
.not-found-text {
  font-size: 50px;
}
</style>