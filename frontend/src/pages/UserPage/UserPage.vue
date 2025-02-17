<template>
  <div v-if="hasCorrectId" class="container mx-auto p-6 max-w-4xl">
    <div class="bg-white shadow rounded-lg p-6 flex flex-col items-center relative">
      <BaseButton
        v-if="isOwner"
        class="absolute top-4 right-4"
        @click="editProfile"
      >
        Редактировать
      </BaseButton>

      <UserAvatar :src="user.avatarUrl || DefaultAvatar" />
      <div class="flex items-center gap-2 mt-4">
        <h1 class="text-2xl font-bold">@{{ user.username }}</h1>
        <span class="px-3 py-1 bg-blue-100 text-blue-700 rounded text-sm">{{ user.role }}</span>
      </div>

      <UserContact :displayName="user.displayName" :email="user.email" class="mt-6 w-full" />

      <UserBiography :biography="user.biography" class="mt-6 w-full" />
    </div>
  </div>
  <not-found-page v-else></not-found-page>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getRoleFromToken } from '@/helpers/jwt/jwt'
import api from '@/helpers/http/http'


import UserAvatar from '@/pages/UserPage/components/UserAvatar.vue'
import UserContact from '@/pages/UserPage/components/UserContact.vue'
import UserBiography from '@/pages/UserPage/components/UserBiography.vue'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'
import DefaultAvatar from '@/assets/images/404.svg'
import NotFoundPage from '@/pages/NotFoundPage/NotFoundPage.vue'

const route = useRoute()
const router = useRouter()
const user = ref({
  id: '',
  username: '',
  email: '',
  avatarUrl: '',
  displayName: '',
  biography: '',
  role: ''
})

const currentUserId = localStorage.getItem('userId')
const hasCorrectId = ref(true)
const isOwner = computed(() => user.value.id === currentUserId)

const fetchUser = async () => {
  try {
    const userId = route.params.id
    const response = await api.get(`/users/${userId}`)
    if (response.status !== 200) {
      throw new Error(`Server error: ${response.status} ${response.statusText}`)
    }

    user.value = response.data
    user.value.role = getRoleFromToken(localStorage.getItem('accessToken'))
    if (!user.value.avatarUrl) user.value.avatarUrl = DefaultAvatar

  } catch (error) {
    hasCorrectId.value = false
    console.error('Error fetching user:', error.message)
  }
}

const editProfile = () => {
  if (isOwner.value) {
    router.push(`/user/${user.value.id}/edit`)
  }
}

onMounted(fetchUser)
</script>
