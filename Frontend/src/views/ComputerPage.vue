<template>
    <div class="sm:flex text-slate-200">
        <div class="w-full sm:w-2/3 h-full sm:float-left sm:ml-3 mt-16 bg-opacity-50 border-opacity-0 text-slate-200 mr-auto text-lg bg-darkerBlue pb-2 border-4 rounded-br-none rounded-bl-none border-darkBlue rounded">
            <div> 
                <div class="ml-2 flex justify-left content-center"> 
                    <img class="sm:w-20 w-14 h-14 sm:h-20" :src="deviceImageLink"/>
                    <h1 class="pt-1 text-3xl ml-2 sm:text-5xl mt-2">{{computer?.device}} {{computer?.computerId}} - {{computer?.computerLabel}}</h1>
                </div>
                <div class="bg-darkBlue w-full h-1 bg-opacity-50"></div>
            </div>
            <div class="grid xl:grid-cols-2 grid-cols-1 mt-3">
                <div class="w-full h-auto flex flex-col"> 
                    <div v-if="(system.SystemID != 0)" class="w-2/3 self-center"> 
                        <h2 class="sm:text-4xl text-2xl">System {{computer.mcsSystemId}} - {{system.Name}}</h2>
                        <h3 class="sm:text-3xl text-xl">Produces: {{system.Output}}</h3>
                    </div>
                    <div v-else class="w-2/3 self-center"> 
                        <h2 class="sm:text-4xl text-2xl">No System</h2>
                        <h3 class="sm:text-3xl text-xl"></h3>
                    </div>
                    <div class="bg-darkBlue w-5/6 h-1 bg-opacity-50 mt-2 mb-4 self-center"></div>
                    <div class="w-2/3 self-center"> 
                        <h2 class="sm:text-4xl text-2xl">Current Status:</h2>
                        <div class="flex"> 
                            <div :class="{orange_status: orangeStatusColor, disconnected: isDisconnected || computer?.status == 'Error'}" class="w-4 h-4 bg-green-600 rounded-full m-1 sm:m-2 sm:mt-3"> </div>
                            <h3 class="sm:text-3xl text-xl">{{computer.status}}</h3>
                        </div>
                    </div>
                    <div class="bg-darkBlue w-5/6 h-1 bg-opacity-50 mt-2 mb-4 self-center"></div>
                    <div class="w-2/3 self-center"> 
                        <h2 class="sm:text-4xl text-2xl">Last Update:</h2>
                        <div class="flex"> 
                            <div :class="{disconnected: isDisconnected}" class="w-4 h-4 bg-green-600 rounded-full m-1 sm:m-2 sm:mt-3"> </div>
                            <h3 class="sm:text-3xl text-xl">{{formatedDateTime}}</h3>
                        </div>
                    </div>
                    <div class="bg-darkBlue w-5/6 h-1 bg-opacity-50 mt-2 mb-4 self-center"></div>
                    <div v-if="isTurtle" class="w-2/3 self-center mt-5"> 
                        <InventoryComponent :computer_id="computerIdAsNumber" ref="inventory"/>
                    </div>

                </div>

                <div v-if="isTurtle" class="w-full h-auto flex flex-col"> 
                    <div class="self-center"> 
                        <iframe id="dynmap" class="h-96 w-96" :src="dynmapLink"/>
                        <h2 class="text-2xl w-full text-center">Live location: x:{{/*@ts-ignore*/
                            this.locationObject.x}} / y:{{/*@ts-ignore*/
                                this.locationObject.y}} / z:{{/*@ts-ignore*/
                                    this.locationObject.z}}  ({{/*@ts-ignore*/
                                        this.locationObject.dimension}})</h2>
                    </div>
                    <div class="flex flex-col mt-5 sm:mt-32 mb-5">
                        <h2 class="self-center text-2xl">Current fuel status</h2>
                        <div class="w-2/3 bg-gray-200  h-7 dark:bg-gray-700 self-center">
                            <div :class="{ 
                                /*@ts-ignore*/
                                low_fuel: computer?.fuelLevel < computer?.fuelLimit*0.5, no_fuel: computer?.fuelLevel == 0}" class="bg-green-700 h-full " :style="{'width': percentFuel}">
                                <h4 class="ml-5">{{computer?.fuelLevel}}/{{computer?.fuelLimit}}</h4>
                            </div>
                        </div>
                    </div>
                </div>
            
            </div>
        </div>

        <div class="w-full sm:w-1/4 sm:mr-3 sm:float-right mt-3 cursor-default select-none h-auto">
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
    import type {Log, Computer, System, Location, Inventory} from '../interfaces';
    import {getAllLogsByTypeAndPage, getAllLogsByTypeComputerAndPage, getAmountOfLogsByComputerAndTypes, getAmountOfLogsByTypes} from '../services/api/LogApi';
    import {getComputerById} from '../services/api/ComputerApi';
    import {getFarmById} from '../services/api/SystemsApi';
    import {getLastLocation} from '../services/api/LocationApi';

    import LogComponent from '../components/LogComponent.vue';
    import InventoryComponent from '../components/InventoryComponent.vue'

    import { defineComponent, ref } from 'vue';
    // eslint-disable-next-line vue/prefer-import-from-vue
    import { toNumber } from '@vue/shared';

    export default defineComponent({
    data() {
        return {
            logs: ref([] as Log[]),
            computer: ref({} as Computer),
            system: ref({} as System),
            inventory: ref({} as Inventory),
            currentPage: ref(0),
            pageSize: 8,
            loadingLogs: false,
            amountOfLogs: 0,
            debugChecked: false,
            warningChecked: true,
            errorChecked: true,
            infoChecked: true,
            isDisconnected:false,
            timeout:0,
            locationObject: {} as Location,
            dynmapLink: "",
        };
    },
    props: {
            id: {
                type: Number,
                required: true,
            }
        },

    computed: {
        computerIdAsNumber() {
            return toNumber(this.id)
        },
        sortLogsByDate(){
            const sortedLogs = this.logs.slice().sort((a, b) => new Date(b.creationDateTime).getTime() - new Date(a.creationDateTime).getTime())
            return sortedLogs
        },
        amountOfPages() {
            return Math.ceil(this.amountOfLogs/this.pageSize)
        },
        percentFuel(){
            return (this.computer.fuelLevel/this.computer.fuelLimit) * 100 + "%"
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
        orangeStatusColor(){
            return this.computer?.status == "Need Player" || this.computer?.status == "Manually Terminated"
        },
        formatedDateTime() {
            //@ts-ignore
            const dateTime = new Date(this.computer?.creationDateTime)
            return dateTime.toDateString() + " - " + this.addZero(dateTime.getHours()) + ":" + this.addZero(dateTime.getMinutes()) + ":" + this.addZero(dateTime.getSeconds())
        },
        isTurtle(){
            if (this.computer?.device == "Normal Turtle" || this.computer?.device == "Advanced Turtle"){
                return true
            } else  {
                return false
            }
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
        },
        computer() {
                this.isDisconnected = false
                if(this.timeout) clearTimeout(this.timeout)
                /*@ts-ignore*/
                this.timeout = setTimeout(() => {this.isDisconnected = true}, 10 * 1000)
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
            if (toNumber(computer.computerId) == this.id) {
                this.computer = computer;
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
            this.amountOfLogs = await getAmountOfLogsByComputerAndTypes(toNumber(this.computer.computerId), {Debug:this.debugChecked,Warning:this.warningChecked,Error:this.errorChecked,Info:this.infoChecked})
            if (this.currentPage > this.amountOfPages - 1) {
                    this.currentPage = 0
                }
            await getAllLogsByTypeComputerAndPage(this.currentPage, this.pageSize, {Debug:this.debugChecked,Warning:this.warningChecked,Error:this.errorChecked,Info:this.infoChecked},toNumber(this.computer.computerId)).then((newLogs) => {
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
        addZero(i:any) {
                if (i < 10) {i = "0" + i}
                return i;
                },

        generateDynmapLink(){
            let dimension
            /*@ts-ignore*/
            if (this.locationObject.dimension == "Overworld") {
                dimension = "world"
            }
            /*@ts-ignore*/
            else if (this.locationObject.dimension == "Nether") {
                dimension = "DIM-1"
            }
            /*@ts-ignore*/
            else if (this.locationObject.dimension == "End") {
                dimension = "DIM1"
            } else {
                return ""
            }
            /*@ts-ignore*/
            return "https://storage.mcsynergy.nl/dynmap/?worldname="+dimension+"&mapname=flat&zoom=5&x="+this.locationObject.x+"&y="+this.locationObject.y+"&z="+this.locationObject.z
        
        }
        
        
    },
    async mounted(){
        this.$refs.logs
        this.$refs.computers
        this.$refs.farms
        this.computer = await getComputerById(this.id)
        this.logs = await getAllLogsByTypeComputerAndPage(this.currentPage, this.pageSize, {Debug:this.debugChecked,Warning:this.warningChecked,Error:this.errorChecked,Info:this.infoChecked},toNumber(this.computer.computerId))
        
        this.amountOfLogs = await getAmountOfLogsByComputerAndTypes(toNumber(this.computer.computerId), {Debug:this.debugChecked,Warning:this.warningChecked,Error:this.errorChecked,Info:this.infoChecked})
        this.system = await getFarmById(toNumber(this.computer?.mcsSystemId))

        this.isDisconnected = false
        this.timeout = window.setTimeout(() => {this.isDisconnected = true}, 10 * 1000)
        if (this.isTurtle) {
            this.locationObject = await getLastLocation(toNumber(this.computer?.computerId))

        }
        this.dynmapLink = this.generateDynmapLink()


        //@ts-ignore
        this.$emitter.on("new-location", (msgData:any) => {
            if (msgData.computerId == this.computer?.computerId){
                this.locationObject = msgData
            }

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
    components: { LogComponent, InventoryComponent }
})
</script>

<style scoped>
.low_fuel{
    @apply bg-orange-500 !important;
}
.no_fuel{
    @apply text-red-600 !important;
}
.disconnected {
    /* color: red !important; */
    @apply bg-red-600 !important;
}
.orange_status{
    @apply bg-orange-500 !important;
}
</style>
