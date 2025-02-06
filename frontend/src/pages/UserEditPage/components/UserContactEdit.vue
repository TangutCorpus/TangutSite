<template>
  <div>
    <h2 class="text-lg font-semibold mb-2">Контактные данные</h2>
    <label class="block text-gray-600">Email:</label>
    <input v-model="emailProxy" class="w-full p-2 border rounded mt-1" type="email">

    <label class="block text-gray-600 mt-3">Ф.И.О.:</label>
    <input v-model="displayNameProxy" class="w-full p-2 border rounded mt-1" type="text">
  </div>
</template>

<script setup>
import { computed, defineEmits, defineProps } from 'vue'

const props = defineProps({ email: String, displayName: String })
const emit = defineEmits(['update:email', 'update:displayName'])

const emailProxy = computed({
  get: () => props.email,
  set: (value) => emit('update:email', value)
})

const displayNameProxy = computed({
  get: () => props.displayName,
  set: (value) => emit('update:displayName', value)
})
</script>
