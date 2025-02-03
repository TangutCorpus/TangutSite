<template>
  <div class="container mx-auto p-6 max-w-4xl">
    <div class="bg-white shadow rounded-lg p-6 flex flex-col items-center relative">
      <BaseButton class="absolute top-4 right-4" @click="editProfile">Редактировать</BaseButton>
      <UserAvatar :src="user.avatarUrl" />
      <div class="flex items-center gap-2 mt-4">
        <h1 class="text-2xl font-bold">{{ user.displayName }}</h1>
        <span class="px-3 py-1 bg-blue-100 text-blue-700 rounded text-sm">{{ user.role }}</span>
      </div>
      <p class="text-gray-500">@{{ user.username }}</p>

      <UserContact :email="user.email" :created-at="user.createdAt" class="mt-6 w-full" />

      <UserBiography :biography="user.biography" class="mt-6 w-full" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import UserAvatar from '@/pages/UserPage/components/UserAvatar.vue';
import UserContact from '@/pages/UserPage/components/UserContact.vue';
import UserBiography from '@/pages/UserPage/components/UserBiography.vue';
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue';
import DefaultAvatar from '@/assets/images/404.svg';

const user = ref({
  id: '123e4567-e89b-12d3-a456-426614174000',
  username: 'exampleUser',
  email: 'user@example.com',
  avatarUrl: DefaultAvatar,
  displayName: 'Example User',
  biography: 'Здесь может быть ваша биография...',
  role: 'Editor',
  createdAt: '2025-02-01'
});

const editProfile = () => {
  console.log('Редактирование профиля');
};
</script>
