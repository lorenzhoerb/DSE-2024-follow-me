<template>
  <ol>
    <li v-for="(log, idx) in logs" :key="idx">
      <span class="mr-5 font-medium text-gray-900 whitespace-nowrap"
        >[{{ log.timestamp.toISOString() }}]</span
      >
      <span class="font-medium text-gray-900">{{ log.message }} </span>
    </li>
  </ol>
</template>

<script setup lang="ts">
import type { LogInfo } from '@/types'
defineProps<{
  logs: LogInfo[]
}>()
</script>
