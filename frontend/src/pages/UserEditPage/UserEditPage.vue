<template>
  <div class="container mx-auto p-6 max-w-4xl">
    <div class="bg-white shadow rounded-lg p-6 relative">
      <BaseButton class="absolute top-4 right-4" @click="saveChanges">Сохранить</BaseButton>
      <UserAvatarUpload v-model="user.avatarUrl" />

      <div class="flex items-center gap-2 mt-4">
        <h1 class="text-2xl font-bold">{{ user.displayName }}</h1>
        <span class="px-3 py-1 bg-blue-100 text-blue-700 rounded text-sm">{{ user.role }}</span>
      </div>
      <p class="text-gray-500">@{{ user.username }}</p>

      <UserContactEdit v-model:created-at="user.createdAt" v-model:email="user.email" class="mt-6 w-full" />

      <UserBiographyEdit v-model:biography="user.biography" class="mt-6 w-full" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import UserAvatarUpload from '@/pages/UserEditPage/components/UserAvatarUpload.vue'
import UserContactEdit from '@/pages/UserEditPage/components/UserContactEdit.vue'
import UserBiographyEdit from '@/pages/UserEditPage/components/UserBiographyEdit.vue'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'
import DefaultAvatar from '@/assets/images/404.svg'

const user = ref({
  id: '123e4567-e89b-12d3-a456-426614174000',
  username: 'exampleUser',
  email: 'user@example.com',
  avatarUrl: DefaultAvatar,
  displayName: 'Example User',
  biography: 'Здесь может быть ваша биография...',
  role: 'Editor',
  createdAt: '2025-02-01'
})

const saveChanges = () => {
  console.log('Сохранение изменений', user.value)
}
</script>
