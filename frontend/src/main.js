import { createApp } from 'vue'
import App from '@/App.vue'
import router from '@/helpers/router'
import i18n from "@/helpers/i18n/i18n";
import '@/assets/styles/main.css'

const app = createApp(App)
app.use(router)
app.use(i18n)

app.config.globalProperties.$t = i18n.global.t
app.config.globalProperties.$i18n = i18n.global

app.mount('#app')