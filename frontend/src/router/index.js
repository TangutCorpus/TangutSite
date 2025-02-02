import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import NotFoundView from '@/views/NotFoundView.vue'
import AboutPage from '@/views/AboutView.vue'
import TextView from '@/views/TextView.vue'
import UserView from '@/views/UserView.vue'
import LibraryView from '@/views/LibraryView.vue'
import TextFragmentView from '@/views/TextFragmentView.vue'
import UserEditView from '@/views/UserEditView.vue'
import TextEditView from '@/views/TextEditView.vue'
import SearchResults from '@/views/SearchResults.vue'
import TextFragmentEditView from '@/views/TextFragmentEditView.vue'

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
