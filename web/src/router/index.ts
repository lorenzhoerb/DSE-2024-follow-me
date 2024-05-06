import DashboardView from '@/views/DashboardView.vue'
import { createRouter, createWebHistory } from 'vue-router'
import { toPageTitle } from '@/utils'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: DashboardView
    },
    {
      path: '/inventory',
      component: () => import('@/views/InventoryView.vue'),
      meta: {
        title: 'Inventory'
      }
    }
  ]
})

router.beforeEach((to) => {
  document.title = toPageTitle(to?.meta?.title)
})

export default router
