import Axios from 'axios';
import type { Computer, System, Types, Location, Log, Inventory } from '../../interfaces';

export async function getLastLocation(computerId:number):Promise<Location> {
    return Axios.get(import.meta.env.VITE_APP_ROOT_API + "/app/locations/last", { params: {computerId: computerId}})
    .then(function (response){
        return response.data;
    })
    .catch(function (error){
        console.error(error.statusCode + " " + error.message);
        return {} as Location;
    })
}