<template>
  <div v-if="user" class="narrow-container">
    <div class="card-container standard-flex-container relative">
      <UserAvatar :src="user.avatarUrl || DefaultAvatar"/>
      <div class="flexbox-center gap-2 mt-4">
        <h1 class="header-md-text">@{{ user.username }}</h1>
        <BaseButton v-if="isOwner" @click="editProfile">
          Редактировать
        </BaseButton>
      </div>

      <UserContact :displayName="user.displayName" :email="user.email" :role="user.roles" class="mt-6 w-full"/>

      <UserBiography :biography="user.biography" class="mt-6 w-full"/>
    </div>
  </div>
  <not-found-page v-else></not-found-page>
</template>

<script setup lang="ts">
import {computed, onMounted, Ref, ref} from 'vue'
import {useRoute, useRouter} from 'vue-router'

import UserAvatar from '@/pages/UserPage/components/UserAvatar.vue'
import UserContact from '@/pages/UserPage/components/UserContact.vue'
import UserBiography from '@/pages/UserPage/components/UserBiography.vue'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'
import DefaultAvatar from '@/assets/images/404.svg'
import NotFoundPage from '@/pages/NotFoundPage/NotFoundPage.vue'
import {User} from "@/helpers/http/interfaces";
import {getUserById} from "@/helpers/http/sessions";

const route = useRoute()
const router = useRouter()
const user: Ref<User | null> = ref(null)

const hasCorrectId = ref(true)
const isOwner = ref(false)

const fetchUser = async () => {
  const userId = route.params.id.toString()
  const fetchedUser = await getUserById(userId)

  if (!fetchedUser) {
    hasCorrectId.value = false
    return
  }
  isOwner.value = fetchedUser.id === userId
  user.value = {
    ...fetchedUser,
    roles: fetchedUser.roles || "EDITOR",
    avatarUrl: fetchedUser.avatarUrl || DefaultAvatar,
  }
}

const editProfile = () => {
  if (isOwner.value) {
    router.push(`/user/${user.value.id}/edit`)
  }
}

onMounted(fetchUser)
</script>
