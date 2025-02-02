<template>
  <div>
    <h2 class="text-lg font-semibold mb-2">Контактные данные</h2>
    <label class="block text-gray-600">Email:</label>
    <input v-model="emailProxy" type="email" class="w-full p-2 border rounded mt-1">

    <label class="block text-gray-600 mt-3">Дата регистрации:</label>
    <input v-model="createdAtProxy" type="text" class="w-full p-2 border rounded mt-1" disabled>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, computed } from 'vue';

const props = defineProps({ email: String, createdAt: String });
const emit = defineEmits(['update:email', 'update:createdAt']);

const emailProxy = computed({
  get: () => props.email,
  set: (value) => emit('update:email', value)
});

const createdAtProxy = computed({
  get: () => props.createdAt,
  set: (value) => emit('update:createdAt', value)
});
</script>
