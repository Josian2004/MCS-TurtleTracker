import Axios from 'axios';
import type { Computer} from '../../interfaces';

export async function getAllComputers():Promise<Computer[]>{
    return Axios.get(import.meta.env.VITE_APP_ROOT_API + "/app/computers/all")
    .then(function (response){
        return response.data;
    })
    .catch(function (error){
        console.error(error.statusCode + " " + error.message);
        return [] as Computer[];
    })
}

export async function getComputerById(id:number|undefined):Promise<Computer> {
    if (id != undefined){
        return Axios.get(import.meta.env.VITE_APP_ROOT_API + "/app/computers/", { params: {id: id}})
        .then(function (response){
            return response.data;
        })
        .catch(function (error){
            console.error(error.statusCode + " " + error.message);
            return {} as Computer;
        })
    } else {
        return {} as Computer
    }
    
}