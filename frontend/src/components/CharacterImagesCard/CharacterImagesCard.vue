<template>
  <div class="card-container">
    <h2 class="section-label">{{ $t('CharacterImagesCard.title') }}</h2>

    <div v-for="group in imageGroups" :key="group.textId" class="mt-4 first:mt-2">
      <div class="flex items-center justify-between mb-2">
        <span class="text-xs font-medium text-gray-600">{{ group.textTitle }}</span>
      </div>

      <div class="flex flex-wrap gap-2 items-end">
        <div
            v-for="(img, idx) in getVisibleImages(group)"
            :key="idx"
            class="w-12 h-12 bg-gray-50 border border-gray-200 rounded flex items-center justify-center overflow-hidden p-1"
        >
          <img :src="img.url" :alt="group.textTitle" class="max-w-full max-h-full object-contain" />
        </div>

        <button
            v-if="group.images.length > 2"
            @click="toggleGroup(group.textId)"
            class="text-[10px] text-blue-600 hover:underline mb-1"
        >
          {{ isExpanded(group.textId) ? $t('common.hide') : `+ еще ${group.images.length - 2}` }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { ImageGroup } from '@/helpers/http/interfaces'

const props = defineProps<{ imageGroups: ImageGroup[] }>()

const expandedGroups = ref<string[]>([])

const toggleGroup = (id: string) => {
  const index = expandedGroups.value.indexOf(id)
  if (index === -1) expandedGroups.value.push(id)
  else expandedGroups.value.splice(index, 1)
}

const isExpanded = (id: string) => expandedGroups.value.includes(id)

const getVisibleImages = (group: ImageGroup) => {
  return isExpanded(group.textId) ? group.images : group.images.slice(0, 2)
}
</script>