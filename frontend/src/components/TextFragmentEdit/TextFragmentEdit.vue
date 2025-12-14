<template>
  <div v-if="fragment">
    <h1 class="header-semibold-text text-center">{{ $t('TextFragmentEdit.page') }} #{{ fragment.pageNumber }}</h1>
    <label class="header-semibold-text">{{ $t('TextFragmentEdit.textId') }}</label>
    <input v-model="fragment.textId" class="form-input" disabled type="text">
    <ImageUpload :images="fragment.images" :single="false" class="mb-4" @update:images="updateImages"/>
    <TranslationListEdit
        v-model:pureText="fragment.pureText"
        v-model:translations="fragment.translationsXML"
        class="mb-4"
    />
  </div>
  <div v-else>
    <NotFoundPage/>
  </div>
</template>

<script setup lang="ts">
import ImageUpload from '@/pages/UserEditPage/components/ImageUpload.vue';
import TranslationListEdit from '@/pages/TextFragmentEditPage/components/TranslationListEdit.vue';
import NotFoundPage from "@/pages/NotFoundPage/NotFoundPage.vue";

const props = defineProps({
  fragment: Object,
});

const emit = defineEmits(['update:fragment']);

const updateImages = (newImages) => {
  emit('update:fragment', {...props.fragment, images: newImages});
};

</script>