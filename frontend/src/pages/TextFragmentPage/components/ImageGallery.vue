<template>
  <div class="bg-white shadow rounded-lg p-6 relative max-h-[50vh] overflow-hidden flex items-center justify-center">
    <button class="absolute left-2 top-1/2 transform -translate-y-1/2 bg-gray-700 text-white p-2 rounded-full" @click="prevImage">◀</button>
    <img :src="images[currentImage]" class="w-auto max-h-full mx-auto cursor-pointer rounded-lg" @click="$emit('open-slider')">
    <button class="absolute right-2 top-1/2 transform -translate-y-1/2 bg-gray-700 text-white p-2 rounded-full" @click="nextImage">▶</button>
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';
const props = defineProps({ images: Array });
const currentImage = ref(0);
const prevImage = () => currentImage.value = (currentImage.value - 1 + props.images.length) % props.images.length;
const nextImage = () => currentImage.value = (currentImage.value + 1) % props.images.length;
</script>