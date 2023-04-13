// import app from "../main"
// const $emitter = app.config.globalProperties.$emitter
// const WsLocations = new WebSocket(import.meta.env.VITE_APP_ROOT_WS + "/ws/app/locations");

// // export function openLocationWS(){
// //     WsLocations = new WebSocket(import.meta.env.VITE_APP_ROOT_WS + "/ws/app/locations");
// // }

// console.log("Location Websocket opened")

// WsLocations.onmessage = (event) => {
//     const message = JSON.parse(event.data)
//     $emitter.emit('new-location', message)
// };




import app from "../../main"

const $emitter = app.config.globalProperties.$emitter

function openLocationWS() {
    console.log("[Location] Opening Websocket")
    const WsLocations = new WebSocket(import.meta.env.VITE_APP_ROOT_WS + "/ws/app/locations");

    WsLocations.onopen = (event) => {
        console.log("[Location] Websocket opened")
    }

    WsLocations.onmessage = (event) => {
        const message = JSON.parse(event.data)
            $emitter.emit('new-location', message)
    };

    WsLocations.onclose = (event) => {
        console.log("[Location] Websocket closed, reopening in 5 sec.....")
        setTimeout(openLocationWS, 5000)
    }

}
openLocationWS()