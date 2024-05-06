<script setup lang="ts">
import { RouterLink } from 'vue-router'
</script>

<template>
  <div class="border-b border-gray-200">
    <nav class="flex gap-8 items-center space-between mx-auto container py-6 text-gray-900">
      <RouterLink to="/">
        <h2 class="font-semibold text-2xl uppercase">
          <span class="px-1 py-1 rounded">Follow</span
          ><span class="px-2 py-1 rounded bg-blue-600 font-uppercase text-white">Me</span>
        </h2>
      </RouterLink>
      <ul class="flex gap-2 font-medium">
        <li
          class="hover:bg-gray-100 transition-colors duration-300 ease-in-out bg-white px-2 py-1 rounded"
        >
          <RouterLink to="/vehicles" c>Vehicles</RouterLink>
        </li>
        <li
          class="hover:bg-gray-100 transition-colors duration-300 ease-in-out bg-white px-2 py-1 rounded"
        >
          <RouterLink to="/logs">Logs</RouterLink>
        </li>
      </ul>
    </nav>
  </div>
</template>
