<template>
  <div class="container mx-auto p-6 max-w-4xl">
    <div class="bg-white shadow rounded-lg p-6 relative">
      <label class="text-lg font-semibold mb-2">Название текста</label>
      <input required v-model="text.title" class="w-full p-2 border rounded mb-4" type="text">
      <TextMetadataEdit :metadata="text.metadata"></TextMetadataEdit>
      <div v-for="page in pages" :key="page.pageNumber">
        <TextFragmentPreview :fragment="page" class="mt-6" @edit="openFragmentEdit" />
      </div>
      <BaseButton class="mt-6 w-full" @click="openFragmentEdit(null)">Добавить фрагмент</BaseButton>
      <BaseButton class="mt-6 w-full" :disabled="isSaving" primary @click="saveText">{{ saveButtonText }}</BaseButton>
      <TextFragmentEditPopup v-if="isPageEditOpen" :fragment="editingPage" @close="closePageEdit"
                             @saved="saveFragment"
                             @update:fragment="updateEditingPage" />
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import api from '@/helpers/http/http'
import TextFragmentPreview from '@/pages/TextEditPage/components/TextFragmentPreview.vue'
import TextFragmentEditPopup from '@/pages/TextEditPage/components/TextFragmentEditPopup.vue'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'
import { useRoute } from 'vue-router'
import TextMetadataEdit from '@/pages/TextEditPage/components/TextMetadataEdit.vue'

const route = useRoute()
const currentPageTextId = route.params.id || '[[editing]'
const text = ref({ pageIds: [], title: "", metadata: {} })
const pages = ref([])
const isPageEditOpen = ref(false)
const editingPage = ref(null)
const saveButtonText = ref('Сохранить')
const isSaving = ref(false)
let pageNumber = 1

const fetchTextData = async () => {
  console.log(currentPageTextId)
  if (!currentPageTextId.startsWith('[[editing]')) {
    try {
      const response = await api.get(`/texts/${currentPageTextId}`)
      text.value = response.data
      text.value.metadata = JSON.parse(response.data.metadata)
      console.log(text.value )
      for(const pageId of response.data.pageIds){
        let page = (await api.get(`/pages/${pageId}`)).data
        page.translationsXML = JSON.parse(page.translationsXML)
        page['images'] = []
        pages.value.push(page)
        pageNumber++
      }
    } catch (error) {
      console.error("Ошибка загрузки текста", error)
    }
  }
}

onMounted(fetchTextData)

const openFragmentEdit = (page) => {
  editingPage.value = page || { pageId: "", textId: currentPageTextId, pageNumber: pageNumber++, images: [], translationsXML: [], pureText: "" }
  isPageEditOpen.value = true
}

const closePageEdit = () => {
  isPageEditOpen.value = false
}

const updateEditingPage = (updatedFragment) => {
  editingPage.value = updatedFragment
}

const saveFragment = (fragment) => {
  const index = pages.value.findIndex((p) => p.pageNumber === fragment.pageNumber)
  if (index !== -1) {
    pages.value[index] = fragment
  } else {
    pages.value.push(fragment)
  }
  closePageEdit()
}

const saveText = async () => {
  if (isSaving.value) return
  isSaving.value = true

  let textRequest = {
    pageIds: text.value.pageIds,
    title: text.value.title,
    metadata: JSON.stringify(text.value.metadata)
  }

  let textId = ""

  if(!currentPageTextId.startsWith('[[editing]')){
    await api.put(`/texts/${currentPageTextId}`, { ...textRequest, id: currentPageTextId })
    textId = currentPageTextId
  } else {
    const response = await api.post('/texts', textRequest)
    textId = response.data
  }
  const pageIds = []
  for (const page of pages.value) {
    if (!text.value.pageIds.includes(page.id)) {
      const imageIds = await uploadImages(page.images)
      const pageRequest = {
        textId: textId,
        imagesIDs: imageIds,
        pageNumber: page.pageNumber,
        pureText: page.pureText,
        glossedTextXML: "",
        translationsXML: JSON.stringify(page.translationsXML),
      }
      const pageResponse = await api.post('/pages', pageRequest)
      pageIds.push(pageResponse.data)
    }
  }
  textRequest.pageIds.push(...pageIds)
  await api.put(`/texts/${textId}`, textRequest)
  saveButtonText.value = 'Сохранено'

  setTimeout(() => {
    saveButtonText.value = 'Сохранить'
    isSaving.value = false
  }, 1000)

}

const uploadImages = async (images) => {
  const uploadedImageIds = []
  for (const image of images) {
    const file = await fetch(image).then(res => res.blob())
    const formData = new FormData()
    formData.append('file', file)
    try {
      const response = await api.post('/image/upload', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      })
      uploadedImageIds.push(response.data.id)
    } catch (error) {
      console.error("Ошибка загрузки изображения", error)
    }
  }
  return uploadedImageIds
}
</script>
