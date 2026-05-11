<template>
  <div v-if="sources.length" class="inline-block relative group ml-1">
    <span class="cursor-help text-[10px] bg-gray-200 text-gray-600 px-1 rounded hover:bg-gray-300">
      {{ sources.length }}
    </span>
    <div class="hidden group-hover:block absolute bottom-full mb-1 left-1/2 -translate-x-1/2 z-20 w-48 p-2 bg-white border border-gray-200 rounded shadow-lg text-[10px]">
      <ul class="list-disc pl-3">
        <li v-for="s in sources" :key="s">{{ s }}</li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{ sources: string[] }>();
</script>
