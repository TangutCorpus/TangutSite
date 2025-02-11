<template>
  <div>
    <h2 class="text-lg font-semibold mb-2">Выберите редактируемое свойство</h2>
    <div class="mb-4">
      <select
        v-model="selectedProperty"
        class="w-full p-2 border rounded mb-4"
        @change="addProperty"
      >
        <option disabled value="">Метаданные текста</option>
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
        v-model="metadataProxy[property]"
        class="w-full p-2 border rounded mb-4"
      ></textarea>
      <button class="text-red-500" @click="removeProperty(property)">Удалить свойство</button>
    </div>
  </div>
</template>

<script setup>
import { computed, defineEmits, defineProps, ref, watch } from 'vue'

const props = defineProps({
  metadata: Object
})

const emit = defineEmits(['update:metadata'])

const availableProperties = {
  'author': 'Автор',
  'date': 'Дата создания',
  'description': 'Описание',
  'material': 'Материал',
  'storage': 'Хранение'
}

const propertiesChosen = ref([])
const selectedProperty = ref('')

const metadataProxy = computed({
  get: () => props.metadata ?? {},
  set: (value) => emit('update:metadata', value)
})

const updatePropertiesChosen = () => {
  propertiesChosen.value = Object.keys(props.metadata || {}).filter(key => key in availableProperties)
}

watch(() => props.metadata, updatePropertiesChosen, { deep: true, immediate: true })

const addProperty = () => {
  if (selectedProperty.value && !propertiesChosen.value.includes(selectedProperty.value)) {
    propertiesChosen.value.push(selectedProperty.value)
    selectedProperty.value = ''
  }
}

const removeProperty = (property) => {
  const index = propertiesChosen.value.indexOf(property)
  if (index > -1) {
    propertiesChosen.value.splice(index, 1)
  }
}
</script>