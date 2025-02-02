<template>
  <div>
    <h2 class="text-lg font-semibold mb-2">Изображения</h2>

    <BaseButton @click="triggerFileInput" class="mt-2 w-full">Выбрать изображения</BaseButton>
    <input ref="fileInput" type="file" multiple @change="handleFileUpload" accept="image/*" class="hidden">

    <!-- Контейнер с изображениями -->
    <div ref="imageContainer" class="grid grid-cols-3 gap-2 mt-4">
      <div
        v-for="(img, index) in imagesProxy"
        :key="img"
        class="relative cursor-move"
        :data-index="index"
        draggable="true"
        @dragstart="handleDragStart(index)"
        @dragover.prevent
        @drop="handleDrop(index)"
      >
        <img :src="img" class="w-full h-auto rounded shadow">
        <button @click="removeImage(index)" class="absolute top-0 right-0 bg-red-500 text-white p-1 rounded">✖</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits, computed, nextTick } from 'vue';
import BaseButton from '@/components/BaseButton.vue';

const props = defineProps({ images: Array });
const emit = defineEmits(['update:images']);

const imagesProxy = computed({
  get: () => props.images,
  set: (value) => emit('update:images', value)
});

const fileInput = ref(null);
const draggedIndex = ref(null);

const triggerFileInput = () => {
  fileInput.value.click();
};

const handleFileUpload = (event) => {
  const files = Array.from(event.target.files);
  const newImages = files.map(file => URL.createObjectURL(file));
  imagesProxy.value = [...imagesProxy.value, ...newImages];
};

const removeImage = (index) => {
  imagesProxy.value.splice(index, 1);
};

// Начало перетаскивания
const handleDragStart = (index) => {
  draggedIndex.value = index;
};

// Завершение перетаскивания
const handleDrop = async (newIndex) => {
  if (draggedIndex.value === null || draggedIndex.value === newIndex) return;

  const updatedImages = [...imagesProxy.value];
  const movedItem = updatedImages.splice(draggedIndex.value, 1)[0];
  updatedImages.splice(newIndex, 0, movedItem);

  imagesProxy.value = updatedImages;

  // Гарантируем обновление DOM с помощью nextTick
  await nextTick();
  emit('update:images', updatedImages);

  draggedIndex.value = null;
};
</script>
