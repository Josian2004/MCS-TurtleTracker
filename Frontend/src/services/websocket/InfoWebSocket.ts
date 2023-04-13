import app from "../../main"

const $emitter = app.config.globalProperties.$emitter

function openInfoWS() {
    console.log("[Info] Opening Websocket")
    const WsInfo = new WebSocket(import.meta.env.VITE_APP_ROOT_WS + "/ws/app/info");

    WsInfo.onopen = (event) => {
        console.log("[Info] Websocket opened")
    }

    WsInfo.onmessage = (event) => {
        const message = JSON.parse(event.data)
        $emitter.emit('new-computer', message)
    };

    WsInfo.onclose = (event) => {
        console.log("[Info] Websocket closed, reopening in 5 sec.....")
        setTimeout(openInfoWS, 5000)
    }

}
openInfoWS()