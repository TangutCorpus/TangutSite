<template>
  <section :id="id" class="mb-12">
    <h1 class="text-3xl font-bold mb-4">{{ title }}</h1>
    <slot />
  </section>
</template>

<script setup>
import { defineProps } from 'vue'

const props = defineProps({ id: String, title: String })
</script>
