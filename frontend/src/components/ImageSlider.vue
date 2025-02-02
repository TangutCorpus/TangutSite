<template>
  <div v-if="isSliderOpen" class="fixed inset-0 bg-black bg-opacity-80 flex items-center justify-center" @keydown.esc="$emit('close-slider')" @keydown.left="prevImage" @keydown.right="nextImage" tabindex="0">
    <div class="relative bg-white p-8 rounded-lg shadow-lg max-w-4xl mx-auto">
      <button class="absolute top-4 right-4 bg-white text-black p-2 rounded" @click="$emit('close-slider')">✖</button>
      <button class="absolute top-4 right-16 bg-white text-black p-2 rounded" @click="openOriginalImage">🔗</button>
      <button class="absolute left-4 top-1/2 transform -translate-y-1/2 bg-white text-black p-2 rounded" @click="prevImage">◀</button>
      <img :src="images[currentImage]" class="max-w-full max-h-full object-contain rounded-lg" @dblclick="toggleZoom">
      <button class="absolute right-4 top-1/2 transform -translate-y-1/2 bg-white text-black p-2 rounded" @click="nextImage">▶</button>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, onMounted, onUnmounted } from 'vue';
const props = defineProps({ images: Array, isSliderOpen: Boolean });
const currentImage = ref(0);
const zoomed = ref(false);
const prevImage = () => currentImage.value = (currentImage.value - 1 + props.images.length) % props.images.length;
const nextImage = () => currentImage.value = (currentImage.value + 1) % props.images.length;
const openOriginalImage = () => window.open(props.images[currentImage.value], '_blank');
const toggleZoom = () => zoomed.value = !zoomed.value;

const handleKeydown = (event) => {
  if (event.key === 'ArrowLeft') prevImage();
  if (event.key === 'ArrowRight') nextImage();
  if (event.key === 'Escape') emit('close-slider');
};

onMounted(() => window.addEventListener('keydown', handleKeydown));
onUnmounted(() => window.removeEventListener('keydown', handleKeydown));
</script>