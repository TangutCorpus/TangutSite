<template>
  <form class="space-y-4">
    <input class="w-full p-2 border rounded" placeholder="Name" type="text">
    <input class="w-full p-2 border rounded" placeholder="Email" type="email">
    <textarea class="w-full p-2 border rounded" placeholder="Message"></textarea>
    <BaseButton primary>Send</BaseButton>
  </form>
</template>

<script setup>
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue'
</script>