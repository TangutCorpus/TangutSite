<template>
  <div class="container mx-auto p-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
    <LibraryItem v-for="text in texts" :key="text.id" :text="text" :maxPerRow="10" @select="goToText" />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import LibraryItem from '@/components/LibraryItem.vue';

const router = useRouter();

const texts = ref([
  { id: 1, title: 'Lorem Ipsum', chapters: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10'] },
  { id: 2, title: 'Dolor Sit Amet', chapters: ['1', '2', '3'] },
  { id: 3, title: 'Consectetur Adipiscing', chapters: ['1', '2', '3', '4', '5', '6', '7', '8'] },
  { id: 4, title: 'Etiam Euismod', chapters: ['1', '2', '3', '4', '5', '6'] },
  { id: 5, title: 'Vestibulum Ante', chapters: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11'] }
]);

const goToText = (id) => {
  router.push(`/text/${id}`);
};
</script>
