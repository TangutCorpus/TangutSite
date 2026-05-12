<template>
  <div class="narrow-container py-12 space-y-16 animate-fade-in">

    <header class="relative text-center py-8">
      <div class="absolute inset-0 flex items-center justify-center -z-10 opacity-[0.03] select-none">
        <span class="text-[20rem] font-serif">𗼇</span>
      </div>
      <h1 class="text-4xl md:text-5xl font-light tracking-tight text-gray-900 mb-4">
        {{ $t('About.title') }}
      </h1>
      <p class="text-lg text-blue-600 font-medium uppercase tracking-[0.2em]">
        {{ $t('About.subtitle') }}
      </p>
      <div class="mt-6 w-12 h-px bg-gray-300 mx-auto"></div>
    </header>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-12 items-start">

      <section class="space-y-6">
        <h2 class="text-2xl font-semibold text-gray-800 flex items-center">
          <span class="w-2 h-2 bg-blue-500 rounded-full mr-3"></span>
          {{ $t('About.historyTitle') }}
        </h2>
        <div class="prose prose-blue text-gray-600 leading-relaxed">
          <p>{{ $t('About.historyP1') }}</p>
          <p>{{ $t('About.historyP2') }}</p>
        </div>
      </section>

      <aside class="bg-gray-50 rounded-2xl p-8 border border-gray-100 shadow-inner relative overflow-hidden">
        <h3 class="text-sm font-bold text-gray-400 uppercase tracking-widest mb-6">
          {{ $t('About.featuresTitle') }}
        </h3>
        <ul class="space-y-4">
          <li v-for="(item, i) in $tm('About.features')" :key="i" class="flex items-start">
            <span class="text-blue-500 mr-3 font-mono text-sm">0{{ i + 1 }}</span>
            <span class="text-gray-700 font-medium">{{ item }}</span>
          </li>
        </ul>
        <span class="absolute -bottom-10 -right-10 text-9xl text-gray-200/50 transform -rotate-12 select-none">𘟙</span>
      </aside>
    </div>

    <section class="bg-slate-900 rounded-3xl p-10 md:p-16 text-white relative overflow-hidden shadow-2xl">
      <div class="relative z-10 max-w-2xl">
        <h2 class="text-3xl font-light mb-6">{{ $t('About.missionTitle') }}</h2>
        <p class="text-slate-300 text-lg leading-relaxed mb-8">
          {{ $t('About.missionText') }}
        </p>
        <div class="grid grid-cols-3 gap-4 text-sm font-mono text-blue-400">
          <div v-for="stat in stats" :key="stat.label">
            <span class="block text-white text-2xl font-sans font-bold">{{ stat.value }}</span>
            {{ $t(`About.stats.${stat.label}`) }}
          </div>
        </div>
      </div>
      <div class="absolute top-0 right-0 w-96 h-96 bg-blue-500/10 rounded-full blur-3xl -mr-20 -mt-20"></div>
    </section>

  </div>
</template>

<script setup lang="ts">
import { useI18n } from 'vue-i18n';
const { t, tm } = useI18n();

const stats = [
  { value: '~10', label: 'characters' },
  { value: '2026', label: 'year' },
  { value: '~10', label: 'texts' }
];
</script>

<style scoped>
.font-serif {
  font-family: "Noto Serif Tangut", "Tangut", serif;
}

.animate-fade-in {
  animation: fadeIn 0.8s ease-out forwards;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(15px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>