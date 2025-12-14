<template>
  <div>
    <h2 class="header-semibold-text">{{ $t('UserContact.contactInfo') }}</h2>
    <p class="text-gray-700">{{ $t('UserContact.email') }}: {{ email }}</p>
    <p class="text-gray-700">{{ $t('UserContact.realName') }}: {{ displayName }}</p>
    <p class="text-gray-700">{{ $t('UserContact.role') }}: {{ role }}</p>
  </div>
</template>

<script setup>
const props = defineProps({
  email: String,
  displayName: String,
  role: String,
})
</script>
