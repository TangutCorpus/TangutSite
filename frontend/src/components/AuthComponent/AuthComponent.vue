<template>
  <div class="modal-backdrop z-50">
    <div class="modal-container">
      <div class="modal-left">
        <h1 class="header-first-level">𗼇𗟲</h1>
        <p class="mb-4"> {{ $t('AuthComponent.authDescription') }}</p>
      </div>

      <div class="modal-right">
        <BaseButtonComponent class="modal-close" @click="handleClose">
          <svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path d="M6 18L18 6M6 6l12 12" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"/>
          </svg>
        </BaseButtonComponent>

        <h1 class="header-first-level">
          {{ mode === 'register' ? $t('AuthComponent.registrationHeader') : $t('AuthComponent.signInHeader') }}
        </h1>

        <ToggleButtons :mode="mode" :step="step" @change="handleToggle"/>

        <form class="space-y-4" @submit.prevent="handleSubmit">
          <div v-if="mode === 'register' && step === 1">
            <div class="form-group">
              <label class="text-label" for="email">{{ $t('AuthComponent.email') }}</label>
              <input id="email" v-model="email" class="form-input" required type="email"/>
            </div>
            <div class="form-group">
              <label class="text-label" for="nickname">{{ $t('AuthComponent.nickname') }}</label>
              <input id="nickname" v-model="username" class="form-input" required type="text"/>
            </div>
            <div class="form-group">
              <label class="text-label" for="password">{{ $t('AuthComponent.password') }}</label>
              <input id="password" v-model="password" class="form-input" required type="password"/>
            </div>
            <button class="button-submit" type="submit">{{ $t('AuthComponent.next') }}</button>
          </div>

          <div v-if="mode === 'register' && step === 2">
            <div class="form-group">
              <label class="text-label" for="name">{{ $t('AuthComponent.realName') }}</label>
              <input id="name" v-model="name" class="form-input" required type="text"/>
            </div>
            <div class="mb-6">
              <label class="text-label" for="biography">{{ $t('AuthComponent.biography') }}</label>
              <textarea id="biography" v-model="biography" class="form-textarea" required/>
            </div>
            <div class="mb-4 flexbox-center">
              <input v-model="agreeTerms" class="mr-2" required type="checkbox"/>
              <span class="text-sm">{{ $t('AuthComponent.agreeWithTerms') }}</span>
            </div>
            <button class="button-submit" type="submit">{{ $t('AuthComponent.completeRegistration') }}</button>
          </div>

          <div v-if="mode === 'login'">
            <div class="form-group">
              <label class="text-label" for="email">{{ $t('AuthComponent.email') }}</label>
              <input id="email" v-model="email" class="form-input" required type="email"/>
            </div>
            <div class="form-group">
              <label class="text-label" for="password">{{ $t('AuthComponent.password') }}</label>
              <input id="password" v-model="password" class="form-input" required type="password"/>
            </div>
            <button class="button-submit" type="submit">{{ $t('AuthComponent.signIn') }}</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router'
import BaseButtonComponent from "@/components/BaseButtonComponent/BaseButtonComponent.vue";
import ToggleButtons from "@/components/ToggleButtons/ToggleButtons.vue";
import {login, signup} from "@/helpers/http/sessions";

const router = useRouter()
const emit = defineEmits(['close'])

const handleClose = () => emit('close')

const mode = ref<'login' | 'register'>('login')
const step = ref(1)

function handleToggle({mode: newMode, step: newStep}) {
  mode.value = newMode
  step.value = newStep
}

const email = ref('')
const username = ref('')
const password = ref('')
const name = ref('')
const biography = ref('')
const agreeTerms = ref(false)

async function handleSubmit() {
  if (mode.value === 'login') {
    const userId = await login({
      email: email.value,
      password: password.value,
    })
    if (userId) {
      await router.push(`/user/${userId}`)
      handleClose()
    }
  } else if (mode.value === 'register') {
    if (step.value === 1) {
      step.value = 2
      return
    }
    const userId = await signup({
      email: email.value,
      username: username.value,
      password: password.value,
      avatarUrl: '',
      displayName: name.value,
      biography: biography.value,
    })
    await router.push(`/user/${userId}`)
    handleClose()
  }
}
</script>