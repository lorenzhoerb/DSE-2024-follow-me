<template>
  <div
    class="flex-1 h-full grid grid-cols-[350px_minmax(900px,_1fr)] grid-rows-3 grid-flow-col gap-4"
  >
    <div class="w-full row-span-3 card flex flex-col">
      <VehicleListCard :vehicles="inventory"></VehicleListCard>
    </div>

    <div class="card row-span-2 col-span-2">
      <MainMap></MainMap>
    </div>

    <div class="col-span-2 w-full h-full flex flex-col">
      <div class="card flex flex-col flex-1">
        <h3 class="font-semibold text-xl mb-2">Logs</h3>
        <div class="w-full flex-1 relative">
          <div class="absolute top-0 bottom-0 w-full overflow-y-scroll">
            <LogListConnected></LogListConnected>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import type { Ref } from 'vue'
import VehicleListCard from '@/components/vehicle/VehicleListCard.vue'
import MainMap from '@/components/map/MainMap.vue'
import LogListConnected from '@/components/logs/LogListConnected.vue'
import { fetchAll } from '@/services/VehicleService'
import type { VehicleInfo } from '@/types'

const vehicleInfo: Ref<VehicleInfo[]> = ref([])

const fetchAllVehicleData = () => {
  fetchAll().then((data) => {
    vehicleInfo.value = data
    console.log(data)
  })
}

const inventory = computed(() =>
  vehicleInfo.value
    .filter((v) => v.info !== null && v.info !== undefined)
    .map((v) => ({
      ...v.info,
      isInFollowMeMode: v.status ? v.status.followMeModeActive : false
    }))
)

// Call fetchVehicles when the component is mounted
onMounted(() => {
  fetchAllVehicleData()
})
</script>
