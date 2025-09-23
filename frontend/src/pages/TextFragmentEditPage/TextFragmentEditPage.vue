<template>
  <div class="narrow-container">
    <div class="card-container">
      <TextFragmentEdit v-model:fragment="fragment" />
      <BaseButton class="mt-6 w-full" primary @click="saveFragment">Сохранить</BaseButton>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import api from '@/helpers/http/http';
import TextFragmentEdit from '@/components/TextFragmentEdit/TextFragmentEdit.vue';
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue';

const route = useRoute();
const router = useRouter()
const fragment = ref({
  id: "",
  textId: "",
  imagesIDs: [],
  pageNumber: 0,
  pureText: "",
  glossedTextXML: "",
  translationsXML: ""
});

onMounted(async () => {
  const fragmentId = route.params.id;
  const response = await api.get(`/pages/${fragmentId}`);

  fragment.value = response.data;

  fragment.value.translationsXML = JSON.parse(response.data.translationsXML)
});

const saveFragment = async () => {
  fragment.value.translationsXML = JSON.stringify(fragment.value.translationsXML)
  await api.put(`/pages/${fragment.value.id}`, fragment.value);
  router.push(`/page/${route.params.id}`)
};
</script>