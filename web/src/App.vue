<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
</script>

<template>
  <header class="bg-gray-800 py-4 flex gap-4 items-center">
    <h1 class="text-4xl font-semibold text-red-500">Hello World</h1>
    <div>
      <nav class="flex gap-2">
        <RouterLink to="/" class="bg-white p-2 rounded">Dashboard</RouterLink>
        <RouterLink to="/vehicles" class="bg-white p-2 rounded"
          >Vehicles</RouterLink
        >
      </nav>
    </div>
  </header>

  <RouterView />
</template>
