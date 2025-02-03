<template>
  <table class="w-full border border-gray-300">
    <thead>
    <tr class="bg-gray-100">
      <th v-for="header in headers" :key="header" class="py-2 px-4 border">{{ header }}</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="row in rows" :key="row.id">
      <td v-for="(value, index) in row.values" :key="index" class="py-2 px-4 border">{{ value }}</td>
    </tr>
    </tbody>
  </table>
</template>

<script setup>
import { defineProps } from 'vue'

const props = defineProps({ headers: Array, rows: Array })
</script>