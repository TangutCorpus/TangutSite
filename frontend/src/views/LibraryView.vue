<template>
  <div class="container mx-auto p-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
    <div v-for="text in texts" :key="text.id" class="bg-gray-100 shadow rounded-lg p-6">
      <h2 class="text-xl font-bold text-blue-600 cursor-pointer" @click="goToText(text.id)">
        {{ text.title }}
      </h2>
      <div class="mt-2 flex flex-wrap justify-center gap-2">
        <span v-for="chapter in text.chapters" :key="chapter" class="px-3 py-1 bg-blue-200 text-blue-700 rounded text-sm">
          {{ chapter }}
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const texts = ref([
  { id: 1, title: 'Lorem Ipsum', chapters: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10'] },
  { id: 2, title: 'Dolor Sit Amet', chapters: ['1', '2', '3'] },
  { id: 3, title: 'Consectetur Adipiscing', chapters: ['1', '2', '3', '4', '5', '6', '7', '8'] },
]);

const goToText = (id) => {
  router.push(`/text/${id}`);
};
</script>
