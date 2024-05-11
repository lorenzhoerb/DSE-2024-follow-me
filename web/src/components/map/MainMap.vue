<script setup>
import { GoogleMap, Marker, InfoWindow } from 'vue3-google-map'
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { mapsStyling } from '@/assets/mapsStyling'
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
let intervalId
onMounted(() => {
  intervalId = setInterval(updateMarkerPosition, 1500)
})

// Clear interval when component is unmounted
onBeforeUnmount(() => {
  clearInterval(intervalId)
})
</script>

<template>
  <GoogleMap
    api-key="AIzaSyDsxhW-nKqat4HnyYSRDkJTP4SZdJoUaKI"
    style="width: 100%; height: 100%"
    :center="centerMain"
    :zoom="15"
    :styles="mapsStyling"
    :street-view-control="false"
    :map-type-control="false"
  >
    <Marker :options="{ position: center }" @click="console.log('hello')">
      <InfoWindow v-model="infowindow">
        <div id="content">
          <h6 class="text-md font-semibold">VIN-234-23</h6>
          <ul>
            <li>Velocity: 10km/h</li>
            <li>Lane: 1</li>
            <li>FollowMe Mode: Active</li>
            <li>Target Velocity: 200km/h</li>
            <li>Target Lane: 2</li>
          </ul>
        </div>
      </InfoWindow>
    </Marker>
  </GoogleMap>
</template>
