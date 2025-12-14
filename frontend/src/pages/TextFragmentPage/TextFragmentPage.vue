<template>
  <div v-if="page" class="narrow-container grid grid-cols-1 gap-6">
    <div class="col-span-4 text-center mb-4 relative">
      <h1 class="header-md-text inline-block">{{ $t('TextFragmentPage.text') }}: {{ textTitle }}.
        {{ $t('TextFragmentPage.page') }} #{{ page.pageNumber }}</h1>
      <button class="absolute right-0 top-0 mt-2 mr-4 button-helper" @click="editTextFragment">
        {{ $t('TextFragmentPage.edit') }}
      </button>
    </div>

    <div>
      <ImageGallery :images="images" @open-slider="isSliderOpen = true"/>
      <div class="mt-6">
        <TranslationList
            :pureText="page.pureText"
            :translations="page.translationsXML"
        />
      </div>
    </div>

    <ImageSlider v-if="isSliderOpen" :images="images" :isSliderOpen="isSliderOpen"
                 @close-slider="isSliderOpen = false"/>
  </div>
  <div v-else>
    <NotFoundPage/>
  </div>
</template>

<script setup lang="ts">
import {onMounted, Ref, ref} from 'vue';
import {useRoute, useRouter} from 'vue-router'
import ImageGallery from '@/pages/TextFragmentPage/components/ImageGallery.vue';
import ImageSlider from '@/pages/TextFragmentPage/components/ImageSlider.vue';
import TranslationList from '@/pages/TextFragmentPage/components/TranslationList.vue';
import defaultImage from '@/assets/images/404.svg';
import {TextPage} from "@/helpers/http/interfaces";
import {getImage, getTextById, getTextPageById} from "@/helpers/http/sessions";
import NotFoundPage from "@/pages/NotFoundPage/NotFoundPage.vue";

const route = useRoute();
const router = useRouter()
const isSliderOpen = ref(false);
const images = ref([defaultImage]);
const page: Ref<TextPage> = ref(null);
const textTitle = ref('')

onMounted(async () => {
  const {id} = route.params
  page.value = await getTextPageById(id)
  const text = await getTextById(page.value.textId)
  textTitle.value = text.title
  if (page.value.imagesIDs && page.value.imagesIDs.length > 0) {
    const imageRequests = page.value.imagesIDs.map(async (imageId) => {
      try {
        return getImage(imageId)
      } catch {
        return defaultImage;
      }
    });
    images.value = await Promise.all(imageRequests);
  } else {
    images.value = [defaultImage];
  }
});

const editTextFragment = () => {
  router.push(`${route.params.id}/edit`)
}
</script>
