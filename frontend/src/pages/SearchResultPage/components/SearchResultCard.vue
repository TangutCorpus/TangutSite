<template>
  <div class="border rounded-lg p-4 shadow-md bg-white">
    <h3 class="text-lg font-bold">{{ result.title }}</h3>
    <p class="text-gray-600">{{ result.pureText }}</p>
    <a :href="`/text/${result.id}`" class="text-blue-500 mt-2 inline-block">Читать далее</a>
  </div>
</template>

<script setup>
defineProps({ result: Object })
</script>
