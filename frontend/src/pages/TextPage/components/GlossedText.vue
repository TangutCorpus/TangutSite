<template>
  <div>
    <div v-if="textPages.length > 0">
      <div v-for="page in textPages" :key="page.id"
           class="bg-gray-100 p-6 rounded-lg mb-6 border-l-4 border-blue-500">
        <h2 class="text-lg font-bold">
          <a :href="`/page/${page.id}`">Страница #{{ page.pageNumber }}</a>
        </h2>

        <pre class="bg-white p-4 border rounded">{{ page.pureText }}</pre>

        <p v-if="page.glossedText" class="bg-yellow-100 p-2 rounded mt-2">{{ page.glossedText }}</p>
      </div>
    </div>

    <div v-else class="bg-red-100 p-6 rounded-lg border-l-4 border-red-500 text-center">
      <h2 class="text-lg font-bold text-red-600">Ошибка</h2>
      <p class="w-full p-4 border rounded bg-white">Нет связанных страниц с этим текстом...</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({ textPages: Array });
</script>