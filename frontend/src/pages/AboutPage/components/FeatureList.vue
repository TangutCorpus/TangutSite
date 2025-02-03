<template>
  <div class="mt-10 grid md:grid-cols-2 gap-8">
    <FeatureItem v-for="(feature, index) in features" :key="index">
      <template #icon>
        <svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path d="M12 4.5v15m7.5-7.5h-15" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" />
        </svg>
      </template>
      <template #title>{{ feature.title }}</template>
      <template #description>{{ feature.description }}</template>
    </FeatureItem>
  </div>
</template>

<script setup>
import FeatureItem from '@/pages/AboutPage/components/FeatureItem.vue'
import { defineProps } from 'vue'

const props = defineProps({ features: Array })
</script>
