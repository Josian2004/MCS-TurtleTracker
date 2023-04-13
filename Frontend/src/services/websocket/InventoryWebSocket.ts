import app from "../../main"

const $emitter = app.config.globalProperties.$emitter

function openInventoryWS() {
    console.log("[Inventory] Opening Websocket")
    const WsInventory = new WebSocket(import.meta.env.VITE_APP_ROOT_WS + "/ws/app/inventory");

    WsInventory.onopen = (event) => {
        console.log("[Inventory] Websocket opened")
    }

    WsInventory.onmessage = (event) => {
        const message = JSON.parse(event.data)
        $emitter.emit('new-inventory', message)
    };

    WsInventory.onclose = (event) => {
        console.log("[Inventory] Websocket closed, reopening in 5 sec.....")
        setTimeout(openInventoryWS, 5000)
    }

}
openInventoryWS()