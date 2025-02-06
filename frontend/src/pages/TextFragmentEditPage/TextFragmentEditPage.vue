<template>
  <div v-if="isOwner" class="container mx-auto p-6 max-w-4xl">
    <div class="bg-white shadow rounded-lg p-6 relative">
      <label class="block text-lg font-semibold mb-2">Название фрагмента</label>
      <input v-model="fragment.title" class="w-full p-2 border rounded mb-4" type="text">

      <ImageUpload :images="fragment.images" :single="false" class="mb-6" @update:images="updateImages" />
      <TranslationListEdit v-model:translations="fragment.translations" class="mb-6" />

      <BaseButton class="mt-6 w-full" primary @click="saveFragment">Сохранить</BaseButton>
    </div>
  </div>

  <NotFoundPage v-else-if="!isLoading"></NotFoundPage>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import api from '@/helpers/http/http';
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue';
import ImageUpload from '@/pages/UserEditPage/components/ImageUpload.vue';
import TranslationListEdit from '@/pages/TextFragmentEditPage/components/TranslationListEdit.vue';
import NotFoundPage from '@/pages/NotFoundPage/NotFoundPage.vue';

const route = useRoute();
const router = useRouter();
const fragment = ref({
  title: '',
  images: [],
  translations: []
});

const isLoading = ref(true);
const currentUserId = ref(localStorage.getItem('userId'));
const isOwner = computed(() => !!currentUserId.value);

const fetchFragment = async () => {
  const fragmentId = route.params.id;

  if (!fragmentId) {
    isLoading.value = false;
    return;
  }

  try {
    const response = await api.get(`/fragments/${fragmentId}`);
    if (response.status === 200) {
      fragment.value = response.data;
    } else {
      throw new Error(`Ошибка загрузки: ${response.status}`);
    }
  } catch (error) {
    console.error('Ошибка при загрузке фрагмента:', error.message);
  } finally {
    isLoading.value = false;
  }
};

const saveFragment = async () => {
  try {
    if (!fragment.value.id) {
      const response = await api.post('/fragments', fragment.value);
      if (response.status === 201) {
        await router.push(`/page/${response.data.id}`);
      } else {
        throw new Error(`Ошибка сохранения: ${response.status}`);
      }
    } else {
      const response = await api.put(`/fragments/${fragment.value.id}`, fragment.value);
      if (response.status === 200) {
        await router.push(`/page/${fragment.value.id}`);
      } else {
        throw new Error(`Ошибка обновления: ${response.status}`);
      }
    }
  } catch (error) {
    console.error('Ошибка сохранения:', error.message);
  }
};

const updateImages = (newImages) => {
  fragment.value.images = newImages;
};

onMounted(fetchFragment);
</script>
