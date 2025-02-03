import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/pages/HomePage/HomePage.vue'
import NotFoundView from '@/pages/NotFoundPage/NotFoundPage.vue'
import AboutPage from '@/pages/AboutPage/AboutPage.vue'
import TextView from '@/pages/TextPage/TextPage.vue'
import UserView from '@/pages/UserPage/UserPage.vue'
import LibraryView from '@/pages/MainLibraryPage/MainLibraryPage.vue'
import TextFragmentView from '@/pages/TextFragmentPage/TextFragmentPage.vue'
import UserEditView from '@/pages/UserEditPage/UserEditPage.vue'
import TextEditView from '@/pages/TextEditPage/TextEditPage.vue'
import SearchResults from '@/pages/SearchResultPage/SearchResultPage.vue'
import TextFragmentEditView from '@/pages/TextFragmentEditPage/TextFragmentEditPage.vue'

const routes = [
  { path: '/', component: HomeView },
  { path: '/about', component: AboutPage },
  { path: '/text', component: TextView },
  { path: '/text/edit', component: TextEditView },
  { path: '/search', component: SearchResults },
  { path: '/user', component: UserView },
  { path: '/user/edit', component: UserEditView },
  {path: '/fragment', component: TextFragmentView},
  {path: '/fragment/edit', component: TextFragmentEditView},

  { path: '/library', component: LibraryView },
  { path: '/:pathMatch(.*)*', component: NotFoundView } // Redirect unknown paths to 404
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
