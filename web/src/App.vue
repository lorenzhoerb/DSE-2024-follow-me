<script setup lang="ts">
import { RouterView } from "vue-router";
import NavHeader from "@/components/NavHeader.vue";
</script>

<template>
  <div class="flex flex-col min-h-screen">
    <header class="sticky top-0 bg-white z-50">
      <NavHeader />
    </header>

    <main class="flex-1 bg-gray-100 overflow-y-auto p-8">
      <RouterView />
    </main>
  </div>
</template>
