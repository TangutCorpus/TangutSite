<template>
    <div class="bg-white min-h-screen flex flex-col items-center">
        <h1 class="text-3xl font-bold text-center p-4">{{ componentName }}</h1>
       <div class="not-found-page">
      <img src="../assets/404.jpg" alt="404 Not Found" class="not-found-image" />
    </div>
    </div>

</template>

<script setup lang="ts">
import {ref} from 'vue';

const componentName = ref('grammar');
</script>
<style>
.not-found-page {
    display: flex;
    justify-content: center;
    align-items: center;
    /*height: 100vh; !* Высота вьюпорта *!*/
    width: 100vw; /* Ширина вьюпорта */
}
.not-found-image {
  max-width: 70%;
  max-height: 70%;
  display: block;
}
</style>


