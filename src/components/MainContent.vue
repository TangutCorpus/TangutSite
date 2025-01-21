<template>
    <div class="bg-white min-h-screen flex flex-col">
        <header class="flex justify-end p-4">
            <nav>
                <ul class="flex space-x-4">
                    <li
                        @click="navigateTo('home')"
                        :class="[
                  'font-bold',
                  'cursor-pointer',
                  { 'font-bold': currentPage === 'home' },
                ]"
                    >
                        Главная
                    </li>
                    <li
                        @click="navigateTo('library')"
                        :class="['cursor-pointer', { 'font-bold': currentPage === 'library' }]"
                    >
                        Библиотека
                    </li>
                    <li
                        @click="navigateTo('grammar')"
                        :class="['cursor-pointer', { 'font-bold': currentPage === 'grammar' }]"
                    >
                        Грамматика
                    </li>
                    <li
                        @click="navigateTo('about')"
                        :class="['cursor-pointer', { 'font-bold': currentPage === 'about' }]"
                    >
                        О нас
                    </li>
                    <li
                        @click="toggleAuthModal"
                        :class="['cursor-pointer', { 'font-bold': currentPage === 'auth' }]"
                    >
                        Вход / Регистрация
                    </li>
                </ul>
            </nav>
        </header>
        <h1 v-if="currentPage !== 'home' || isSecondMode" class="second-title absolute top-4 left-4 text-3xl font-bold">Tangut</h1>
        <div v-if="show404" class="not-found-page-404">
            <img :src="image404" alt="404 Not Found" class="not-found-image-404" />
        </div>
        <main class="flex flex-col items-center flex-grow main-content-main">
            <template v-if="currentPage === 'home' && !selectedSuggestion">
                <div class="w-full home-container flex flex-col mt-[15%] items-center" :class="{'home-container--active': isSecondMode }">
                    <h1 v-if="!isSecondMode" class="first-title font-bold text-6xl mb-2">Tangut</h1>
                    <p v-if="!isSecondMode" class="text-gray-600 mb-4 text-center">
                        Онлайн-библиотека и словарь тангутского языка
                    </p>
                    <div class="w-full flex items-center justify-center mt-2 pb-[30px] search-bar-wrapper">
                        <SearchBar :suggestions="searchSuggestions" @select-suggestion="selectSuggestion" @all-results="handleAllResults" ref="searchBar"/>
                    </div>
                    <div v-if="isSecondMode" class="search-results-container" :style="{ width: containerWidth, top: searchResultsTop }">
                        <ul>
                            <li v-for="result in searchResults.slice(0, 10)" :key="result.id" class="search-result-item" @click="selectSearchResult(result)">
                                <div class="full-mode w-full">
                                    <div class="search-result-title">{{ result.title }}</div>
                                    <div class="search-result-text">
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                                        et dolore magna aliqua.
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
                                        et dolore magna aliqua.
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </template>
            <template v-else-if="currentPage === 'library' && !selectedSuggestion">
                <h1 class="text-3xl font-bold">Библиотека</h1>
            </template>
            <template v-else-if="currentPage === 'grammar' && !selectedSuggestion">
                <h1 class="text-3xl font-bold">Грамматика</h1>
            </template>
            <template v-else-if="currentPage === 'about' && !selectedSuggestion">
                <h1 class="text-3xl font-bold">О нас</h1>
            </template>
            <template v-else-if="currentPage === 'article' && selectedSuggestion === null">
                <h1 class="text-3xl font-bold">Статья</h1>
                <div class="mt-4">
                    <p>Тут могла быть ваша статья</p>
                </div>
            </template>
            <template v-else-if="currentPage === 'auth' && !selectedSuggestion">
                <h1 class="text-3xl font-bold">Вход / Регистрация</h1>
            </template>
            <SuggestionDetails v-else :suggestion="selectedSuggestion" @back="clearSelectedSuggestion" />
        </main>
        <AuthModal v-if="showAuthModal" @close="showAuthModal = false" />
    </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUpdated } from 'vue';
import SearchBar from './SearchBar.vue';
import AuthModal from './AuthModal.vue';
import SuggestionDetails from './SuggestionDetails.vue';
import { defineEmits, defineProps } from 'vue';
import '../assets/main.css'
import image404 from '../assets/404.jpg';

const currentPage = ref<'home' | 'library' | 'grammar' | 'about' | 'auth' | 'article'>('home');
const selectedSuggestion = ref<string | null>(null);
const showAuthModal = ref(false);
const searchBar = ref<any>(null);
const searchResultsTop = ref('0px')
const emit = defineEmits(['back']);
const show404 = ref(false)
const props = defineProps<{
  selectedSuggestion: string | null;
}>();
const searchResults = ref< {title: string; author:string, id:number}[]>([])
const isSecondMode = ref(false);

const searchSuggestions = ref([
    { title: 'Текст "Lorem", Глава: "Ipsum", строка 1', author: 'Автор 1', id: 1 },
    { title: 'Текст "Lorem", Глава: "Ipsum", строка 2', author: 'Автор 2', id: 2 },
    { title: 'Текст "Lorem", Глава: "Dolor", строка 1', author: 'Автор 3', id: 3 },
    { title: 'Текст "Sit", Глава: "Amet", строка 1', author: 'Автор 4', id: 4 },
    { title: 'Текст "Consectetur", Глава: "Adipiscing", строка 1', author: 'Автор 5', id: 5 },
    { title: 'Текст "Lorem", Глава: "Ipsum", строка 1', author: 'Автор 1', id: 6 },
    { title: 'Текст "Lorem", Глава: "Ipsum", строка 2', author: 'Автор 2', id: 7 },
    { title: 'Текст "Lorem", Глава: "Dolor", строка 1', author: 'Автор 3', id: 8 },
    { title: 'Текст "Sit", Глава: "Amet", строка 1', author: 'Автор 4', id: 9 },
    { title: 'Текст "Consectetur", Глава: "Adipiscing", строка 1', author: 'Автор 5', id: 10 },
]);

const suggestionWidth = computed(() => {
    return searchBar.value ? searchBar.value.suggestionWidth : '0px'
})

const navigateTo = (page: 'home' | 'library' | 'grammar' | 'about' | 'auth' | 'article') => {
    currentPage.value = page;
    selectedSuggestion.value = null;
    isSecondMode.value = false;
    searchResults.value = []
    toggleShow404(page !== "home")
};
const handleAllResults = (results: {title: string; author:string, id:number}[]) => {
    searchResults.value = results
    isSecondMode.value = true
    updateContainerWidth()
    updateSearchResultsPosition()
}

const updateContainerWidth = () => {
    if(searchBar.value && searchBar.value.$el) {
        const searchBarRect = searchBar.value.$el.getBoundingClientRect()
        const inputLeft = searchBar.value.$el.querySelector('input')?.getBoundingClientRect().left
        containerWidth.value = `${searchBarRect.right - inputLeft + 30}px`
    }
}
const updateSearchResultsPosition = () => {
    if(searchBar.value && searchBar.value.$el) {
        const searchBarRect = searchBar.value.$el.getBoundingClientRect()
        searchResultsTop.value = `${searchBarRect.bottom}px`
    }
}
onMounted(() => {
    updateSearchResultsPosition()
})

onUpdated(() => {
    updateSearchResultsPosition()
})
const toggleAuthModal = () => {
    showAuthModal.value = !showAuthModal.value;
};
const selectSuggestion = (suggestion: { title: string; author: string, id: number }) => {
    selectedSuggestion.value = suggestion.title;
    currentPage.value = 'article';
};
  const selectSearchResult = (suggestion: { title: string; author: string, id: number }) => {
    selectedSuggestion.value = suggestion.title;
    currentPage.value = 'article';
  };
const clearSelectedSuggestion = () => {
    selectedSuggestion.value = null;
    currentPage.value = 'home';
    isSecondMode.value = false;
    searchResults.value = []
};

const toggleShow404 = (value: boolean) => {
    show404.value = value
}

</script>
<style>
.home-container--active {
    margin-top: 20px;
    align-items: center;
    justify-content: center;
}
.home-container {
    display: flex;
    justify-content: center;
}
.search-results-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: 0;
    left: 50%;
    transform: translateX(-50%);
    margin-top: 30px;
    z-index: 10;
}
.search-result-item {
    background-color: #f0f0f0;
    padding: 10px;
    margin-bottom: 10px;
    border-radius: 5px;
    cursor: pointer;
    overflow: hidden;
}
.full-mode {
    width: 100%;
    display: flex;
    flex-direction: column;
}
.search-result-title {
    font-weight: bold;
    margin-bottom: 5px;
    font-size: 18px;
}
.search-result-text {
    white-space: pre-line;
}
.search-bar-wrapper {
  position: relative;
  transform: translateX(-50%);
  left: 50%;
    z-index: 15;
}
.not-found-page-404 {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100vw;
    position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.not-found-image-404 {
  max-width: 70%;
  max-height: 70%;
  display: block;
}
</style>