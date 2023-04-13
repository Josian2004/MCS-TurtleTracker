


<template>
    <div class="w-full sm:w-3/5 h-full sm:float-left sm:ml-3 mt-16 bg-opacity-50 border-opacity-0 text-slate-200 mr-auto text-lg bg-darkerBlue pb-2 border-4 rounded-br-none rounded-bl-none border-darkBlue rounded">
      <div>
        <h1 class="text-slate-200 text-4xl">{{log?.message}}</h1>
      </div>
    </div>
    <div>
      <div class="w-full sm:w-1/3 sm:mr-3 sm:float-right mt-16 border-4 border-opacity-0 cursor-default select-none h-auto bg-opacity-50 bg-darkerBlue rounded-br-none rounded-bl-none border-darkBlue rounded">
        <ComputerComponent v-if="computer != null" :computer="computer"/>
      </div>
    </div>
</template>

<script lang="ts">
  import { defineComponent, ref } from "vue";
  import type { Computer, Log } from "../interfaces";
  import ComputerComponent from "../components/ComputerComponent.vue"
  import {getLogById} from "../services/api/LogApi"
  import type { PropType } from 'vue'
  import { getComputerById } from "../services/api/ComputerApi";
  import { toNumber } from "@vue/shared";

  export default defineComponent({
    data() {
      return{
          computer: ref({} as Computer | null),
          log: ref({} as Log | null),
      };
    },

    props: {
            id: {
                type: String,
                required: true,
            }
        },
        

      methods: {  
        addZero(i:any) {
            if (i < 10) {i = "0" + i}
            return i;
        },
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
        }
      },

      async mounted() {
        this.$refs.log;
        this.log = await getLogById(this.id)
        this.computer = await getComputerById(toNumber(this.log?.computerId))
      },

      components: { ComputerComponent }

  })
</script>

<style>

</style>