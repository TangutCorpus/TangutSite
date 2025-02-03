<template>
  <div class="container mx-auto p-6 grid grid-cols-4 gap-6">
    <div class="col-span-4 text-center mb-4 relative">
      <h1 class="text-3xl font-bold inline-block">Текст: Lorem Ipsum</h1>
      <button class="absolute right-0 top-0 mt-2 mr-4 bg-blue-500 text-white px-4 py-2 rounded">Редактировать</button>
    </div>

    <div class="col-span-1 bg-white shadow rounded-lg p-6 self-start">
      <CollapsibleSection tag="h1" title="Lorem Ipsum">
        <p class="text-gray-600">Найден: Ipsum Lorem (2025-02-01)</p>
        <p class="text-gray-600">Опубликован: Lorem Publication</p>
        <p class="text-gray-600">Хранится: Lorem Storage (№12345)</p>
        <p class="text-gray-600">Материал: Lorem Material</p>
        <p class="text-gray-600">Аналоги: Ipsum Dolor, Sit Amet</p>
        <p class="text-gray-600">Доп. информация: Дополнительные сведения об артефакте</p>
      </CollapsibleSection>

      <CollapsibleSection tag="h2" title="Дополнительные данные">
        <p class="text-gray-600">Lorem: Ipsum</p>
        <p class="text-gray-600">Lorem: Dolor</p>
        <p class="text-gray-600">Lorem: Sit</p>
      </CollapsibleSection>

      <CollapsibleSection tag="h2" title="Историческая информация">
        <p class="text-gray-600">Дата: 2025-02-01</p>
        <p class="text-gray-600">Описание: Исторический контекст Lorem Ipsum.</p>
      </CollapsibleSection>
    </div>
    <div class="col-span-3">
      <GlossedText :textBlocks="texts" />
      <TextCommentCard :comments="comments" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import CollapsibleSection from '@/pages/TextPage/components/CollapsibleSection.vue'
import GlossedText from '@/pages/TextPage/components/GlossedText.vue'
import TextCommentCard from '@/pages/TextPage/components/TextCommentCard.vue'

const comments = ref([
  'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
  'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
  'Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
])
const texts = ref([
  {
    link: '1',
    title: 'Lorem Ipsum',
    text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
    gloss: 'Лорем ипсум долор сит амет, консектетур адиписцинг элит.',
    comment: 'Oremlay ipsumyay olorday itsay ametyay, onsectetuercay adipiscingyay elityay',
    showTranslations: false
  },
  {
    link: '2',
    title: 'Sed Eiusmod',
    text: 'Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
    gloss: 'Сед до эйусмод темпор инцидидунт ут лаборате эт долоре магна аликуа.',
    comment: 'Edsay itaevay eolay inyay iamday empersay orttitorpay.',
    showTranslations: false
  }
])
</script>
