<template>
    <div
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center"
      style="z-index: 50;"
    >
      <div
        class="bg-white rounded-md shadow-lg relative w-[40vw] h-[45vh] flex"
      >
        <div class="left-side rounded-l w-1/2 p-8 text-white bg-black">
          <h1 class="text-5xl font-bold mb-5">Tangut</h1>
          <p class="mb-4">
            Зарегистрируйтесь, чтобы получить полный доступ к библиотеке, словарю
            и грамматике.
          </p>
        </div>
        <div class="right-side rounded-md w-1/2 p-8 pr-8 relative">
          <button
            @click="$emit('close')"
            class="absolute top-2 right-2 text-gray-500 hover:text-gray-700"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </button>
            <h2 class="text-5xl font-bold mb-4" v-if="mode === 'login'">Вход</h2>
              <h2 class="text-5xl font-bold mb-4" v-else-if="mode === 'register'">Регистрация</h2>
          <form>
            <div class="mb-4">
              <label
                for="email"
                class="block text-gray-700 text-sm font-bold mb-2"
                >Email</label
              >
              <input
                type="email"
                id="email"
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            <div class="mb-4">
              <label
                for="password"
                class="block text-gray-700 text-sm font-bold mb-2"
                >Пароль</label
              >
              <input
                type="password"
                id="password"
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
            <div class="mb-4" v-if="mode === 'register'">
                 <label class="flex items-center">
                    <input type="checkbox" class="mr-2">
                    <span class="text-sm">Я согласен с условиями</span>
                    </label>
            </div>
            <div class="flex justify-between">
               <button
                   class="bg-black text-white px-4 py-2 rounded hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500 absolute"
                   v-if="mode === 'login'"
                   style="bottom: 12px; right: 12px;"
               >
                Войти
              </button>
                <button
                  class="bg-black text-white px-4 py-2 rounded hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500 absolute"
                  v-else-if="mode === 'register'"
                  style="bottom: 12px; right: 12px;"
              >
                Зарегистрироваться
              </button>
            </div>
              <div class="mt-4" v-if="mode === 'login'">
               <a href="#" class="text-gray-500 underline hover:text-gray-700">Забыли пароль?</a>
              </div>
            <div class="mt-4">
               <span v-if="mode === 'login'"  class="text-gray-600 text-sm">Нет аккаунта?</span>
              <span v-else class="text-gray-600 text-sm">Уже есть аккаунт?</span>
              <button
                class="text-blue-500 hover:text-blue-700 text-sm ml-1"
                @click.prevent="toggleMode()"
              >
                {{ mode === 'login' ? 'Регистрация' : 'Вход' }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, defineEmits } from 'vue';
  
  const emit = defineEmits(['close']);
    const mode = ref<'login' | 'register'>('login')
    const toggleMode = () => {
        mode.value = mode.value === 'login' ? 'register' : 'login';
    }
  </script>
