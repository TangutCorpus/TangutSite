<template>
  <div>
    <h2 class="text-lg font-semibold mb-2">Контактные данные</h2>
    <label class="block text-gray-600">Email:</label>
    <input v-model="emailProxy" class="w-full p-2 border rounded mt-1" type="email">

    <label class="block text-gray-600 mt-3">Дата регистрации:</label>
    <input v-model="createdAtProxy" class="w-full p-2 border rounded mt-1" disabled type="text">
  </div>
</template>

<script setup>
import { computed, defineEmits, defineProps } from 'vue'

const props = defineProps({ email: String, createdAt: String })
const emit = defineEmits(['update:email', 'update:createdAt'])

const emailProxy = computed({
  get: () => props.email,
  set: (value) => emit('update:email', value)
})

const createdAtProxy = computed({
  get: () => props.createdAt,
  set: (value) => emit('update:createdAt', value)
})
</script>
