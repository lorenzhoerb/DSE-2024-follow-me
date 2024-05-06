<template>
  <div>
    <h1 class="font-semibold text-3xl pb-2">Inventory</h1>
      <div class="card"  v-if="loading">
        <div class="animate-pulse w-100 ">
          <div class="flex justify-between mb-4 gap-2">
            <div v-for="idx in 6" :key="idx" class="h-4 bg-gray-200 rounded-full w-48 mb-4"></div>
          </div>
            <div v-for="idx in 10" :key="idx" class="h-4 bg-gray-200 rounded-full w-full mb-6"></div>
        </div>
      </div>
      <InventoryTable :vehicles="vehicles" v-else/>
  </div>
</template>

<script setup lang="ts">
import {ref, onMounted} from 'vue';
import type { Ref } from 'vue'
import InventoryTable from '@/components/vehicle/InventoryTable.vue'
import {vehicles} from '@/mock'

const loading: Ref<boolean> = ref(false);

</script>
