import { createApp } from 'vue'
import { createPinia } from 'pinia'
import mitt from 'mitt'

import App from './App.vue'
import router from './router/index'

import "./index.css"

const $emitter = mitt()

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.config.globalProperties.$emitter = $emitter

app.mount('#app')

export default app