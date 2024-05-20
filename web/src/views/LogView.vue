<template>
  <div>
    <h1 class="font-semibold text-3xl pb-2">Logs</h1>
    <div class="card" v-if="loading">
      <div class="animate-pulse w-100">
        <div v-for="idx in 20" :key="idx" class="h-4 bg-gray-200 rounded-full w-full mb-4"></div>
      </div>
    </div>
    <div v-else class="card">
      <LogList :logs="eventLogEntries" v-if="eventLogEntries.length > 0" />
      <h3 v-else class="text-center font-semibold text-gray-600 text-4xl py-10">No Logs</h3>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type { Ref } from 'vue'
import LogList from '@/components/LogList.vue'
import { fetchEvents } from '@/services/EventLogService';

const loading: Ref<boolean> = ref(false)
const eventLogEntries: Ref<any[]> = ref([])



const fetchEventLogs = () => {
  loading.value = true
  fetchEvents()
    .then((data) => {
      eventLogEntries.value = data
      loading.value = false
    })
    .catch((err) => console.log(err))
}

// Call fetchVehicles when the component is mounted
onMounted(() => {
  fetchEventLogs()
})
</script>
