<template>
  <div v-if="isOwner" class="narrow-container">
    <div class="card-container relative">
      <BaseButton class="absolute top-4 right-4" @click="saveChanges">{{ $t('UserEditPage.save') }}</BaseButton>
      <UserAvatarUpload v-model="user.avatarUrl"/>

      <div class="flexbox-center gap-2 mt-4">
        <h1 class="header-md-text">@{{ user.username }}</h1>
      </div>

      <UserContactEdit v-model:display-name="user.displayName" v-model:email="user.email" class="mt-6 w-full"/>

      <UserBiographyEdit v-model:biography="user.biography" class="mt-6 w-full"/>
    </div>
  </div>
  <not-found-page v-else-if="!isLoading"></not-found-page>
</template>

<script setup lang="ts">
import {computed, onMounted, Ref, ref} from 'vue'
import UserAvatarUpload from '@/pages/UserEditPage/components/UserAvatarUpload.vue'
import UserContactEdit from '@/pages/UserEditPage/components/UserContactEdit.vue'
import UserBiographyEdit from '@/pages/UserEditPage/components/UserBiographyEdit.vue'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'
import DefaultAvatar from '@/assets/images/404.svg'
import {useRoute, useRouter} from 'vue-router'
import NotFoundPage from '@/pages/NotFoundPage/NotFoundPage.vue'
import {User} from "@/helpers/http/interfaces";
import {getUserById, updateUser} from "@/helpers/http/sessions";

const route = useRoute()
const router = useRouter()
const user: Ref<User> = ref(null)
const isLoading = ref(true)

const isOwner = ref(false)

const fetchUser = async () => {
  const userId = route.params.id
  user.value = await getUserById(userId)
  isLoading.value = false
  isOwner.value = userId === user.value.id
  if (!user.value.avatarUrl) {
    user.value.avatarUrl = DefaultAvatar
  }
}

const saveChanges = async () => {
  const userId = user.value.id
  const updatedUser: User = {
    id: userId,
    username: user.value.username,
    email: user.value.email,
    avatarUrl: user.value.avatarUrl,
    displayName: user.value.displayName,
    biography: user.value.biography
  }
  const updated = await updateUser(userId, updatedUser)

  if (updated) {
    await router.push(`/user/${userId}`)
  }
}
onMounted(fetchUser)
</script>