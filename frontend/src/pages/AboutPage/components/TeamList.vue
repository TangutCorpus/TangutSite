<template>
  <div class="grid grid-cols-3 gap-4">
    <TeamMember v-for="member in members" :key="member.name" v-bind="member" />
  </div>
</template>

<script setup>
import TeamMember from '@/pages/AboutPage/components/TeamMemberCard.vue';
import { defineProps } from 'vue';
const props = defineProps({ members: Array });
</script>