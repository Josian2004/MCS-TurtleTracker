<template>
    <div>        
        <div class="w-4/5 mt-3 mb-3 cursor-default select-none m-auto">
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
                    <input class="" type="checkbox" id="checkboxDebug" v-model="debugChecked" />
                    <label class="ml-2" for="checkboxDebug">Debug</label>
                </div>
                <div>
                    <input class="" type="checkbox" id="checkboxWarning" v-model="warningChecked" />
                    <label class="ml-2" for="checkboxWarning">Warning</label>
                </div>
                <div> 
                    <input class="" type="checkbox" id="checkboxError" v-model="errorChecked" />
                    <label class="ml-2" for="checkboxError">Error</label>
                </div>
                <div class="pr-3"> 
                    <input class="" type="checkbox" id="checkboxInfo" v-model="infoChecked" />
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
    import {getAllFarms, getFarmById} from '../services/api/SystemsApi';

    import { defineComponent, ref } from 'vue';
    import LogComponent from '../components/LogComponent.vue';
    // eslint-disable-next-line vue/prefer-import-from-vue
    import { toNumber } from '@vue/shared';

    export default defineComponent({
    data() {
        return {
            logs: ref([] as Log[]),
            selectedLog: {} as Log,
            computers: ref([] as Computer[]),
            systems: ref([] as System[]),
            currentPage: ref(0),
            pageSize: 50,
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
    
        
        
    },
    async mounted(){
        this.$refs.logs

        this.logs = await getAllLogsByTypeAndPage(0, this.pageSize, {Debug:this.debugChecked,Warning:this.warningChecked,Error:this.errorChecked,Info:this.infoChecked})
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
        

    },
    created() {
        
    },
    components: { LogComponent }
})
</script>

<style scoped>
    
</style>
