<template>
  <div class="not-found-page">
    <div class="not-found-text">
      <h1 class="error-code">404</h1>
      <p class="error-message">Страница не найдена</p>
      <router-link to="/" class="home-link">Вернуться на главную</router-link>
    </div>
    <img :src="notFoundImage" alt="404 Not Found" class="not-found-image" />
  </div>
</template>

<script setup lang="ts">
import notFoundImage from '../assets/images/404.svg'
</script>

<style scoped>
.not-found-page {
  position: fixed;
  inset: 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: white;
  padding: 0 5%;
  overflow: hidden;
}

.not-found-text {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
}

.error-code {
  font-size: 6rem;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.error-message {
  font-size: 1.5rem;
  color: #666;
  margin: 10px 0 20px;
}

.home-link {
  display: inline-block;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  font-weight: bold;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.home-link:hover {
  background-color: #0056b3;
}

.not-found-image {
  position: absolute;
  bottom: 0;
  right: 0;
  height: 100vh;
}
</style>
