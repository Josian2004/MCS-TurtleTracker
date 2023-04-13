<template>
    
    <div class="flex">
        <div v-if="inventory.items" class="grid grid-cols-4 gap-0 mb-5">
            <div v-for="item in inventory.items" :key="item.slot" ref="inventory" class="aspect-square flex justify-center content-center relative text-center bg-inventoryBG border-4 border-white border-t-inventoryBorderDark border-l-inventoryBorderDark"> 
                <img v-if="item.name != ''" data-tooltip-target="tooltip-default" class="h-full w-full self-center" :src="(('/items/'+ item.name.split(':').pop()) + '.png')"/>
                <div v-else class="sm:h-[60px] h-[45px] sm:w-[60px] w-[45px]"></div>
                <h6 v-if="(item.name != '' && item.count > 1)" class="absolute bottom-0 right-1 p-0 m-0 font-black">{{item.count}}</h6>
            </div>
        </div>
        <div v-else class="grid grid-cols-4 gap-0 ] mb-5">
            <div v-for="index in 16" :key="index" ref="inventory" class="aspect-square flex justify-center content-center relative text-center bg-inventoryBG border-4 border-white border-t-inventoryBorderDark border-l-inventoryBorderDark">
                <div class="sm:w-[60px] w-[45px] sm:h-[60px] h-[45px]"></div>
            </div>
        </div>
        <svg @click="refresh" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 -translate-y-8 m-2 cursor-pointer">
            <path stroke-linecap="round" stroke-linejoin="round" d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0l3.181 3.183a8.25 8.25 0 0013.803-3.7M4.031 9.865a8.25 8.25 0 0113.803-3.7l3.181 3.182m0-4.991v4.99" />
        </svg>
    </div>
    
    
</template>

<script lang="ts">

import type { Inventory, Item } from '../interfaces';
import { defineComponent, ref } from 'vue'
import type { PropType } from 'vue'
import { getInventory } from '../services/api/InventoryApi';
import { toNumber } from '@vue/shared';
    

    export default defineComponent({
        data() {
            return{
                inventory: ref({} as Inventory)
            };
        },

        props: {
            computer_id: {
                type: Number,
                required: true,
            }
        },
            

        async mounted() {
           

           /*@ts-ignore*/
            this.$emitter.on("new-inventory", (msgData:any) => {
                const message = msgData
                var inventory = {} as Inventory;
                inventory = message;
                this.saveInventory(inventory);
            });
            await getInventory(this.computer_id)
        },

        created(){
        },


        watch: {
           
        },


        
        computed: {
            
        
        },

        methods: {
            saveInventory(inventory: Inventory) {
                let tempItemList = new Array(16) as Item[]
                if (toNumber(inventory.computer_id) == this.computer_id) {
                    for (let itemI = 0; itemI < inventory.items.length; itemI++) {
                        tempItemList[inventory.items[itemI].slot - 1] = inventory.items[itemI]
                    }
                    inventory.items = tempItemList
                    for (let index = 0; index < inventory.items.length; index++) {
                        if (inventory.items[index] == null || inventory.items[index] == undefined){
                            inventory.items[index] = {name:"", display_name:"",count:0,max_count:0,slot:index+1}
                        }
                    }
                    this.inventory = inventory;
                    this.$refs.inventory
                }
            },
            async refresh() {
                await getInventory(this.computer_id)
            }
        }
    })
</script>

<style scoped>
    
</style>