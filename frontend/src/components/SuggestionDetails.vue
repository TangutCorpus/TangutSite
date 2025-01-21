<template>
  <div class="w-1/2 p-6">
    <h1 class="text-7xl font-bold mb-6 text-center">{{suggestion}}</h1>
    <div class="flex">
      <div class="w-1/2 pr-4">
         <div class="relative border border-gray-300">
          <img src="../assets/dada.jpg" alt="Изображение" class="block w-full h-auto" />
          <button class="absolute top-1/2 left-2 -translate-y-1/2 bg-white bg-opacity-50 hover:bg-opacity-75 p-2 rounded-full">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5L8.25 12l7.5-7.5" />
            </svg>
          </button>
             <button class="absolute top-1/2 right-2 -translate-y-1/2 bg-white bg-opacity-50 hover:bg-opacity-75 p-2 rounded-full">
               <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                 <path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" />
               </svg>
             </button>
        </div>
      </div>
      <div class="w-1/2 pl-4 border border-gray-300 rounded-md p-4 mt-2">
        <h2 class="text-xl font-semibold mb-2">Основная информация</h2>
        <div class="space-y-2">
          <div class="flex justify-between">
            <span class="font-medium">Автор находки</span>
            <span>Вася Пупкин</span>
          </div>
          <div class="flex justify-between">
            <span class="font-medium">Время находки</span>
            <span>Вася Пупкин</span>
          </div>
          <div class="flex justify-between">
            <span class="font-medium">Где опубликован</span>
            <span>Вася Пупкин</span>
          </div>
            <div class="flex justify-between">
                <span class="font-medium">Где хранится</span>
                <span>Вася Пупкин</span>
            </div>
          <div class="flex justify-between">
            <span class="font-medium">Номер</span>
            <span>Вася Пупкин</span>
          </div>
          <div class="flex justify-between">
            <span class="font-medium">Тип книги/материала</span>
            <span>Вася Пупкин</span>
          </div>
             <div class="flex justify-between">
                <span class="font-medium">Аналоги</span>
                 <span>Вася Пупкин</span>
          </div>
          <div class="flex justify-between">
            <span class="font-medium">Прочее</span>
            <span>Вася Пупкин</span>
          </div>
        </div>
        <a href="#" class="text-gray-500 underline hover:text-gray-700 block mt-4 text-blue-500 hover:text-blue-700 text-center">Ссылка на страницу</a>
      </div>
    </div>
      <div class="mt-8">
        <h2 class="text-4xl font-semibold mb-5 text-center">Переводы</h2>
        <div class="space-y-4">
          <div v-for="i in 5" :key="i" class="border border-gray-300 p-4 rounded">
            <div class="flex justify-between mb-2">
                <span class="font-medium">Русский</span>
            </div>
              <p>Лорем ипсум долор сит амет</p>
          </div>
        </div>
      </div>
    <button
      class="bg-black hover:bg-gray-800 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline mt-8"
      @click="$emit('back')"
    >
      Назад
    </button>
  </div>
</template>

<script setup lang="ts">
import { defineEmits, defineProps } from 'vue';

const emit = defineEmits(['back']);

const props = defineProps<{
    suggestion: string;
}>();
</script>
