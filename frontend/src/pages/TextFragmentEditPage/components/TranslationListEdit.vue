<template>
  <div>
    <label class="block header-semibold-text">{{ $t('TranslationListEdit.editTranslations') }}</label>

    <div class="rounded-lg mb-6 left-border-card">
      <textarea v-model="localText" class="form-textarea" :placeholder="$t('TranslationListEdit.originalText')"/>
      <button class="button-primary" @click="addTranslation">{{ $t('TranslationListEdit.addTranslation') }}</button>
      <div v-for="(translation, index) in localTranslations" :key="index" class="mt-2 flexbox-center">
        <select v-model="translation.lang" class="p-2 border rounded mr-2" @change="updateTranslations">
          <option value="ru">{{ $t('TranslationListEdit.russian') }}</option>
          <option value="en">{{ $t('TranslationListEdit.english') }}</option>
        </select>
        <textarea v-model="translation.text" class="form-textarea" @input="updateTranslations"
                  :placeholder="$t('TranslationListEdit.enterTranslation')"/>
        <button class="ml-2 text-red-500" @click="removeTranslation(index)">✖</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref, watch} from "vue";
import {buildTranslationsXML, parseTranslationsXML} from "@/helpers/xml/xmlParser";

const props = defineProps({
  pureText: String,
  translations: String,
})
const emit = defineEmits(['update:pureText', 'update:translations']);

const localText = ref(props.pureText || '');
const localTranslations = ref(parseTranslationsXML(props.translations));

watch(localText, (newValue) => {
  emit('update:pureText', newValue);
});

watch(localTranslations, (newValue) => {
  emit('update:translations', buildTranslationsXML(newValue));
}, { deep: true });

const addTranslation = () => {
  localTranslations.value.push({ lang: 'ru', text: '' });
};

const removeTranslation = (index: number) => {
  localTranslations.value.splice(index, 1);
};

const updateTranslations = () => {
  emit('update:translations', buildTranslationsXML(localTranslations.value));
};
</script>