import Axios from 'axios';
import type { Computer, System, Types, Location, Log, Inventory } from '../../interfaces';

export async function getAllFarms():Promise<System[]> {
    return Axios.get("https://systems.mcsynergy.nl/api/get-all-systems")
    .then(function (response){
        return response.data;
    })
    .catch(function (error){
        console.error(error);
        return [] as System[];
    })
}

export async function getFarmById(id:number|undefined):Promise<System> {
    return Axios.get("https://systems.mcsynergy.nl/api/get-system?SystemID="+id)
    .then(function (response){
        return response.data;
    })
    .catch(function (error){
        console.error(error);
        return {} as System;
    })
}