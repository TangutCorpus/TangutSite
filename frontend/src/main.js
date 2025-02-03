import { createApp } from 'vue'
import App from '@/App.vue'
import router from 'src/helpers/router'
import '@/assets/styles/main.css'

const app = createApp(App)
app.use(router)
app.mount('#app')