<template>
  <div class="h-full w-full flex flex-col">
    <h4 class="font-semibold text-xl mb-3">Vehicles</h4>
    <div class="w-full flex-1 relative">
      <div class="absolute top-0 bottom-0 w-full overflow-y-scroll">
        <div v-for="(v, index) in vehicles" :key="index">
          <VehicleListEntry :vehicle="v"></VehicleListEntry>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { VehicleCardEntry } from '@/types'
import VehicleListEntry from './VehicleListEntry.vue'
defineProps<{
  vehicles: VehicleCardEntry[]
}>()

</script>
