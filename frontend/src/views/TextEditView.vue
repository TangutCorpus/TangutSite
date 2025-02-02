<template>
  <div class="container mx-auto p-6 max-w-4xl">
    <div class="bg-white shadow rounded-lg p-6 relative">
      <label class="block text-lg font-semibold mb-2">Название текста</label>
      <input v-model="text.title" type="text" class="w-full p-2 border rounded mb-4">
      <TextMetadataEdit v-model:metadata="text.metadata" class="mb-6" />
      <EditGlossText v-model:textBlocks="text.fragments" class="mb-6" />
      <BaseButton @click="addGloss" class="mb-4 w-full">Добавить глоссированный текст</BaseButton>
      <EditTextCommentCard v-model:comments="text.comments" class="mb-6" />
      <BaseButton @click="addComment" class="mb-4 w-full">Добавить комментарий</BaseButton>
      <BaseButton primary @click="saveText" class="mt-6 w-full">Сохранить</BaseButton>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import BaseButton from '@/components/BaseButton.vue';
import TextMetadataEdit from '@/components/TextMetadataEdit.vue';
import EditGlossText from '@/components/EditGlossText.vue';
import EditTextCommentCard from '@/components/EditTextCommentCard.vue';

const text = ref({
  title: '',
  metadata: {},
  fragments: [],
  comments: []
});

const saveText = () => {
  console.log('Сохранение текста', text.value);
};

const addGloss = () => {
  text.value.fragments.push({ title: '', text: '', gloss: '', comment: '' });
};

const addComment = () => {
  text.value.comments.push([''])
}

</script>