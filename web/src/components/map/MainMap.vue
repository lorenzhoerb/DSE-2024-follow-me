<template>
  <div class="w-full h-full">
    <GoogleMap
      api-key="AIzaSyDsxhW-nKqat4HnyYSRDkJTP4SZdJoUaKI"
      style="width: 100%; height: 100%"
      :center="centerMain"
      :zoom="15"
      :styles="mapsStyling"
      :street-view-control="false"
      :map-type-control="false"
    >
      <Marker
        :options="{
          position: { lat: v.data?.location?.latitude, lng: v.data?.location?.longitude }
        }"
        v-for="(v, index) in positionVehicles"
        :key="index"
      >
        <InfoWindow v-model="infowindow">
          <div id="content">
            <h6 class="text-md font-semibold">{{ v.vin }}</h6>
            <ul>
              <li>Vehicle Type: {{ v.info?.type }}</li>
              <li>Velocity: {{ v.data?.velocity }}km/h</li>
              <li>Lane: {{ v.data?.lane }}</li>
              <li>FollowMe Mode: {{ v.status?.followMeModeActive ? 'Active' : 'Unactive' }}</li>
              <li>Paired With: {{ v.status?.pairedVin }}</li>
              <li>Target Velocity: {{ v.data?.targetControl?.targetVelocity }}km/h</li>
              <li>Target Lane: {{ v.data?.targetControl?.targetLane }}</li>
            </ul>
          </div>
        </InfoWindow>
      </Marker>
    </GoogleMap>
  </div>
</template>

<script setup lang="ts">
import { GoogleMap, Marker, InfoWindow } from 'vue3-google-map'
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { mapsStyling } from '@/assets/mapsStyling'
import type { VehicleInfo } from '@/types'
import { computed } from 'vue'

const props = defineProps<{
  vehicles: VehicleInfo[]
}>()

const positionVehicles = computed(() =>
  props.vehicles.filter((v) => v.data !== null && v.data !== undefined)
)
console.log(props.vehicles)

const centerMain = { lat: 48.1986581, lng: 16.3658877 }
const center = ref({ lat: 48.1986581, lng: 16.3658877 })
const infowindow = ref(false) // Will be open when mounted

// Function to update marker position
const updateMarkerPosition = () => {
  // Generate random latitude and longitude for demonstration
  const newLat = center.value.lat + (Math.random() - 0.5) * 0.001
  const newLng = center.value.lng + (Math.random() - 0.5) * 0.001
  center.value = { lat: newLat, lng: newLng }
}

// Update marker position every second
let intervalId: number
onMounted(() => {
  intervalId = setInterval(updateMarkerPosition, 1500)
})

// Clear interval when component is unmounted
onBeforeUnmount(() => {
  clearInterval(intervalId)
})
</script>
