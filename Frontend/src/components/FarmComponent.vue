<template>
    <div v-if="sortComputersById.length > 0" :class="{is_collapsed_bg:collapsed}" class="w-[98%] float-left m-2 mt-0 mb-8 bg-opacity-40 bg-darkBlue rounded-br-none rounded-bl-none border-darkBlue rounded">
        <div @click="collapsed = !collapsed" class="hover:cursor-pointer content-center w-full bg-darkerBlue border-4 rounded-br-none rounded-bl-none border-darkerBlue rounded text-slate-200  h-11 flex justify-between">
            <h1 class="pl-1 text-2xl">{{system?.Name}}</h1>
            <div class="flex-row justify-items-center align-middle items-center mr-2"> 
                <small class=" text-base leading-none">ID: {{system?.SystemID}}</small>

                <svg  xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" :class="{is_collapsed_reverse:collapsed}" class="m-auto -mt-1 -mb-1 w-5 h-5 hover:cursor-pointer">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 8.25l-7.5 7.5-7.5-7.5" />
                </svg>
                  
            </div>
            
        </div>
        <div v-if="!collapsed" class="border border-t-0 border-darkerBlue rounded-b">
            <ComputerComponent v-for="computer in sortComputersById" :key="computer.computerId"
                          :computer="computer"
                          ref="computers"
            />
            <!-- <div v-if="sortComputersById.length == 0" class="w-full text-lg bg-darkBlue bg-opacity-50 p-2 text-slate-200">
                <h2>No Turtles in this System</h2>
            </div> -->
        </div>
        


    
    </div>
</template>

<script lang="ts">
 import { defineComponent, ref } from 'vue'
 import type { PropType } from 'vue'
 import type {Log, Computer, System} from '../interfaces';
 import { getAllLogs } from '../services/api/LogApi';
 import { getAllComputers} from '../services/api/ComputerApi';
 import ComputerComponent from './ComputerComponent.vue';

 // eslint-disable-next-line vue/prefer-import-from-vue
 import { toNumber } from '@vue/shared';



export default defineComponent({
    data() {
        return {
            computers: ref([] as Computer[]),
            collapsed: true,
        }
    },

    props: {
        system: Object as PropType<System>
    },
    async mounted(){
        this.$refs.computers
        this.computers = await getAllComputers()

        /*@ts-ignore*/
        this.$emitter.on("new-computer", (msgData:any) => {
            const message = msgData
            var computer = {} as Computer;
            computer = message;
            this.saveComputer(computer);
            
        });

        /*@ts-ignore*/
        this.$emitter.on("collapse", (collapse:any) => {
            this.collapsed = collapse            
        });
    },
    created(){

    },
    watch: {

    },
    computed: {
        sortComputersById(){
            const computersByFarm = [] as Computer[]
            this.computers.forEach(computer => {
                if (computer.mcsSystemId == this.system?.SystemID){
                    computersByFarm.push(computer)
                }
            });
            const sortedComputers = computersByFarm.slice().sort((a, b) => toNumber(a.computerId) - toNumber(b.computerId))
            return sortedComputers
        }
    },
    methods: {
        saveComputer(computer: Computer) {
            for (let i = 0; i < this.computers.length; i++) {
                const selectedComputer = this.computers[i]
                if (selectedComputer.computerId == computer.computerId){
                    this.computers[i] = computer
                    return
                }
            }
            this.computers.push(computer);
        },
    },
    components: { ComputerComponent }

})
</script>

<style scoped>
    .is_collapsed_bg {
        --tw-bg-opacity: 0;
    }
    .is_collapsed_reverse {
        transform: rotate(180deg);
    }
</style>
