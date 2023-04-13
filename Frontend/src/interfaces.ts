interface Log {
    computerId: string;
    messageType: string;
    message: string;
    creationDateTime: string;
    serviceName: string;
    metaData:object;
    id:string;
}
interface Computer {
    computerId: string;
    computerLabel: string;
    mcsSystemId: Number
    assignedMcsSystem: string;
    status: string;
    device: string;
    fuelLevel: number;
    fuelLimit: number;
    creationDateTime: string;
}
interface Location {
    computerId: number;
    x: string;
    y: string;
    z: string;
    dimension: string;
}
interface System {
    SystemID: number;
    Name: string;
    Output: string;
    Description: string;
}
interface Types {
    Debug: boolean;
    Error: boolean;
    Warning: boolean;
    Info: boolean;
}
interface Item {
    name: string;
    display_name: string;
    count: number;
    max_count: number;
    slot:number
}
interface Inventory {
    computer_id: number;
    items: Item[];
}


export type {Log, Computer, Location, System, Types, Inventory, Item}