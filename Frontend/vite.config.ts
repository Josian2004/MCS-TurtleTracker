import { fileURLToPath, URL } from 'node:url'
import { VitePWA } from 'vite-plugin-pwa'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    port: 8080,
    host: true,
  },
  plugins: [
    vue(), 
    vueJsx(), 
    VitePWA({ 
      // registerType: 'autoUpdate', 
      injectRegister: 'auto', 
      strategies: 'generateSW',
      devOptions: {
        enabled: true
      },
      workbox: {
        globPatterns: ['**/*.{js,css,html,ico,png,svg}']
      },
      includeAssets: ['favicon.ico', 'mcsynergy-07-192x192.png', 'mcsynergy-07-512x512.png'],
      manifest: {
        name: 'MCS Turtle Tracker',
        short_name: 'MCST',
        description: 'Turtle Tracker for MCS',
        theme_color: '#0d0d19',
        icons: [
          {
            src: 'mcsynergy-07-192x192.png',
            sizes: '192x192',
            type: 'image/png'
          },
          {
            src: 'mcsynergy-07-512x512.png',
            sizes: '512x512',
            type: 'image/png'
          }
        ]
      }
    })
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})
