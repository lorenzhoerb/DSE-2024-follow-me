<template>
  <div>
    <h1 class="font-semibold text-3xl pb-2">Inventory</h1>
    <div class="card" v-if="loading">
      <div class="animate-pulse w-100">
        <div v-for="idx in 30" :key="idx" class="h-2.5 bg-gray-200 rounded-full w-full mb-4"></div>
      </div>
    </div>
    <div v-else class="card">
      <LogList :logs="logs" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { Ref } from 'vue'
import LogList from '@/components/LogList.vue'
import type { LogInfo } from '@/types'

const loading: Ref<boolean> = ref(false)
const logs: LogInfo[] = [
  {
    timestamp: new Date(),
    message: 'This is a pessage'
  },
  {
    timestamp: new Date(),
    message: 'This is a pessage'
  },
  {
    timestamp: new Date(),
    message: 'This is a pessage'
  }
]
</script>
