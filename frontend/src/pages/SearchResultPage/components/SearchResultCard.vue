<template>
  <div class="border rounded-lg p-4 shadow-md bg-white">
    <h3 class="text-bold">{{ result.title }}</h3>
    <p class="text-gray-700">{{ result.pureText }}</p>
    <a :href="`/text/${result.id}`" class="text-blue-700 mt-2 inline-block">Читать далее</a>
  </div>
</template>

<script setup>
const props = defineProps({ result: Object })
</script>
