<template>
  <select class="language-switcher" v-model="currentLocale">
    <option v-for="locale in supportedLocales">
      {{ locale }}
    </option>
  </select>
</template>

<script setup lang="ts">
import {ref, watch} from "vue";
import i18n from "@/helpers/i18n/i18n.js"

const supportedLocales: string[] = Object.keys(i18n.global.messages.value)
const currentLocale = ref(i18n.global.locale.value)
watch(currentLocale, (newLocale: string) => {
  i18n.global.locale.value = newLocale;
});
</script>

<style scoped>
.language-switcher {
  padding: 5px;
  border-radius: 4px;
}
</style>