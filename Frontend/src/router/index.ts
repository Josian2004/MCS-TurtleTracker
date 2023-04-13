import ComputerPage from '../views/ComputerPage.vue'
import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../views/HomePage.vue'
import LogsPage from '../views/LogsPage.vue'
import LogDetailPage from '../views/LogDetailPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage
    },
    {
      path: '/computer',
      name: 'computer',
      component: ComputerPage,
      props: route => ({ id: route.query.id })
    },
    {
      path: '/logs',
      name: 'logs',
      component: LogsPage
    },
    {
      path: '/logs/log',
      name: 'log',
      component: LogDetailPage,
      props: route => ({ id: route.query.id })
    },
  ]
}) 

export default router
