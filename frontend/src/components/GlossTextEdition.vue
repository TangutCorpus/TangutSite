<template>
  <div>
    <button @click="addTextBlock" class="bg-blue-500 text-white px-4 py-2 rounded mb-4">Добавить текст</button>

    <div v-for="(block, index) in textBlocks" :key="index" class="bg-gray-100 p-6 rounded-lg mb-6 border-l-4 border-blue-500">
      <input v-model="block.title" placeholder="Заголовок" class="w-full p-2 border rounded mb-2" />
      <input v-model="block.link" placeholder="ID фрагмента" @input="updateFragmentLink(index)" class="w-full p-2 border rounded mb-2" />
      <a :href="block.fragmentLink" class="text-blue-500 underline mb-2">Перейти к фрагменту</a>

      <textarea v-model="block.text" placeholder="Введите текст" class="w-full p-2 border rounded"></textarea>

      <button @click="toggleTranslations(index)" class="bg-green-500 text-white px-3 py-1 rounded mt-2">
        {{ block.showTranslations ? 'Скрыть переводы' : 'Показать переводы' }}
      </button>

      <div v-if="block.showTranslations" class="mt-4">
        <p class="bg-yellow-100 p-2 rounded">Перевод 1</p>
        <p class="bg-green-100 p-2 rounded mt-2">Перевод 2</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const textBlocks = ref([]);

const addTextBlock = () => {
  textBlocks.value.push({ title: '', link: '', fragmentLink: '', text: '', showTranslations: false });
};

const toggleTranslations = (index) => {
  textBlocks.value[index].showTranslations = !textBlocks.value[index].showTranslations;
};

const updateFragmentLink = (index) => {
  textBlocks.value[index].fragmentLink = `/fragment/${textBlocks.value[index].link}`;
};
</script>
