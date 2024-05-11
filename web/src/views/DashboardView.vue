<template>
  <div
    class="flex-1 h-full grid grid-cols-[350px_minmax(900px,_1fr)] grid-rows-3 grid-flow-col gap-4"
  >
    <div class="w-full row-span-3 card flex flex-col">
      <VehicleListCard :vehicles="vehicles"></VehicleListCard>
    </div>

    <div class="card row-span-2 col-span-2">
      <MainMap></MainMap>
    </div>

    <div class="col-span-2 w-full h-full flex flex-col">
      <div class="card flex flex-col flex-1">
        <h3 class="font-semibold text-xl mb-2">Logs</h3>
        <div class="w-full flex-1 relative">
          <div class="absolute top-0 bottom-0 w-full overflow-y-scroll">
            <LogList :logs="sortedLogs" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { vehicles } from '@/mock'
import VehicleListCard from '@/components/vehicle/VehicleListCard.vue'
import LogList from '@/components/LogList.vue'
import MainMap from '@/components/map/MainMap.vue'

import { logs } from '@/mock'

const sortedLogs = computed(() => {
  return logs.sort()
})
</script>
