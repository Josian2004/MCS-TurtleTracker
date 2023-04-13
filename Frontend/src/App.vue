<!-- bg-[url('./assets/mcsynergy-13.svg')] -->

<template>
  <div>
    <HeaderComponent/>
    <RouterView/>
  </div>
</template>





<script lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HeaderComponent from './components/HeaderComponent.vue'

export default {
  
  components: {
      HeaderComponent,
    }
  }
</script>

<style>

  

</style>
