<template>
  <div>
    <h1 class="header-semibold-text text-center">{{ $t('TextFragmentEdit.page')}} #{{ fragment.pageNumber }}</h1>
    <label class="header-semibold-text">{{ $t('TextFragmentEdit.textId') }}</label>
    <input disabled v-model="fragment.textId" class="form-input" type="text">
    <ImageUpload :images="fragment.images" :single="false" class="mb-4" @update:images="updateImages"/>
    <TranslationListEdit v-model:translations="fragment.translationsXML" v-model:original-text="fragment.pureText"
                         class="mb-4"/>
  </div>
</template>

<script setup>
import ImageUpload from '@/pages/UserEditPage/components/ImageUpload.vue';
import TranslationListEdit from '@/pages/TextFragmentEditPage/components/TranslationListEdit.vue';

const props = defineProps({
  fragment: Object,
});
const emit = defineEmits(['update:fragment']);

const updateImages = (newImages) => {
  emit('update:fragment', {...props.fragment, images: newImages});
};

</script>