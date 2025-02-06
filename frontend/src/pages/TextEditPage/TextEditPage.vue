<template>
  <div v-if="isLoading" class="container mx-auto p-6 max-w-4xl text-center">
    <p>Загрузка...</p>
  </div>

  <div v-else-if="text" class="container mx-auto p-6 max-w-4xl">
    <div class="bg-white shadow rounded-lg p-6 relative">
      <label class="block text-lg font-semibold mb-2">Название текста</label>
      <input v-model="text.title" class="w-full p-2 border rounded mb-4" type="text">

      <TextMetadataEdit v-model:metadata="text.metadata" class="mb-6" />
      <TextCommentCardEdit v-model:comments="text.comments" class="mb-6" />

      <div v-for="fragment in text.fragments" :key="fragment.id" class="mb-4">
        <TextFragmentPreview :fragment="fragment" @edit="openFragmentEdit(fragment)" />
      </div>

      <BaseButton class="mb-4 w-full" @click="openFragmentEdit(null)">Добавить фрагмент</BaseButton>
      <BaseButton class="mt-6 w-full" primary @click="saveText">Сохранить</BaseButton>
    </div>
  </div>

  <NotFoundPage v-else></NotFoundPage>

  <TextFragmentEditPopup v-if="isFragmentEditOpen" :fragment="editingFragment" @close="closeFragmentEdit" @save="saveFragment" />
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import api from '@/helpers/http/http';
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue';
import TextMetadataEdit from '@/pages/TextEditPage/components/TextMetadataEdit.vue';
import TextCommentCardEdit from '@/pages/TextEditPage/components/TextCommentCardEdit.vue';
import TextFragmentPreview from '@/pages/TextEditPage/components/TextFragmentPreview.vue';
import TextFragmentEditPopup from '@/pages/TextEditPage/components/TextFragmentEditPopup.vue';
import NotFoundPage from '@/pages/NotFoundPage/NotFoundPage.vue';

const route = useRoute();
const router = useRouter();
const text = ref(null);
const isLoading = ref(true);
const isFragmentEditOpen = ref(false);
const editingFragment = ref(null);

const fetchText = async () => {
  try {
    const response = await api.get(`/texts/${route.params.id}`);
    text.value = response.data;
  } catch (error) {
    console.error('Ошибка при загрузке текста:', error);
  } finally {
    isLoading.value = false;
  }
};

const saveText = async () => {
  try {
    const response = await api.put(`/texts/${route.params.id}`, text.value);
    if (response.status === 200) {
      await router.push(`/text/${route.params.id}`);
    }
  } catch (error) {
    console.error('Ошибка сохранения:', error);
  }
};

const openFragmentEdit = (fragment) => {
  editingFragment.value = fragment;
  isFragmentEditOpen.value = true;
};

const closeFragmentEdit = () => {
  isFragmentEditOpen.value = false;
  editingFragment.value = null;
};

const saveFragment = (fragmentId) => {
  fetchText();
  closeFragmentEdit();
};

onMounted(fetchText);
</script>
