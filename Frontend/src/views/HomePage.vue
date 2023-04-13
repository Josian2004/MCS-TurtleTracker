<template>
    <div>
        <div class="w-full sm:w-2/3 pt-3 sm:float-left">
            <div  class="flex justify-center"> 
                <h1 @click="changeCollapse(!collapsed)" class="text-center text-slate-200 text-4xl hover:cursor-pointer">Computers</h1>
                <div @click="changeCollapse(!collapsed)" class="pl-2 mt-4 hover:cursor-pointer"> 
                    <svg v-if="!collapsed" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="white" class="w-4 h-4">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M9 9V4.5M9 9H4.5M9 9L3.75 3.75M9 15v4.5M9 15H4.5M9 15l-5.25 5.25M15 9h4.5M15 9V4.5M15 9l5.25-5.25M15 15h4.5M15 15v4.5m0-4.5l5.25 5.25" />
                    </svg>
                    <svg v-if="collapsed" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="white" class="w-4 h-4">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 3.75v4.5m0-4.5h4.5m-4.5 0L9 9M3.75 20.25v-4.5m0 4.5h4.5m-4.5 0L9 15M20.25 3.75h-4.5m4.5 0v4.5m0-4.5L15 9m5.25 11.25h-4.5m4.5 0v-4.5m0 4.5L15 15" />
                    </svg>
                </div>
            </div>
            
            <div class="min-w-full grid xl:grid-cols-2 grid-cols-1 sm:ml-3 pt-3">
                <FarmComponent v-for="system in sortSystemsById" :key="system.SystemID"
                :system="system"
                ref="systems"/>
            </div>
        </div>
        <div class="w-full sm:w-1/4 sm:mr-3 sm:float-right mt-3 cursor-default select-none">
            <div class="flex justify-between mb-3">
                <div class="flex ml-5">
                    <svg @click="changePage(-1)" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="white" class="w-6 h-6 mt-auto cursor-pointer">
                        <path fill-rule="evenodd" d="M7.72 12.53a.75.75 0 010-1.06l7.5-7.5a.75.75 0 111.06 1.06L9.31 12l6.97 6.97a.75.75 0 11-1.06 1.06l-7.5-7.5z" clip-rule="evenodd" />
                    </svg>
                </div>
                <div @click="changePage(0)" class="flex cursor-pointer">
                    <h1 class="text-center text-slate-200 text-4xl">Logs</h1>
                    <h1 class="text-center text-slate-200 text-xl ml-3 self-center">{{currentPageDisplay}}/{{amountOfPages}}</h1>
                </div>
                <div class="flex mr-5">
                    <svg @click="changePage(1)" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="white" class="w-6 h-6 mt-auto cursor-pointer">
                        <path fill-rule="evenodd" d="M16.28 11.47a.75.75 0 010 1.06l-7.5 7.5a.75.75 0 01-1.06-1.06L14.69 12 7.72 5.03a.75.75 0 011.06-1.06l7.5 7.5z" clip-rule="evenodd" />
                    </svg>
                </div>
                

            </div>
            <div class="flex justify-between text-slate-200 text-lg bg-darkerBlue m-auto pb-2 border-4 rounded-br-none rounded-bl-none border-darkerBlue rounded">
                <div class="pl-3"> 
                    <input class="scale-150" type="checkbox" id="checkboxDebug" v-model="debugChecked" />
                    <label class="ml-2" for="checkboxDebug">Debug</label>
                </div>
                <div>
                    <input class="scale-150" type="checkbox" id="checkboxWarning" v-model="warningChecked" />
                    <label class="ml-2" for="checkboxWarning">Warning</label>
                </div>
                <div> 
                    <input class="scale-150" type="checkbox" id="checkboxError" v-model="errorChecked" />
                    <label class="ml-2" for="checkboxError">Error</label>
                </div>
                <div class="pr-3"> 
                    <input class="scale-150" type="checkbox" id="checkboxInfo" v-model="infoChecked" />
                    <label class="ml-2" for="checkboxInfo">Info</label>
                </div>
            </div>
            <LogComponent v-for="log in sortLogsByDate" :key="log.computerId"
                          :log="log"
                          ref="logs"/>
        </div>
    </div>
    
</template>

<script lang="ts">
    import type {Log, Computer, System} from '../interfaces';
    import {getAllLogsByTypeAndPage, getAllLogsByTypeComputerAndPage, getAmountOfLogsByComputerAndTypes, getAmountOfLogsByTypes} from '../services/api/LogApi';
    import {getAllComputers, getComputerById} from '../services/api/ComputerApi';
    import {getAllFarms, getFarmById} from '../services/api/SystemsApi';
    import {getLastLocation} from '../services/api/LocationApi';

    import { defineComponent, ref } from 'vue';
    import LogComponent from '../components/LogComponent.vue';
    import FarmComponent from '../components/FarmComponent.vue';
    // eslint-disable-next-line vue/prefer-import-from-vue
    import { toNumber } from '@vue/shared';

    export default defineComponent({
    data() {
        return {
            logs: ref([] as Log[]),
            computers: ref([] as Computer[]),
            systems: ref([] as System[]),
            currentPage: ref(0),
            pageSize: 15,
            loadingLogs: false,
            amountOfLogs: 0,
            debugChecked: false,
            warningChecked: true,
            errorChecked: true,
            infoChecked: true,
            collapsed: true,
        };
    },
    computed: {
        sortLogsByDate(){
            const sortedLogs = this.logs.slice().sort((a, b) => new Date(b.creationDateTime).getTime() - new Date(a.creationDateTime).getTime())
            return sortedLogs
        },
        sortComputersById(){
            const sortedComputers = this.computers.slice().sort((a, b) => toNumber(a.computerId) - toNumber(b.computerId))
            return sortedComputers
        },
        sortSystemsById(){
            const sortedSystems = this.systems.slice().sort((a, b) => toNumber(a.SystemID) - toNumber(b.SystemID))
            return sortedSystems
        },
        amountOfPages() {
            return Math.ceil(this.amountOfLogs/this.pageSize)
        },
        currentPageDisplay() {
            if (this.amountOfLogs == 0) {
                return this.currentPage
            } else {
                return this.currentPage + 1
            }
        }
    },
    watch: {
        logs(){
            this.$refs.logs
        },
        async debugChecked(){
            this.loadingLogs = true
            this.logs.length = 0
            await this.getLogsByPage()
        },
        async warningChecked(){

            this.loadingLogs = true
            this.logs.length = 0
            await this.getLogsByPage()
        },
        async errorChecked(){
            this.loadingLogs = true
            this.logs.length = 0
            await this.getLogsByPage()
        },
        async infoChecked(){
            this.loadingLogs = true
            this.logs.length = 0
            await this.getLogsByPage()
        }
    },

    methods: {
        async saveLog(log: Log) {
            if (this.currentPage === 0){
                this.loadingLogs = true
                this.logs.length = 0
                await this.getLogsByPage()
                
            }
        },
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
        async changePage(direction:number){
            if (direction === 1 && this.currentPage < this.amountOfPages - 1 && !this.loadingLogs){
                this.loadingLogs = true
                this.currentPage++
                this.logs.length = 0
                await this.getLogsByPage()
            }
            if (direction === -1 && this.currentPage != 0 && !this.loadingLogs){
                this.loadingLogs = true;
                --this.currentPage
                this.logs.length = 0
                await this.getLogsByPage()
            }
            if (direction === 0 && this.currentPage != 0 && !this.loadingLogs){
                this.loadingLogs = true;
                this.currentPage = 0
                this.logs.length = 0
                await this.getLogsByPage()
            }
            
        },
        async getLogsByPage(){
            //const newLogs = 
            //this.logs.length = 0
            this.amountOfLogs = await getAmountOfLogsByTypes({Debug:this.debugChecked,Warning:this.warningChecked,Error:this.errorChecked,Info:this.infoChecked})
            if (this.currentPage > this.amountOfPages - 1) {
                    this.currentPage = 0
                }
            await getAllLogsByTypeAndPage(this.currentPage, this.pageSize, {Debug:this.debugChecked,Warning:this.warningChecked,Error:this.errorChecked,Info:this.infoChecked}).then((newLogs) => {
                // for (let index = 0; index < this.pageSize; index++) {
                //     this.logs[index] = newLogs[index];
                //     this.$refs.logs
                //     this.loadingLogs = false
                // }
                this.logs = newLogs
                this.loadingLogs = false
            })
            this.$refs.logs
        },
        changeCollapse(collapse:boolean) {
            this.collapsed = collapse
            /*@ts-ignore*/
            this.$emitter.emit("collapse", collapse)
        },
        
        
        
    },
    async mounted(){
        this.$refs.logs
        this.$refs.computers
        this.$refs.farms

        this.logs = await getAllLogsByTypeAndPage(0, this.pageSize, {Debug:this.debugChecked,Warning:this.warningChecked,Error:this.errorChecked,Info:this.infoChecked})
        this.computers = await getAllComputers()
        this.amountOfLogs = await getAmountOfLogsByTypes({Debug:this.debugChecked,Warning:this.warningChecked,Error:this.errorChecked,Info:this.infoChecked})
        
        this.systems = await getAllFarms()
        this.systems.push({
            SystemID: 0,
            Name: "No System",
            Output: "None",
            Description: "Turtles without a system",
        })
        /*@ts-ignore*/
        this.$emitter.on("new-message", (msgData:any) => {
            const message = msgData
            var log = {} as Log;
            log = message;
            this.saveLog(log);
            
        });
        /*@ts-ignore*/
        this.$emitter.on("new-computer", (msgData:any) => {
            const message = msgData
            var computer = {} as Computer;
            computer = message;
            this.saveComputer(computer);
            
        });

    },
    created() {
        
    },
    components: { LogComponent, FarmComponent }
})
</script>

<style scoped>
    
</style>
