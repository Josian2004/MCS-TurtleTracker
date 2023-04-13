import Axios from 'axios';
import type { Types, Log } from '../../interfaces';

export async function getAllLogs():Promise<Log[]>{
    return Axios.get(import.meta.env.VITE_APP_ROOT_API + "/app/messages/all")
    .then(function (response){
        return response.data;
    })
    .catch(function (error) {
        console.error(error.statusCode + " " + error.message);
        return [] as Log[];
    })
}

export async function getLogById(id:string):Promise<Log>{
    return Axios.get(import.meta.env.VITE_APP_ROOT_API + "/app/messages/by", {params: {id: id}})
    .then(function (response){
        return response.data;
    })
    .catch(function (error) {
        console.error(error.statusCode + " " + error.message);
        return {} as Log;
    })
}

export async function getAllLogsByPage(page:number, size:number):Promise<Log[]> {
    return Axios.get(import.meta.env.VITE_APP_ROOT_API + "/app/messages/all/by-range", { params: {page: page, size: size}})
    .then(function (response){
        return response.data;
    })
    .catch(function (error){
        console.error(error.statusCode + " " + error.message);
        return [] as Log[];
    })
}

export async function getAllLogsByTypeAndPage(page:number, size:number, types:Types):Promise<Log[]> {
    const enabledTypes = [] as string[]
    if (types.Debug) {
        enabledTypes.push("Debug")
    }
    if (types.Warning) {
        enabledTypes.push("Warning")
    }
    if (types.Error) {
        enabledTypes.push("Error")
    }
    if (types.Info) {
        enabledTypes.push("Info")
    }
    return Axios.post(import.meta.env.VITE_APP_ROOT_API + "/app/messages/all/by-range/by-types",enabledTypes, {headers:{'Content-Type': 'application/json'},params:{page: page, size: size}})
    .then(function (response){
        return response.data;
    })
    .catch(function (error){
        console.error(error.statusCode + " " + error.message);
        return [] as Log[];
    })
}

export async function getAllLogsByTypeComputerAndPage(page:number, size:number, types:Types, computerId:number|undefined):Promise<Log[]> {
    const enabledTypes = [] as string[]
    if (types.Debug) {
        enabledTypes.push("Debug")
    }
    if (types.Warning) {
        enabledTypes.push("Warning")
    }
    if (types.Error) {
        enabledTypes.push("Error")
    }
    if (types.Info) {
        enabledTypes.push("Info")
    }
    return Axios.post(import.meta.env.VITE_APP_ROOT_API + "/app/messages/all/by-range/by-types/by-computers",enabledTypes, {headers:{'Content-Type': 'application/json'},params:{page: page, size: size, computerId:computerId}})
    .then(function (response){
        return response.data;
    })
    .catch(function (error){
        console.error(error.statusCode + " " + error.message);
        return [] as Log[];
    })
}

export async function getAmountOfLogs():Promise<number> {
    return Axios.get(import.meta.env.VITE_APP_ROOT_API + "/app/messages/all/amount")
    .then(function (response){
        return response.data;
    })
    .catch(function (error){
        console.error(error.statusCode + " " + error.message);
        return 0;
    })
}

export async function getAmountOfLogsByTypes(types:Types):Promise<number> {
    const enabledTypes = [] as string[]
    if (types.Debug) {
        enabledTypes.push("Debug")
    }
    if (types.Warning) {
        enabledTypes.push("Warning")
    }
    if (types.Error) {
        enabledTypes.push("Error")
    }
    if (types.Info) {
        enabledTypes.push("Info")
    }
    return Axios.post(import.meta.env.VITE_APP_ROOT_API + "/app/messages/all/amount/by-types",enabledTypes, {headers:{'Content-Type': 'application/json'}})
    .then(function (response){
        return response.data;
    })
    .catch(function (error){
        console.error(error.statusCode + " " + error.message);
        return 0;
    })
}

export async function getAmountOfLogsByComputerAndTypes(computerId:number|undefined,types:Types):Promise<number> {
    const enabledTypes = [] as string[]
    if (types.Debug) {
        enabledTypes.push("Debug")
    }
    if (types.Warning) {
        enabledTypes.push("Warning")
    }
    if (types.Error) {
        enabledTypes.push("Error")
    }
    if (types.Info) {
        enabledTypes.push("Info")
    }
    return Axios.post(import.meta.env.VITE_APP_ROOT_API + "/app/messages/all/amount/by-types/by-computers",enabledTypes, {headers:{'Content-Type': 'application/json'}, params:{computerId:computerId}})
    .then(function (response){
        return response.data;
    })
    .catch(function (error){
        console.error(error.statusCode + " " + error.message);
        return 0;
    })
}