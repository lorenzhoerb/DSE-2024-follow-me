import DashboardView from "@/views/DashboardView.vue";
import { createRouter, createWebHistory } from "vue-router";
import { toPageTitle } from "@/utils";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      component: DashboardView,
    },
    {
      path: "/vehicles",
      component: () => import("@/views/VehiclesView.vue"),
      meta: {
        title: "Vehicles",
      },
    },
  ],
});

router.beforeEach((to) => {
  document.title = toPageTitle(to?.meta?.title);
});

export default router;
