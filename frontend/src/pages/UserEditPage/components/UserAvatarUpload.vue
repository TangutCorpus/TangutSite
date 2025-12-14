<template>
  <div class="standard-flex-container">
    <img :src="avatarPreview" class="profile-picture">
    <input ref="fileInput" accept="image/*" class="hidden" type="file" @change="handleFileUpload">
    <BaseButton @click="triggerFileInput">{{ $t('UserAvatarUpload.changeAvatar') }}</BaseButton>
  </div>
</template>

<script setup>
import {ref, watch} from 'vue'
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'

const props = defineProps({modelValue: String})
const emit = defineEmits(['update:modelValue'])
const fileInput = ref(null)
const avatarPreview = ref(props.modelValue)

const triggerFileInput = () => fileInput.value.click()

const handleFileUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = () => {
      avatarPreview.value = reader.result
      emit('update:modelValue', reader.result)
    }
    reader.readAsDataURL(file)
  }
}

watch(() => props.modelValue, (newValue) => {
  avatarPreview.value = newValue
})
</script>
