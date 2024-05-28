<template>
  <div>
    <h1 class="font-semibold text-3xl pb-2">Inventory</h1>
    <div class="card" v-if="loading">
      <div class="animate-pulse w-100">
        <div class="flex justify-between mb-4 gap-2">
          <div v-for="idx in 6" :key="idx" class="h-4 bg-gray-200 rounded-full w-48 mb-4"></div>
        </div>
        <div v-for="idx in 10" :key="idx" class="h-4 bg-gray-200 rounded-full w-full mb-6"></div>
      </div>
    </div>
    <InventoryTable :vehicles="vehicleListEntries" v-else />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import type { Ref } from 'vue'
import InventoryTable from '@/components/vehicle/InventoryTable.vue'
import { fetchAll } from '@/services/VehicleService'
import type { VehicleInfo } from '@/types'

const loading: Ref<boolean> = ref(false)
const vehicleInfo: Ref<VehicleInfo[]> = ref([])

const vehicleListEntries = computed(() =>
  vehicleInfo.value
    .filter((v) => v.info !== null && v.info !== undefined)
    .map((v) => ({
      ...v.info,
      isInFollowMeMode: v.status ? v.status.followMeModeActive : false,
      pairedWith: v.status?.pairedVin
    }))
)

// Call fetchVehicles when the component is mounted
onMounted(() => {
  loading.value = true
  fetchAll()
    .then((data) => {
      vehicleInfo.value = data
      loading.value = false
    })
    .catch((error) => {
      console.error(error)
      loading.value = false
    })
})
</script>
