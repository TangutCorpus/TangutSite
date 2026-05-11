<template>
  <div class="card-container">
    <div class="text-center py-4">
      <div class="text-8xl leading-none mb-3 select-all" :title="unicodeCode">
        {{ character }}
      </div>
      <div class="flex flex-wrap justify-center gap-2">
        <BadgeTag variant="blue">{{ unicodeCode }}</BadgeTag>
        <BadgeTag variant="gray">{{ $t('CharacterCard.key') }}: {{ unicodeKey }}</BadgeTag>
        <BadgeTag variant="gray">{{ strokeCountUnicode }} {{ $t('CharacterCard.strokes') }}</BadgeTag>
      </div>
    </div>

    <div v-if="components.length" class="mt-4">
      <h2 class="section-label">{{ $t('CharacterCard.components') }}</h2>
      <div class="flex flex-wrap justify-center gap-3 mt-2">
        <button
            v-for="comp in components"
            :key="comp.id"
            class="flex flex-col items-center cursor-pointer hover:opacity-80 transition-opacity"
            :title="$t(`CharacterCard.roles.${comp.role}`)"
            @click="$emit('componentClick', comp.articleId)"
        >
          <span
              class="text-3xl p-2 rounded-md border-2"
              :class="roleClass(comp.role)"
          >{{ comp.character }}</span>
          <span class="text-xs mt-1" :class="roleLabelClass(comp.role)">
            {{ $t(`CharacterCard.roles.${comp.role}`) }}
          </span>
        </button>
      </div>
    </div>

    <div v-if="seaOfWritingAnalysis" class="mt-4">
      <button
          class="text-xs text-blue-700 hover:underline w-full text-left"
          @click="seaExpanded = !seaExpanded"
      >
        {{ seaExpanded ? '▾' : '▸' }}
        {{ $t('CharacterCard.seaOfWriting') }}: {{ seaOfWritingAnalysis }}
      </button>
      <div v-if="seaExpanded" class="text-sm text-gray-600 mt-2 leading-relaxed">
        {{ $t('CharacterCard.seaOfWritingHint') }}
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { CharacterComponent, ComponentRole } from '@/helpers/http/interfaces'
import BadgeTag from '@/components/CharacterCard/subcomponents/BadgeTag.vue'

defineProps<{
  character: string
  unicodeCode: string
  unicodeKey: string
  strokeCountUnicode: number
  components: CharacterComponent[]
  seaOfWritingAnalysis: string
}>()

defineEmits<{ (e: 'componentClick', articleId: string): void }>()

const seaExpanded = ref(false)

const ROLE_CLASSES: Record<ComponentRole, string> = {
  semantic: 'border-blue-400 bg-blue-50 text-blue-900',
  phonetic: 'border-teal-400 bg-teal-50 text-teal-900',
  chinesePhonetic: 'border-amber-400 bg-amber-50 text-amber-900',
  other: 'border-gray-300 bg-gray-50 text-gray-700',
}

const ROLE_LABEL_CLASSES: Record<ComponentRole, string> = {
  semantic: 'text-blue-700',
  phonetic: 'text-teal-700',
  chinesePhonetic: 'text-amber-700',
  other: 'text-gray-500',
}

const roleClass = (role: ComponentRole) => ROLE_CLASSES[role] ?? ROLE_CLASSES.other
const roleLabelClass = (role: ComponentRole) => ROLE_LABEL_CLASSES[role] ?? ROLE_LABEL_CLASSES.other
</script>