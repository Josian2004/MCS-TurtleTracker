import Axios from 'axios';
import type { Computer, System, Types, Location, Log, Inventory } from '../../interfaces';


export async function getInventory(id:number|undefined):Promise<Inventory> {
    if (id != undefined){
        return Axios.get(import.meta.env.VITE_APP_ROOT_API + "/app/inventory/", { params: {computerId: id}})
        .then(function (response){
            return response.data;
        })
        .catch(function (error){
            console.error(error.statusCode + " " + error.message);
            return {} as Inventory;
        })
    } else {
        return {} as Inventory
    }
    
}