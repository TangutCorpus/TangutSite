<template>
  <div>
    <div v-if="textPages.length > 0">
      <div v-for="page in textPages" :key="page.id"
           class="rounded-lg mb-6 left-border-card">
        <h2 class="text-bold">
          <a :href="`/page/${page.id}`">{{ $t('GlossedText.page') }} #{{ page.pageNumber }}</a>
        </h2>

        <pre class="p-4 bg-white border rounded">{{ page.pureText }}</pre>

        <p v-if="page.glossedText" class="bg-yellow-100 p-2 rounded mt-2">{{ page.glossedText }}</p>
      </div>
    </div>

    <div v-else class="error-card">
      <h2 class="header-error-text">{{ $t('GlossedText.error') }}</h2>
      <p class="text-error">{{ $t('GlossedText.noConnectedPages') }}</p>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({textPages: Array});
</script>