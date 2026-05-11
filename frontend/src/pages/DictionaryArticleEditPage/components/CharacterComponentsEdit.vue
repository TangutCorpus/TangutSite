<template>
  <div>
    <h2 class="section-label mb-4">{{ $t('DictionaryEdit.sections.components') }}</h2>

    <div class="flex flex-col gap-3">
      <div
          v-for="(comp, index) in components"
          :key="index"
          class="flex items-center gap-2 p-3 bg-gray-50 rounded-lg border border-gray-200"
      >
        <input
            v-model="comp.character"
            class="form-input w-16 text-center text-xl"
            placeholder="符"
        />

        <select v-model="comp.role" class="form-input w-40">
          <option value="semantic">{{ $t('CharacterCard.roles.semantic') }}</option>
          <option value="phonetic">{{ $t('CharacterCard.roles.phonetic') }}</option>
          <option value="chinesePhonetic">{{ $t('CharacterCard.roles.chinesePhonetic') }}</option>
          <option value="other">{{ $t('CharacterCard.roles.other') }}</option>
        </select>

        <input
            v-model="comp.articleId"
            class="form-input flex-1"
            :placeholder="$t('DictionaryEdit.linkedArticleId')"
        />

        <button class="text-red-500 p-2" @click="removeComponent(index)">✖</button>
      </div>

      <button
          class="button-primary self-start mt-2"
          @click="addComponent"
      >
        + {{ $t('DictionaryEdit.addComponent') }}
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { CharacterComponent } from '@/helpers/http/interfaces'

const props = defineProps<{
  components: CharacterComponent[]
}>()

const emit = defineEmits(['update:components'])

const addComponent = () => {
  const newList = [...props.components, { id: crypto.randomUUID(), character: '', role: 'semantic', articleId: '' }]
  emit('update:components', newList)
}

const removeComponent = (index: number) => {
  const newList = [...props.components]
  newList.splice(index, 1)
  emit('update:components', newList)
}
</script>