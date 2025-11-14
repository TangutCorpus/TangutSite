<template>
  <div class="narrow-container">
    <div class="card-container relative">
      <label class="header-semibold-text">{{ $t('TextEditPage.textName') }}</label>
      <input v-model="text.title" class="form-input" required type="text">
      <TextMetadataEdit v-model:metadata="text.metadata" />
      <div v-for="page in pages" :key="page.pageNumber">
        <TextFragmentPreview :fragment="page" class="mt-6" @edit="openFragmentEdit"/>
      </div>
      <BaseButton class="mt-6 w-full" @click="openFragmentEdit(null)">{{ $t('TextEditPage.addFragment') }}</BaseButton>
      <BaseButton :disabled="isSaving" class="mt-6 w-full" primary @click="saveText">{{ saveButtonText }}</BaseButton>
      <TextFragmentEditPopup v-if="isPageEditOpen" :fragment="editingPage" @close="closePageEdit"
                             @saved="saveFragment"
                             @update:fragment="updateEditingPage"/>
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted, Ref, ref} from 'vue'
import TextFragmentPreview from '@/pages/TextEditPage/components/TextFragmentPreview.vue'
import TextFragmentEditPopup from '@/pages/TextEditPage/components/TextFragmentEditPopup.vue'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'
import {useRoute} from 'vue-router'
import TextMetadataEdit from '@/pages/TextEditPage/components/TextMetadataEdit.vue'
import {useI18n} from "vue-i18n";
import {
  createText,
  createTextPage,
  getTextById,
  getTextPageById,
  updateText,
  uploadImage
} from "@/helpers/http/sessions.js";
import {Text, TextPage, TextPageRequest} from "@/helpers/http/interfaces";

const route = useRoute()
const {t} = useI18n()
const currentId = route.params.id || '[[editing]'
const text: Ref<Text> = ref({
  id: "",
  title: "",
  metadata: "",
  pageIds: [],
})
const pages: Ref<TextPage[]> = ref([])
const isPageEditOpen = ref(false)
const saveButtonText = ref(t('TextEditPage.toSave'))
const isSaving = ref(false)
let pageNumber = 1
const editingPage: Ref<TextPage> = ref(null)

const fetchTextData = async () => {
  if (!currentId.startsWith('[[editing]')) {
    text.value = await getTextById(currentId)
    text.value.metadata = JSON.parse(text.value.metadata)
    for (const pageId of text.value.pageIds) {
      let page = await getTextPageById(pageId)
      page.translationsXML = JSON.parse(page.translationsXML)
      page['images'] = []
      pages.value.push(page)
      pageNumber++
    }
  }
}

onMounted(fetchTextData)

const openFragmentEdit = (page) => {
  editingPage.value = page || {
    id: "[[editing]]",
    textId: text.value.id,
    imagesIDs: [],
    pageNumber: pageNumber,
    pureText: "",
    glossedTextXML: "",
    translationsXML: "",
  }
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
  pageNumber++
}

const saveText = async () => {
  if (isSaving.value) return
  isSaving.value = true

  let textRequest = {
    pageIds: text.value.pageIds,
    title: text.value.title,
    metadata: JSON.stringify(text.value.metadata || {})
  }

  let textId: string

  if (!currentId.startsWith('[[editing]')) {
    await updateText(currentId, {...textRequest, id: currentId})
    textId = currentId
  } else {
    textId = await createText(textRequest)
  }
  const pageIds = []
  for (const page: TextPage of pages.value) {
    if (!text.value.pageIds.includes(page.id)) {
      const imageIds = await uploadImages(page.imagesIDs)
      const pageRequest: TextPageRequest = {
        textId: textId,
        imagesIDs: imageIds,
        pageNumber: page.pageNumber,
        pureText: page.pureText,
        glossedTextXML: "",
        translationsXML: JSON.stringify(page.translationsXML),
      }
      const pageId = await createTextPage(pageRequest)
      pageIds.push(pageId)
    }
  }
  textRequest.pageIds.push(...pageIds)
  await updateText(textId, textRequest)
  saveButtonText.value = t('TextEditPage.saved')

  setTimeout(() => {
    saveButtonText.value = t('TextEditPage.toSave')
    isSaving.value = false
  }, 1000)

}

const uploadImages = async (images) => {
  const uploadedImageIds = []
  for (const image of images) {
    const id = await uploadImage(image)
    uploadedImageIds.push(id)
  }
  return uploadedImageIds
}
</script>
