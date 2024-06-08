<script setup lang="ts">
import LogList from '@/components/logs/LogList.vue'
import { ref, onMounted, onUnmounted } from 'vue'
import type { Ref } from 'vue'
import { fetchEvents } from '@/services/EventLogService';
import { Client } from '@stomp/stompjs'

const loading: Ref<boolean> = ref(false)
const eventLogEntries: Ref<any[]> = ref([])


const stompClient = new Client({
    brokerURL: 'ws://' + window.location.host + '/api/events/ws',
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
})

stompClient.onConnect = (frame) => {
    stompClient.subscribe('/topic/events', (eventBody) => {
        const event = JSON.parse(eventBody.body)
        event.timestamp = new Date(event.timestamp)
        eventLogEntries.value.unshift(event) 
        console.log(event);
    })
}


stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error)
    if (error.target) {
        console.error('WebSocket Error - Target:', error.target)
    }
}

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message'])
    console.error('Additional details: ' + frame.body)
}

const fetchEventLogs = () => {
    loading.value = true
    fetchEvents()
        .then((data) => {
            eventLogEntries.value = data
            loading.value = false
        })
        .catch((err) => console.log(err))
}

// Call fetchVehicles when the component is mounted
onMounted(() => {
    fetchEventLogs()
    stompClient.activate();
})

onUnmounted(() => {
    stompClient.deactivate()
})

</script>
<template>
    <div>
        <div class="animate-pulse w-100" v-if="loading">
            <div v-for="idx in 20" :key="idx" class="h-4 bg-gray-200 rounded-full w-full mb-4"></div>
        </div>
        <LogList :logs="eventLogEntries" v-else></LogList>

    </div>
</template>