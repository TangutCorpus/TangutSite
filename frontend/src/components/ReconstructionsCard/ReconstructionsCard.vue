<template>
  <div class="card-container">
    <h2 class="section-label">{{ $t('ReconstructionsCard.title') }}</h2>

    <table class="w-full text-sm mt-2">
      <tbody>
      <tr
          v-for="rec in visibleReconstructions"
          :key="rec.id"
          :class="{ 'text-gray-400': rec.deprecated }"
          class="border-b border-gray-100 last:border-0"
      >
        <td class="py-1 pr-3 text-gray-500 whitespace-nowrap">
          {{ rec.author }} {{ rec.year }}
        </td>
        <td class="py-1 italic">{{ rec.value }}</td>
        <td v-if="rec.deprecated" class="py-1 text-xs text-gray-400 pl-2">
          {{ $t('ReconstructionsCard.deprecated') }}
        </td>
      </tr>
      </tbody>
    </table>

    <button
        v-if="hasDeprecated"
        class="text-xs text-blue-700 hover:underline mt-2"
        @click="showDeprecated = !showDeprecated"
    >
      {{ showDeprecated
        ? $t('ReconstructionsCard.hideDeprecated')
        : $t('ReconstructionsCard.showDeprecated', { count: deprecatedCount })
      }}
    </button>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import type { Reconstruction } from '@/helpers/http/interfaces'

const props = defineProps<{ reconstructions: Reconstruction[] }>()

const showDeprecated = ref(false)

const hasDeprecated = computed(() => props.reconstructions.some(r => r.deprecated))
const deprecatedCount = computed(() => props.reconstructions.filter(r => r.deprecated).length)

const visibleReconstructions = computed(() =>
    showDeprecated.value
        ? props.reconstructions
        : props.reconstructions.filter(r => !r.deprecated),
)
</script>