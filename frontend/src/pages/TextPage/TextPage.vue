<template>
  <div class="container mx-auto p-6 grid grid-cols-4 gap-6">
    <div class="col-span-4 text-center mb-4 relative">
      <h1 class="text-3xl font-bold inline-block">Текст: {{ text.title }}</h1>
      <button class="absolute right-0 top-0 mt-2 mr-4 bg-blue-500 text-white px-4 py-2 rounded">
        Редактировать
      </button>
    </div>

    <div class="col-span-1 bg-white shadow rounded-lg p-6 self-start">
      <h1 class="text-center"><b>Информация</b></h1>
      <CollapsibleSection v-for="(fields, section) in parsedComment" :key="section" :title="section">
        <p v-for="(value, key) in fields" :key="key" class="text-gray-600">
          {{ key }}: {{ value }}
        </p>
      </CollapsibleSection>
    </div>

    <div class="col-span-3">
      <GlossedText :textPages="textPages" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import CollapsibleSection from '@/pages/TextPage/components/CollapsibleSection.vue';
import GlossedText from '@/pages/TextPage/components/GlossedText.vue';
import api from '@/helpers/http/http';
import { parseXmlComment } from '@/helpers/xml/xmlParser';

const text = ref({ title: '', comment: '' });
const textPages = ref([]);
const parsedComment = ref({});

onMounted(async () => {
  const { data } = await api.get('/texts/1');
  text.value = data;
  parsedComment.value = parseXmlComment(data.comment);

  const pagesResponse = await api.get(`/fragments?textId=${data.id}`);
  textPages.value = pagesResponse.data;
});
</script>
