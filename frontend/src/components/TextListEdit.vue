<template>
  <div>
    <button @click="addCard" class="bg-blue-500 text-white px-4 py-2 rounded mb-4">Добавить перевод</button>

    <div v-for="(card, index) in cards" :key="index" class="bg-gray-100 p-6 rounded-lg mb-6 border-l-4 border-blue-500">
      <textarea v-model="card.original" placeholder="Оригинальный текст" class="w-full p-2 border rounded"></textarea>

      <button @click="addTranslation(index)" class="bg-green-500 text-white px-3 py-1 rounded mt-2">Добавить перевод</button>

      <div v-for="(translation, tIndex) in card.translations" :key="tIndex" class="mt-2 flex items-center">
        <select v-model="translation.lang" class="p-2 border rounded mr-2">
          <option value="ru">Русский</option>
          <option value="en">English</option>
        </select>
        <textarea v-model="translation.text" placeholder="Введите перевод" class="p-2 border rounded w-full" />
        <button @click="removeTranslation(index, tIndex)" class="ml-2 text-red-500">✖</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const cards = ref([]);

const addCard = () => {
  cards.value.push({ original: '', translations: [] });
};

const addTranslation = (index) => {
  cards.value[index].translations.push({ lang: 'ru', text: '' });
};

const removeTranslation = (cardIndex, translationIndex) => {
  cards.value[cardIndex].translations.splice(translationIndex, 1);
};
</script>
