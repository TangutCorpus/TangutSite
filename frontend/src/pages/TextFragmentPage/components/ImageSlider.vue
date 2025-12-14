<template>
  <div v-if="isSliderOpen" class="fixed inset-0 bg-black bg-opacity-80 flexbox-center"
       tabindex="0" @keydown.esc="$emit('close-slider')" @keydown.left="prevImage" @keydown.right="nextImage">
    <div class="relative dialog-card">
      <button class="absolute top-4 right-4 button-light p-2" @click="$emit('close-slider')">✖</button>
      <button class="absolute top-4 right-16 button-light p-2" @click="openOriginalImage">🔗</button>
      <button class="absolute left-4 center-y button-light p-2"
              @click="prevImage">◀
      </button>
      <img :src="images[currentImage]" class="max-w-full max-h-full object-contain rounded-lg" @dblclick="toggleZoom">
      <button class="absolute right-4 center-y button-light p-2"
              @click="nextImage">▶
      </button>
    </div>
  </div>
</template>

<script setup>
import {onMounted, onUnmounted, ref} from 'vue'

const props = defineProps({images: Array, isSliderOpen: Boolean})
const currentImage = ref(0)
const zoomed = ref(false)
const prevImage = () => currentImage.value = (currentImage.value - 1 + props.images.length) % props.images.length
const nextImage = () => currentImage.value = (currentImage.value + 1) % props.images.length
const openOriginalImage = () => window.open(props.images[currentImage.value], '_blank')
const toggleZoom = () => zoomed.value = !zoomed.value

const handleKeydown = (event) => {
  if (event.key === 'ArrowLeft') prevImage()
  if (event.key === 'ArrowRight') nextImage()
  if (event.key === 'Escape') emit('close-slider')
}

onMounted(() => window.addEventListener('keydown', handleKeydown))
onUnmounted(() => window.removeEventListener('keydown', handleKeydown))
</script>