<template>
  <span
      class="px-2 py-0.5 rounded text-xs font-medium border"
      :class="variants[variant]"
  >
    <slot />
  </span>
</template>

<script setup lang="ts">
const props = defineProps<{
  variant: 'blue' | 'gray' | 'amber' | 'teal'
}>();

const variants = {
  blue: 'bg-blue-50 border-blue-200 text-blue-700',
  gray: 'bg-gray-100 border-gray-200 text-gray-600',
  amber: 'bg-amber-50 border-amber-200 text-amber-900',
  teal: 'bg-teal-50 border-teal-200 text-teal-900',
};
</script>