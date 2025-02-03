import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/pages/HomeView.vue'
import NotFoundView from '@/pages/NotFoundView.vue'
import AboutPage from '@/pages/AboutView.vue'
import TextView from '@/pages/TextView.vue'
import UserView from '@/pages/UserView.vue'
import LibraryView from '@/pages/LibraryView.vue'
import TextFragmentView from '@/pages/TextFragmentView.vue'
import UserEditView from '@/pages/UserEditView.vue'
import TextEditView from '@/pages/TextEditView.vue'
import SearchResults from '@/pages/SearchResults.vue'
import TextFragmentEditView from '@/pages/TextFragmentEditView.vue'

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
