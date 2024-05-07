<template>
  <div>
    <h1 class="font-semibold text-3xl pb-2">Inventory</h1>
    <div class="card" v-if="loading">
      <div class="animate-pulse w-100">
        <div v-for="idx in 20" :key="idx" class="h-4 bg-gray-200 rounded-full w-full mb-4"></div>
      </div>
    </div>
    <div v-else class="card">
      <LogList :logs="sortedLogs" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import type { Ref } from 'vue'
import LogList from '@/components/LogList.vue'
import { logs } from '@/mock'

const loading: Ref<boolean> = ref(true)
const sortedLogs = computed(() => {
  return logs.sort()
})
</script>
