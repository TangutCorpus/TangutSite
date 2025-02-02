<template>
  <div class="container mx-auto p-6 max-w-4xl">
    <div class="bg-white shadow rounded-lg p-6 flex flex-col items-center">
      <img :src="user.avatarUrl" alt="User Avatar" class="w-32 h-32 rounded-full shadow mb-4">
      <h1 class="text-2xl font-bold">{{ user.displayName }}</h1>
      <p class="text-gray-500">@{{ user.username }}</p>
      <p class="text-gray-700 mt-2">{{ user.biography }}</p>

      <div class="mt-4">
        <span class="px-3 py-1 bg-blue-100 text-blue-700 rounded text-sm">{{ user.role }}</span>
      </div>

      <div class="mt-6 w-full">
        <h2 class="text-lg font-semibold mb-2">Контактные данные</h2>
        <p class="text-gray-600">Email: {{ user.email }}</p>
        <p class="text-gray-600">Дата регистрации: {{ user.createdAt }}</p>
      </div>

      <button class="mt-6 bg-blue-500 text-white px-4 py-2 rounded" @click="editProfile">Редактировать</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import UserAvatar from '@/assets/404.svg'
const user = ref({
  id: '123e4567-e89b-12d3-a456-426614174000',
  username: 'exampleUser',
  email: 'user@example.com',
  avatarUrl: UserAvatar,
  displayName: 'Example User',
  biography: 'Здесь может быть ваша биография...',
  role: 'Editor',
  createdAt: '2025-02-01'
});

const editProfile = () => {
  console.log('Редактирование профиля');
};

onMounted(() => {
});
</script>