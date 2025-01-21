<template>
    <div class="bg-white min-h-screen flex flex-col">
        <header class="flex justify-end p-4">
            <nav>
                <ul class="flex space-x-4">
                    <li><router-link to="/">Главная</router-link></li>
                    <li><router-link to="/library">Библиотека</router-link></li>
                    <li><router-link to="/grammar">Грамматика</router-link></li>
                    <li><router-link to="/about">О нас</router-link></li>
                    <li><router-link to="/auth">Вход / Регистрация</router-link></li>
                </ul>
            </nav>
        </header>

        <div class="flex flex-col items-center justify-center flex-grow">
            <h1 class="text-3xl font-bold mb-2">Tangut</h1>
            <p class="text-gray-600 mb-4">Онлайн-библиотека и словарь тангутского языка</p>
            <SearchBar />
        </div>
    </div>
</template>

<script setup lang="ts">
import SearchBar from '../components/SearchBar.vue';
</script>
