<template>
    <div class="not-found-page">
      <img :src="notFoundImage" alt="404 Not Found" class="not-found-image" />
    </div>
  </template>
  
  <script setup lang="ts">
  import notFoundImage from '../assets/404.jpg';
  
  </script>
  
  <style scoped>
  .not-found-page {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh; /* Высота вьюпорта */
      width: 100vw; /* Ширина вьюпорта */
  }
  .not-found-image {
    max-width: 70%;
    max-height: 70%;
    display: block;
  }
  </style>
  