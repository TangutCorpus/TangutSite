<template>
  <div class="flex items-baseline py-1 border-b border-gray-50 last:border-0">
    <span class="text-xs text-gray-500 w-32 shrink-0">{{ label }}:</span>
    <div class="flex-1 text-sm text-gray-800">
      <slot name="value">{{ value }}</slot>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  label: string
  value?: string
}>();
</script>