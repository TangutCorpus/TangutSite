<template>
  <div>
    <h2 class="header-semibold-text">{{ $t('TextMetadataEdit.choosePropertyToDelete') }}</h2>
    <div class="mb-4">
      <select
          v-model="selectedProperty"
          class="w-full p-2 border rounded mb-4"
          @change="addProperty"
      >
        <option disabled value="">{{ $t('TextMetadataEdit.textMetadata') }}</option>
        <option
            v-for="(value, key) in availableProperties"
            :key="key"
            :disabled="propertiesChosen.includes(key)"
            :value="key"
        >
          {{ value }}
        </option>
      </select>
    </div>

    <div v-for="(property, index) in propertiesChosen" :key="index" class="mb-4">
      <label class="block mb-2">{{ availableProperties[property] }}</label>
      <textarea
          :value="metadata[property]"
          @input="updateProperty(property, $event.target.value)"
          class="form-textarea"
      />
      <button class="text-red-500" @click="removeProperty(property)">{{
          $t('TextMetadataEdit.deleteProperty')
        }}
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import {computed, ref, watch} from 'vue'
import {useI18n} from "vue-i18n";

const {t} = useI18n()

const props = defineProps({
  metadata: {
    type: Object,
    default: () => ({}),
  },
})

const emit = defineEmits(['update:metadata'])

const availableProperties = computed(() => ({
  'author': t('TextMetadataEdit.author'),
  'date': t('TextMetadataEdit.date'),
  'description': t('TextMetadataEdit.description'),
  'material': t('TextMetadataEdit.material'),
  'storage': t('TextMetadataEdit.storage')
}))

const propertiesChosen = ref([])
const selectedProperty = ref('')

const updateProperty = (property: string, value: string) => {
  const updatedMetadata = {
    ...props.metadata,
    [property]: value
  }
  emit('update:metadata', updatedMetadata)
}

const updatePropertiesChosen = () => {
  propertiesChosen.value = Object.keys(props.metadata || {}).filter(key => key in availableProperties.value)
}

watch(() => props.metadata, updatePropertiesChosen, {deep: true, immediate: true})

const addProperty = () => {
  if (selectedProperty.value && !propertiesChosen.value.includes(selectedProperty.value)) {
    const updatedMetadata = {
      ...props.metadata,
      [selectedProperty.value]: ''
    }
    emit('update:metadata', updatedMetadata)
    selectedProperty.value = ''
  }
}

const removeProperty = (property: string) => {
  const index = propertiesChosen.value.indexOf(property)
  if (index > -1) {
    propertiesChosen.value.splice(index, 1)
  }

  const {[property]: removed, ...updatedMetadata} = props.metadata
  emit('update:metadata', updatedMetadata)
}
</script>