import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AuthView from '../views/AuthView.vue'
import NotFoundView from '../views/NotFoundView.vue'

const routes = [
  { path: '/', component: HomeView },
  { path: '/auth', component: AuthView },
  { path: '/:pathMatch(.*)*', component: NotFoundView } // Redirect unknown paths to 404
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
