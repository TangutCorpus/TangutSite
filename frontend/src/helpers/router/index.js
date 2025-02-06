import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/pages/HomePage/HomePage.vue'
import NotFoundPage from '@/pages/NotFoundPage/NotFoundPage.vue'
import AboutPage from '@/pages/AboutPage/AboutPage.vue'
import TextPage from '@/pages/TextPage/TextPage.vue'
import UserPage from '@/pages/UserPage/UserPage.vue'
import LibraryPage from '@/pages/MainLibraryPage/MainLibraryPage.vue'
import TextFragmentPage from '@/pages/TextFragmentPage/TextFragmentPage.vue'
import UserEditPage from '@/pages/UserEditPage/UserEditPage.vue'
import TextEditPage from '@/pages/TextEditPage/TextEditPage.vue'
import SearchResultPage from '@/pages/SearchResultPage/SearchResultPage.vue'
import TextFragmentEditPage from '@/pages/TextFragmentEditPage/TextFragmentEditPage.vue'

const routes = [
  { path: '/', component: HomePage },
  { path: '/search', component: SearchResultPage },
  { path: '/user', component: UserPage },
  { path: '/user/edit', component: UserEditPage },
  { path: '/text', component: TextPage },
  { path: '/text/edit', component: TextEditPage },
  { path: '/page', component: TextFragmentPage },
  { path: '/page/edit', component: TextFragmentEditPage },
  { path: '/text/add', component: TextEditPage },
  { path: '/text/:id', component: TextPage },
  { path: '/text/:id/edit', component: TextEditPage },
  { path: '/library', component: LibraryPage },
  { path: '/about', component: AboutPage },
  { path: '/:pathMatch(.*)*', component: NotFoundPage }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
