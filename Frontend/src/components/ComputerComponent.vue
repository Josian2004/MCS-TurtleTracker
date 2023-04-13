<template>
    <div @click="openComputerPage" class="hover:bg-darkBlue  bg-opacity-50 hover:bg-opacity-50 border-b-4 border-darkerBlue p-2 leading-5 cursor-pointer text-slate-200">
        <div  class="flex justify-between">
            <div class="flex justify-start content-center">
                <div @mouseover="hoverDeviceImage = true" @mouseleave="hoverDeviceImage = false" class="w-10 h-10 flex mr-1 justify-center">
                    <img v-if="!hoverDeviceImage" class="w-full h-full" :src="deviceImageLink"/>
                    <p v-if="hoverDeviceImage" class="text-2xl mt-1">{{computer?.computerId}}</p>
                </div>
                
                <h5 class="text-2xl truncate mt-0.5 has-tooltip">{{displayName}}</h5>
            </div>
            <small class="text-base text-green-600" :class="{orange_status: orangeStatusColor ,disconnected: isDisconnected || computer?.status == 'Error'}">status: {{computer?.status}}</small>
        </div>
        <div class="flex justify-between">
            <h6 class="text-lg"></h6>
            <small @mouseover="hoverDateTime = true" @mouseleave="hoverDateTime = false" v-if="!hoverDateTime" class="text-sm text-right" :class="{disconnected: isDisconnected}">last update: {{formatedDateTime}}</small>
            <small @mouseover="hoverDateTime = true" @mouseleave="hoverDateTime = false" v-if="hoverDateTime" class="text-sm text-right" :class="{disconnected: isDisconnected}">last update: {{date}}</small>
        </div>
        <div v-if="isTurtle" class="flex justify-between border-t-2 border-darkerBlue mt-5">
            <div class="flex justify-start content-center"> 
                <img class="w-6 h-6 mr-1 ml-1" :src="dimensionImageLink"/>
                <h6 class="text-base">x:{{location.x}} / y:{{location.y}} / z:{{location.z}}</h6>
            </div>   
            <div :class="{
                /*@ts-ignore*/
                no_fuel: computer?.fuelLevel < computer?.fuelLimit*0.001}" class="w-1/5 bg-gray-200 group h-4 dark:bg-gray-700 self-center">
                <div :class="{ 
                    /*@ts-ignore*/
                    low_fuel: computer?.fuelLevel < computer?.fuelLimit*0.5, no_fuel: computer?.fuelLevel < computer?.fuelLimit*0.1}" class="bg-green-700 h-full flex" :style="{'width': (this.computer.fuelLevel/this.computer.fuelLimit) * 100 + '%'}">      
                    <h4 style="margin-top: -2px" class="text-sm text-center ml-5 text-transparent group-hover:text-slate-200">{{computer?.fuelLevel}}/{{computer?.fuelLimit}}</h4>
                </div>
            </div>
        </div>  
    </div>
</template>

<script lang="ts">
    import type {Computer, Location} from '../interfaces';

    import { ref } from 'vue'

    import { defineComponent } from 'vue'
    import type { PropType } from 'vue'
    import { getLastLocation } from '../services/api/LocationApi';
    // eslint-disable-next-line vue/prefer-import-from-vue
    import { toNumber } from '@vue/shared';
    

    export default defineComponent({
        data() {
            return{
                isDisconnected:false,
                timeout: 0,
                location: {} as Location,
                hoverDateTime: false,
                hoverDeviceImage:false,
            };
        },

        props: {
            computer: Object as PropType<Computer>,
        },

        async mounted() {
            
            this.isDisconnected = false
            this.timeout = window.setTimeout(() => {this.isDisconnected = true}, 10 * 1000)
            if (this.isTurtle) {
                this.location = await getLastLocation(toNumber(this.computer?.computerId))

            }
            //@ts-ignore
            this.$emitter.on("new-location", (msgData:any) => {
                if (msgData.computerId == this.computer?.computerId){
                    this.location = msgData
                }

            })

        },

        created(){
        },


        watch: {
            computer() {
                this.isDisconnected = false
                if(this.timeout) clearTimeout(this.timeout)
                /*@ts-ignore*/
                this.timeout = setTimeout(() => {this.isDisconnected = true}, 10 * 1000)
            }
        },


        
        computed: {
        formatedDateTime() {
            //@ts-ignore
            const dateTime = new Date(this.computer?.creationDateTime)
            return this.addZero(dateTime.getHours()) + ":" + this.addZero(dateTime.getMinutes()) + ":" + this.addZero(dateTime.getSeconds())
        },
        date() {
            //@ts-ignore
            const dateTime = new Date(this.computer?.creationDateTime)
            return dateTime.toDateString()
        },
        isTurtle(){
            if (this.computer?.device == "Normal Turtle" || this.computer?.device == "Advanced Turtle"){
                return true
            } else  {
                return false
            }
        },
        orangeStatusColor(){
            return this.computer?.status == "Need Player" || this.computer?.status == "Manually Terminated"
        },
        displayName(){
            if (this.computer?.computerLabel == "" || this.computer?.computerLabel == null) {
                return this.computer?.device + " - " + this.computer?.computerId
            } 
            else {
                return this.computer?.computerLabel
            }
        },
        percentageFuel() {
            if (this.computer != null) {
                //@ts-ignore
                return (this.computer.fuelLevel/this.computer.fuelLimit) * 100 + "%"
            } else {
                return "0%"
            }
            
        },
        deviceImageLink(){
            if (this.computer?.device == "Normal Computer"){
                return "/cc_devices/Computer.png"
            }
            if (this.computer?.device == "Advanced Computer"){
                return "cc_devices/Advanced_Computer.png"
            }
            if (this.computer?.device == "Normal Pocket Computer"){
                return "/cc_devices/Normal_Pocket_Computer.png"
            }
            if (this.computer?.device == "Advanced Pocket Computer"){
                return "/cc_devices/Advanced_Pocket_Computer.png"
            }
            if (this.computer?.device == "Normal Turtle"){
                return "/cc_devices/Normal_Turtle.png"
            }
            if (this.computer?.device == "Advanced Turtle"){
                return "/cc_devices/Advanced_Turtle.png"
            } else {
                return ""
            }
        },
        dimensionImageLink(){
            if (this.location?.dimension == "Overworld"){
                return "/items/grass_block.png"
            }
            if (this.location?.dimension == "Nether"){
                return "/items/netherrack.png"
            }
            if (this.location?.dimension == "End"){
                return "/items/end_stone.png"
            }
            else {
                return "/items/bedrock.png"
            }
        },
        

        },

        methods: {
            addZero(i:any) {
                if (i < 10) {i = "0" + i}
                return i;
                },
            openComputerPage(){
                this.$router.push({
                    name: "computer",
                    query: {
                        id: toNumber(this.computer?.computerId)
                    }
                })
            }
        }
    })
</script>

<style scoped>
    .disconnected {
        @apply text-red-600 !important;
    }
    .orange_status{
        @apply text-orange-600 !important;
    }
    .low_fuel{
        @apply bg-orange-600 !important;
    }
    .no_fuel{
        @apply bg-red-600 !important;
    }
</style>