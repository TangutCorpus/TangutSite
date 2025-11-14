import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

const isDevelopment = process.env.NODE_ENV === "development"

export default defineConfig({
    plugins: [
        vue(),
        ...(isDevelopment ? [await import('vite-plugin-vue-devtools').then(m => m.default())] : []),
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        },
    },
})
