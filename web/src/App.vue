<script setup lang="ts">
import { RouterView } from "vue-router";
import NavHeader from "@/components/NavHeader.vue";
</script>

<template>
  <div>
    <header>
      <NavHeader />
    </header>

    <RouterView />
  </div>
</template>
