<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4" style="z-index: 50;">
    <div class="bg-white rounded-md shadow-lg relative w-[50vw] h-[55vh] max-h-[90vh] flex">
      <div class="left-side rounded-l w-2/5 p-8 text-white bg-black flex flex-col justify-center">
        <h1 class="text-5xl font-bold mb-5">𗼇𗟲</h1>
        <p class="mb-4">
          Зарегистрируйтесь или войдите, чтобы получить полный доступ к библиотеке, словарю и грамматике.
        </p>
      </div>

      <div class="right-side rounded-md w-3/5 p-8 pr-8 relative overflow-y-auto max-h-[90vh]">
        <button class="absolute top-2 right-2 text-gray-500 hover:text-gray-700" @click="handleClose">
          <svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path d="M6 18L18 6M6 6l12 12" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" />
          </svg>
        </button>

        <h2 class="text-5xl font-bold mb-4">
          {{ mode === 'register' ? (step === 1 ? 'Регистрация' : 'Регистрация') : 'Вход' }}
        </h2>

        <div class="flex space-x-4 mb-4">
          <button
            :class="['px-4 py-2 rounded', mode === 'register' && step === 1 ? 'bg-black text-white' : 'bg-gray-200']"
            @click="mode = 'register'; step = 1">
            Регистрация
          </button>
          <button :class="['px-4 py-2 rounded', mode === 'login' ? 'bg-black text-white' : 'bg-gray-200']"
                  @click="mode = 'login'; step = 1">Вход
          </button>
        </div>

        <form class="space-y-4" @submit.prevent="handleSubmit">
          <div v-if="mode === 'register' && step === 1">
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="email">Email</label>
              <input id="email" v-model="email"
                     class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                     required type="email" />
            </div>
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="nickname">Никнейм</label>
              <input id="nickname" v-model="username"
                     class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                     required type="text" />
            </div>
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="password">Пароль</label>
              <input id="password" v-model="password"
                     class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                     required type="password" />
            </div>
            <button
              class="bg-black text-white px-4 py-2 rounded hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500 w-full"
              type="submit">
              Дальше
            </button>
          </div>

          <div v-if="mode === 'register' && step === 2">
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="name">Ф.И.О.</label>
              <input id="name" v-model="name"
                     class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                     required type="text" />
            </div>
            <div class="mb-6">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="biography">О себе</label>
              <textarea id="biography" v-model="biography"
                        class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline resize-none h-32"
                        required></textarea>
            </div>
            <div class="mb-4 flex items-center">
              <input v-model="agreeTerms" class="mr-2" required type="checkbox" />
              <span class="text-sm">Я согласен с условиями</span>
            </div>
            <button
              class="bg-black text-white px-4 py-2 rounded hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500 w-full"
              type="submit">
              Завершить регистрацию
            </button>
          </div>

          <div v-if="mode === 'login'">
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="email">Email</label>
              <input id="email" v-model="email"
                     class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                     required type="email" />
            </div>
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2" for="password">Пароль</label>
              <input id="password" v-model="password"
                     class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                     required type="password" />
            </div>
            <button
              class="bg-black text-white px-4 py-2 rounded hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500 w-full"
              type="submit">
              Войти
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/helpers/http/http'

const router = useRouter()
const emit = defineEmits(['close'])

const handleClose = () => emit('close')

const mode = ref<'login' | 'register'>('login')
const step = ref(1)

const email = ref('')
const username = ref('')
const password = ref('')
const name = ref('')
const biography = ref('')
const agreeTerms = ref(false)

async function handleSubmit() {
  if (mode.value === 'login') {
    try {
      const response = await api.post('/auth/login',
        JSON.stringify({ email: email.value, password: password.value })
      )

      if (response.status != 200) throw new Error('Ошибка авторизации')

      const data = await response.json()
      localStorage.setItem('accessToken', data.accessToken)

      const userResponse = await api.post('/users/me', {
        headers: { Authorization: `Bearer ${data.accessToken}` }
      })

      const userData = await userResponse.json()
      await router.push(`/user/${userData.id}`)

    } catch (error) {
      console.error(error)
    }
  } else if (mode.value === 'register') {
    if (step.value === 1) {
      step.value = 2
      return
    }

    try {
      const response = await api.post('/auth/signup', JSON.stringify({
          email: email.value,
          username: username.value,
          password: password.value,
          avatarUrl: '',
          displayName: name.value,
          biography: biography.value
        })
      )

      if (response.status != 200) throw new Error('Ошибка регистрации')

      const data = response.data

      localStorage.setItem('accessToken', data.token)
      localStorage.setItem('userId', data.userId)

      handleClose()
      await router.push(`/user/${data.userId}`)
    } catch (error) {
      console.error(error)
    }
  }
}
</script>