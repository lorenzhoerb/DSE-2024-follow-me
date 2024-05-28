<template>
  <div class="relative overflow-x-auto shadow rounded-lg">
    <table class="w-full text-sm text-left rtl:text-right text-gray-500">
      <thead class="text-xs text-gray-700 uppercase bg-gray-50">
        <tr>
          <th scope="col" class="px-6 py-3">VIN</th>
          <th scope="col" class="px-6 py-3">Type</th>
          <th scope="col" class="px-6 py-3">Manufacturer</th>
          <th scope="col" class="px-6 py-3">Model</th>
          <th scope="col" class="px-6 py-3 text-center">Follow Me Mode</th>
          <th scope="col" class="px-6 py-3">Paired With</th>
        </tr>
      </thead>
      <tbody>
        <tr
          class="bg-white border-b hover:bg-gray-50"
          v-for="(vehicle, idx) in vehicles"
          :key="idx"
        >
          <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
            {{ vehicle.vin }}
          </th>
          <td class="px-6 py-4">
            {{ getVehicleTypeLabel(vehicle.type) }}
          </td>
          <td class="px-6 py-4">
            {{ vehicle.manufacturer }}
          </td>
          <td class="px-6 py-4">
            {{ vehicle.model }}
          </td>
          <td class="px-6 py-4 text-center">
            <span
              v-if="vehicle.isInFollowMeMode"
              class="bg-green-100 text-green-800 text-sm font-medium me-2 px-2.5 py-0.5 rounded"
              >Active</span
            >
            <span
              v-else
              class="bg-red-100 text-red-800 text-sm font-medium me-2 px-2.5 py-0.5 rounded"
              >Inactive</span
            >
          </td>
          <td class="px-6 py-4">
            <span v-if="vehicle.pairedWith">{{ vehicle.pairedWith }}</span>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import type { VehicleTableEntry } from '@/types'
import { getVehicleTypeLabel } from '@/utils'

defineProps<{
  vehicles: VehicleTableEntry[]
}>()
</script>
