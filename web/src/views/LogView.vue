<template>
  <div>
    <h1 class="font-semibold text-3xl pb-2">Logs</h1>
    <div class="card">
      <LogListConnected></LogListConnected>
    </div>
  </div>
</template>

<script setup lang="ts">
import LogListConnected from '@/components/logs/LogListConnected.vue'
</script>
