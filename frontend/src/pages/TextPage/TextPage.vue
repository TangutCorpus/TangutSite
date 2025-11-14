<template>
  <div v-if="text" class="narrow-container grid grid-cols-6 gap-6">
    <div class="col-span-6 text-center mb-4 relative">
      <div class="flexbox-center gap-2 mt-4">
        <h1 class="header-md-text">{{ text.title }}</h1>
        <BaseButton  @click="editTextPage">
          {{ $t('TextPage.edit') }}
        </BaseButton>
      </div>
    </div>

    <div class="col-span-2 card-container self-start">
      <h1 class="text-center text-bold">{{ $t('TextPage.information') }}</h1>
      <div v-for="(fields, section) in JSON.parse(text.metadata)" :key="section" class="text-gray-700">
        {{ section }}: {{ fields }}
      </div>
    </div>

    <div class="col-span-4">
      <GlossedText :textPages="textPages"/>
    </div>
  </div>
  <div v-else>
    <NotFoundPage />
  </div>
</template>

<script setup lang="ts">
import {onMounted, Ref, ref} from 'vue'
import GlossedText from '@/pages/TextPage/components/GlossedText.vue'
import {parseXmlComment} from '@/helpers/xml/xmlParser'
import {useRoute, useRouter} from 'vue-router'
import {TextPage} from "@/helpers/http/interfaces";
import {getTextById, getTextPageById} from "@/helpers/http/sessions";
import NotFoundPage from "@/pages/NotFoundPage/NotFoundPage.vue";
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'

const router = useRouter()
const route = useRoute()
const text: Ref<Text> = ref(null)
const textPages: Ref<TextPage[]> = ref([])
const parsedComment = ref({})
const currentTextId = route.params.id

const editTextPage = () => {
  router.push(`/text/${currentTextId}/edit`)
}

onMounted(async () => {
  text.value = await getTextById(currentTextId)
  parsedComment.value = parseXmlComment(text.value.comment)

  for (const pageId of text.value.pageIds) {
    const page = await getTextPageById(pageId)
    textPages.value.push(page)
  }
})
</script>
