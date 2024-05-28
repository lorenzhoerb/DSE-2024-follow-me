<template>
  <ol>
    <li v-for="(log, idx) in logs" :key="idx" class="flex gap-2 p-1 log-record text-sm">
      <div>
        <span class="font-medium text-gray-900 whitespace-nowrap">[{{ log.timestamp.toISOString() }}]</span>
      </div>
      <div>
        <span class="font-medium text-gray-900 whitespace-nowrap">[{{ log.logLevel }}]: </span>
      </div>
      <div class="flex-1 w-full">
        <span class="font-medium text-gray-900">{{ log.message }} </span>
      </div>
    </li>
  </ol>
</template>

<script setup lang="ts">
import type { LogInfo } from '@/types'
defineProps<{
  logs: LogInfo[]
}>()
</script>

<style scoped lang="postcss">
.log-record:nth-child(2n) {
  @apply bg-zinc-50 rounded;
}
</style>
