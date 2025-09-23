<template>
  <div class="flex space-x-4 mb-4">
    <button
        :class="['button-toggle', isRegisterActive ? 'bg-black text-white' : 'bg-gray-200']"
        @click="$emit('change', { mode: 'register', step: 1 })"
    >
      Регистрация
    </button>
    <button
        :class="['button-toggle', isLoginActive ? 'bg-black text-white' : 'bg-gray-200']"
        @click="$emit('change', { mode: 'login', step: 1 })"
    >
      Вход
    </button>
  </div>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
  mode: {type: String, required: true},
  step: {type: Number, required: true},
})
const emit = defineEmits(['change'])

const isRegisterActive = computed(
    () => props.mode === 'register' && props.step === 1
)
const isLoginActive = computed(() => props.mode === 'login')
</script>
