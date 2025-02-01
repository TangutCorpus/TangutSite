import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import NotFoundView from '@/views/NotFoundView.vue'
import AboutPage from '@/components/AboutPage.vue'
import TextView from '@/views/TextView.vue'

const routes = [
  { path: '/', component: HomeView },
  { path: '/about', component: AboutPage },
  { path: '/text', component: TextView },
  { path: '/:pathMatch(.*)*', component: NotFoundView } // Redirect unknown paths to 404
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
