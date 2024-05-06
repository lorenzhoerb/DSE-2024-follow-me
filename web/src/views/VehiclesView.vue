<script setup lang="ts">
import VehicleListEntry from '@/components/vehicle/VehicleListEntry.vue'
import { VehicleType, type BasicVehicleInfo } from '@/types'
const vehicle: BasicVehicleInfo = {
  vin: 'MED-KA-2001',
  isInFollowMeMode: true,
  manufacturer: 'Mercedes',
  model: 'KA1',
  type: VehicleType.FV
}
</script>

<template>
  <div>
    <h1>Vehicles</h1>
    <div class="card w-96">
      <VehicleListEntry :vehicle="vehicle"></VehicleListEntry>
    </div>
  </div>
</template>
