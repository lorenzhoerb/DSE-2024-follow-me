<template>
  <div
    class="flex-1 h-full grid grid-cols-[350px_minmax(900px,_1fr)] grid-rows-3 grid-flow-col gap-4"
  >
    <div class="w-full row-span-3 card flex flex-col">
      <VehicleListCard :vehicles="vehicles"></VehicleListCard>
    </div>

    <div class="card row-span-2 col-span-2">map</div>
    <div class="card col-span-2">log</div>
  </div>
  <!-- <div class="grid grid-cols-[350px_minmax(900px,_1fr)] grid-rows-3 grid-flow-col gap-4 flex-1">
    <div class="card row-span-3 overflow-y-scroll">
      <h4 class="font-semibold text-xl">Vehicles</h4>
      <div v-for="(v, index) in vehicles" :key="index">
        <VehicleListEntry :vehicle="v"></VehicleListEntry>      
      </div>
    </div>
    <div class="card row-span-2 col-span-2">map</div>
    <div class="card col-span-2">log</div>
  </div> -->
</template>

<script setup lang="ts">
import { vehicles } from '@/mock'
import VehicleListCard from '@/components/vehicle/VehicleListCard.vue'
</script>
