<template>
  <div>
    <h2 class="header-semibold-text">{{ $t('UserContactEdit.contactInfo') }}</h2>
    <label class="block text-gray-700">{{ $t('UserContactEdit.email') }}:</label>
    <input v-model="emailProxy" class="form-input" type="email">

    <label class="block text-gray-700 mt-2">{{ $t('UserContactEdit.realName') }}:</label>
    <input v-model="displayNameProxy" class="form-input" type="text">
  </div>
</template>

<script setup>
import {computed} from 'vue'

const props = defineProps({email: String, displayName: String})
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
