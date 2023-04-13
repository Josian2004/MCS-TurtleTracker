<template>
    <div @click="openLogDetailPage" :class="{clickable:true}" class="border-t-4 bg-opacity-50 border-darkerBlue bg-darkBlue p-2 leading-5 text-slate-200">
        <div class="flex justify-between">
            <h5 class="mb-1 text-2xl">{{displayedName}}</h5>
            <small class="text-lg">{{formatedDateTime}}</small>
        </div>
        <div class="flex justify-between">
            <h6 class="text-xl">{{log?.message}}</h6> 
            <small class="text-lg" :class="{warning_log: log?.messageType == 'Warning', error_log: log?.messageType == 'Error'}">[{{log?.messageType}}]</small>
        </div>
    </div>
</template>

<script lang="ts">
    import type {Computer, Log} from '../interfaces';

    import { defineComponent, ref } from 'vue'
    import type { PropType } from 'vue'
    import { getComputerById } from '../services/api/ComputerApi';
    import { toNumber } from '@vue/shared';

    export default defineComponent({
        data() {
            return{
                computer: ref({} as Computer | null),
            };
        },

        props: {
            log: Object as PropType<Log>,
        },

        methods: {  
            addZero(i:any) {
                if (i < 10) {i = "0" + i}
                return i;
                },
            openLogDetailPage(){
                this.$router.push({
                    name: "log",
                    query: {
                        id: this.log?.id
                    }
                })                
            }
        },
        computed: {
            formatedDateTime() {
                /*@ts-ignore*/
                const dateTime = new Date(this.log?.creationDateTime)
                return dateTime.toDateString() + " - " + this.addZero(dateTime.getHours()) + ":" + this.addZero(dateTime.getMinutes()) + ":" + this.addZero(dateTime.getSeconds())
                },
            displayedName() {

                if (this.log?.computerId != null) {
                    if (this.computer?.computerLabel != null && this.computer?.computerLabel != "") {
                        return this.log?.computerId + " - " + this.computer.computerLabel
                    } else {
                        return this.log?.computerId + " - " + this.computer?.device 
                    }
                } else if (this.log?.serviceName != null) {
                        return this.log?.serviceName
                } else {
                    return "Unknown Sender"
                }
            },
            clickable() {
                return this.computer != null
            }
        },

        async mounted() {
            if (this.log?.computerId != null) {
                this.computer = await getComputerById(toNumber(this.log?.computerId))
            } else {
                this.computer = null
            }
            
        },
    })
</script>

<style scoped>
    .warning_log{
        @apply text-orange-600 !important;
    }
    .error_log{
        @apply text-red-600 !important;
    }
    .clickable{
        @apply hover:bg-opacity-80;
        @apply cursor-pointer
    }
</style>
