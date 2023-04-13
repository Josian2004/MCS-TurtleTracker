// import app from "../main"

// const $emitter = app.config.globalProperties.$emitter

// const WsMessages = new WebSocket(import.meta.env.VITE_APP_ROOT_WS + "/ws/app/messages");

// // export function openMessageWS(){
// //     WsMessages = new WebSocket(import.meta.env.VITE_APP_ROOT_WS + "/ws/app/messages");
// // }
// console.log("Messages Websocket opened")

// WsMessages.onmessage = (event) => {
//     const message = JSON.parse(event.data)
//     $emitter.emit('new-message', message)
    
// };

import app from "../../main"

const $emitter = app.config.globalProperties.$emitter

function openMessageWS() {
    console.log("[Message] Opening Websocket")
    const WsMessages = new WebSocket(import.meta.env.VITE_APP_ROOT_WS + "/ws/app/messages");

    WsMessages.onopen = (event) => {
        console.log("[Message] Websocket opened")
    }

    WsMessages.onmessage = (event) => {
        const message = JSON.parse(event.data)
        $emitter.emit('new-message', message)
    };

    WsMessages.onclose = (event) => {
        console.log("[Message] Websocket closed, reopening in 5 sec.....")
        setTimeout(openMessageWS, 5000)
    }

}
openMessageWS()