<template>
  <div v-if="isOwner" class="narrow-container">
    <div class="card-container relative">
      <BaseButton class="absolute top-4 right-4" @click="saveChanges">Сохранить</BaseButton>
      <UserAvatarUpload v-model="user.avatarUrl" />

      <div class="flexbox-center gap-2 mt-4">
        <h1 class="header-md-text">@{{ user.username }}</h1>
      </div>

      <UserContactEdit v-model:display-name="user.displayName" v-model:email="user.email" class="mt-6 w-full" />

      <UserBiographyEdit v-model:biography="user.biography" class="mt-6 w-full" />
    </div>
  </div>
  <not-found-page v-else-if="!isLoading"></not-found-page>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import UserAvatarUpload from '@/pages/UserEditPage/components/UserAvatarUpload.vue'
import UserContactEdit from '@/pages/UserEditPage/components/UserContactEdit.vue'
import UserBiographyEdit from '@/pages/UserEditPage/components/UserBiographyEdit.vue'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'
import api from '@/helpers/http/http'
import DefaultAvatar from '@/assets/images/404.svg'
import { useRoute, useRouter } from 'vue-router'
import NotFoundPage from '@/pages/NotFoundPage/NotFoundPage.vue'

const route = useRoute()
const router = useRouter()
const user = ref({
  id: '',
  username: '',
  email: '',
  avatarUrl: '',
  displayName: '',
  biography: ''
})

const isLoading = ref(true)
const currentUserId = localStorage.getItem('userId')

const isOwner = computed(() => user.value.id === currentUserId)

const fetchUser = async () => {
  try {
    const userId = route.params.id
    const response = await api.get(`/users/${userId}`)
    if (response.status !== 200) {
      throw new Error(`Server error: ${response.status} ${response.statusText}`)
    }

    user.value = response.data
    if (!user.value.avatarUrl) user.value.avatarUrl = DefaultAvatar

  } catch (error) {
    console.error('Error fetching user:', error.message)
  } finally {
    isLoading.value = false
  }
}


const saveChanges = async () => {
  try {
    const userId = user.value.id

    const updatedUser = {
      username: user.value.username,
      email: user.value.email,
      avatarUrl: user.value.avatarUrl,
      displayName: user.value.displayName,
      biography: user.value.biography
    }

    const response = await api.put(`/users/${userId}`, updatedUser)

    console.log(updatedUser)
    if (response.status === 200) {
      await router.push(`/user/${userId}`)
    } else {
      throw new Error(`Ошибка обновления: ${response.status} ${response.statusText}`)
    }
  } catch (error) {
    console.error('Ошибка сохранения изменений:', error.message)
  }
}
onMounted(fetchUser)
</script>