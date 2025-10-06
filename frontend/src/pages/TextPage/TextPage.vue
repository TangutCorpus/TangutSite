<template>
  <div class="narrow-container grid grid-cols-4 gap-6">
    <div class="col-span-4 text-center mb-4 relative">
      <button class="absolute right-0 top-0 mt-2 mr-4 button-helper" @click="editTextPage">
        {{$t('TextPage.edit')}}
      </button>
    </div>

    <div class="col-span-1 card-container self-start">
      <h1 class="text-center text-bold">{{$t('TextPage.information')}}</h1>
      <div v-for="(fields, section) in text.metadata" :key="section" class="text-gray-700">
        {{ section }}: {{ fields }}
      </div>
    </div>

    <div class="col-span-3">
      <GlossedText :textPages="textPages" />
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import GlossedText from '@/pages/TextPage/components/GlossedText.vue'
import api from '@/helpers/http/http'
import { parseXmlComment } from '@/helpers/xml/xmlParser'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()
const text = ref({ title: '', metadata: '', pageIds: [] })
const textPages = ref([])
const parsedComment = ref({})
const currentTextId = route.params.id

const editTextPage = () => {
  router.push(`/text/${currentTextId}/edit`)
}

onMounted(async () => {
  const { data } = await api.get(`/texts/${currentTextId}`)
  text.value = data
  text.value.metadata = JSON.parse(data.metadata)
  parsedComment.value = parseXmlComment(data.comment)

  for (const pageId of data.pageIds) {
    try {
      const response = await api.get(`/pages/${pageId}`)
      textPages.value.push(response.data)
    } catch (error) {
      console.error(`Error fetching page ${pageId}:`, error)
    }
  }
})
</script>
