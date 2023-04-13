<template>
    <div class="flex justify-between">
        <div @click="returnTo" class="cursor-pointer">
            <img class="w-52 h-auto" alt="MCST Logo" src="../assets/mcsynergy-12.svg"/>
        </div>
        <a href="https://github.com/Josian2004/s3-portfolio/blob/main/Individual/README.md#mcsturtletracker" target="_blank" class="mr-10 mt-3">
            <h1 class="text-slate-200 text-4xl">About</h1>
        </a>
    </div>
    
</template>

<script lang="ts">

import { defineComponent } from 'vue'
    

    export default defineComponent({
        data() {
            return{
                
            };
        },

            

        mounted() {
           
        },

        created(){
        },


        watch: {
           
        },


        
        computed: {
            
        
  },

        methods: {
            returnTo(){
                if (this.$route.name == "home") {
                    window.location.href = "https://portal.mcsynergy.nl"
                } else {
                    this.$router.push("/")
                }
            }
        }
    })
</script>

<style scoped>
    
</style>