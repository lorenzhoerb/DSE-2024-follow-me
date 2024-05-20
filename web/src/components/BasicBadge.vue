<template>
  <span class="bg-green-100 text-green-600 text-xs me-2 px-2 py-0.5 rounded">{{ text }}</span>
</template>

<script setup lang="ts">
defineProps({
  text: String
})
</script>
