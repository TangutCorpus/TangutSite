<template>
  <div class="narrow-container">
    <div class="card-container">
      <TextFragmentEdit v-model:fragment="fragment"/>
      <BaseButton class="mt-6 w-full" primary @click="saveFragment">{{ $t('TextFragmentEditPage.save') }}</BaseButton>
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted, Ref, ref} from 'vue';
import {useRoute, useRouter} from 'vue-router'
import TextFragmentEdit from '@/components/TextFragmentEdit/TextFragmentEdit.vue';
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue';
import TextPage from "@/pages/TextPage/TextPage.vue";
import {getTextPageById, updateText} from "@/helpers/http/sessions";

const route = useRoute();
const router = useRouter()
const fragment: Ref<TextPage> = ref(null);

onMounted(async () => {
  const fragmentId = route.params.id;
  fragment.value = await getTextPageById(fragmentId);
});

const saveFragment = async () => {
  fragment.value.translationsXML = JSON.stringify(fragment.value.translationsXML);
  await updateText(fragment.value.id, fragment.value)
  await router.push(`/page/${route.params.id}`)
};
</script>