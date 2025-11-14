<template>
  <div>
    <h2 class="header-semibold-text">{{ $t('ImageUpload.images') }}</h2>
    <BaseButton class="mt-2 w-full" @click="triggerFileInput">{{ $t('ImageUpload.pickImage') }}</BaseButton>
    <input ref="fileInput" accept="image/*" class="hidden" multiple type="file" @change="handleFileUpload">
    <div ref="imageContainer" class="grid grid-cols-3 gap-2 mt-4">
      <div
          v-for="(img, index) in imagesProxy"
          :key="img"
          :data-index="index"
          class="relative cursor-move"
          draggable="true"
          @dragstart="handleDragStart(index)"
          @drop="handleDrop(index)"
          @dragover.prevent
      >
        <img :src="img" class="w-full h-auto rounded shadow">
        <button class="absolute top-0 right-0 bg-red-500 text-white p-1 rounded" @click="removeImage(index)">✖</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, nextTick, ref} from 'vue'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'

const props = defineProps({images: Array})
const emit = defineEmits(['update:images'])

const imagesProxy = computed({
  get: () => props.images,
  set: (value) => emit('update:images', value)
})

const fileInput = ref(null)
const draggedIndex = ref(null)

const triggerFileInput = () => {
  fileInput.value.click()
}

const handleFileUpload = (event) => {
  const files = Array.from(event.target.files)
  const newImages = files.map(file => URL.createObjectURL(file))
  imagesProxy.value = [...imagesProxy.value, ...newImages]
}

const removeImage = (index) => {
  imagesProxy.value.splice(index, 1)
}

const handleDragStart = (index) => {
  draggedIndex.value = index
}

const handleDrop = async (newIndex) => {
  if (draggedIndex.value === null || draggedIndex.value === newIndex) return

  const updatedImages = [...imagesProxy.value]
  const movedItem = updatedImages.splice(draggedIndex.value, 1)[0]
  updatedImages.splice(newIndex, 0, movedItem)

  imagesProxy.value = updatedImages

  await nextTick()
  emit('update:images', updatedImages)

  draggedIndex.value = null
}
</script>
