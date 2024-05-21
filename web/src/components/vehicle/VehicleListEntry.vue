<template>
  <div
    class="gap-2 w-full p-3 leading-tight transition-all rounded-lg outline-none text-start hover:bg-gray-100 transition-colors duration-200 ease-in-out hover:cursor-pointer"
  >
    <div class="flex justify-between items-center">
      <h6
        class="font-sans text-base antialiased font-semibold leading-relaxed tracking-normal text-blue-gray-900"
      >
        {{ vehicle.vin }}
      </h6>
      <BasicBadge v-if="vehicle.isInFollowMeMode" text="FollowMe"></BasicBadge>
    </div>
    <p class="block font-sans text-sm antialiased font-normal leading-normal text-gray-700">
      <span>{{ vehicleType }}</span> • <span>{{ vehicle.manufacturer }}</span> •
      <span>{{ vehicle.model }}</span>
    </p>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import BasicBadge from '@/components/BasicBadge.vue'
import type { VehicleCardEntry } from '@/types'
import { getVehicleTypeLabel } from '@/utils'

const props = defineProps<{
  vehicle: VehicleCardEntry
}>()

const vehicleType = computed(() => getVehicleTypeLabel(props.vehicle.type))
</script>
