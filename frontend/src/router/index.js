import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import NotFoundView from '@/views/NotFoundView.vue'
import AboutPage from '@/components/AboutPage.vue'
import TextView from '@/views/TextView.vue'
import UserView from '@/views/UserView.vue'
import LibraryView from '@/views/LibraryView.vue'

const routes = [
  { path: '/', component: HomeView },
  { path: '/about', component: AboutPage },
  { path: '/text', component: TextView },
  { path: '/user', component: UserView },
  { path: '/library', component: LibraryView },
  { path: '/:pathMatch(.*)*', component: NotFoundView } // Redirect unknown paths to 404
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
