<template>
  <div>
    <FAQItem v-for="faq in faqs" :key="faq.question" v-bind="faq" />
  </div>
</template>

<script setup>
import FAQItem from '@/pages/AboutPage/components/FAQItem.vue';
import { defineProps } from 'vue';
const props = defineProps({ faqs: Array });
</script>