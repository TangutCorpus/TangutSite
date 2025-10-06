<template>
  <div class="modal-backdrop">
    <div class="dialog-card w-full">
      <TextFragmentEdit v-model:fragment="tempFragment"/>
      <BaseButton class="mt-4 w-full" @click="saveTempFragment">{{ $t('TextFragmentEditPopup.save') }}</BaseButton>
      <BaseButton class="mt-4 w-full" @click="$emit('close')">{{ $t('TextFragmentEditPopup.close') }}</BaseButton>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import TextFragmentEdit from '@/components/TextFragmentEdit/TextFragmentEdit.vue';
import BaseButton from '@/components/BaseButtonComponent/BaseButtonComponent.vue';

const props = defineProps({
  fragment: Object,
});
const emit = defineEmits(['close', 'saved']);
const tempFragment = ref({...props.fragment});

const saveTempFragment = () => {
  emit('saved', tempFragment.value);
  emit('close');
};
</script>