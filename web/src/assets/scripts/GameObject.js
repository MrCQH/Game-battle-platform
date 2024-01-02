const GAME_OBJECTS = [];


export class GameObject{
    constructor(){
        GAME_OBJECTS.push(this);
        this.timedelta = 0;
        this.call_starttime = false;
    }

    start(){

    }

    update(){

    }

    on_destroy(){

    }

    destroy(){
        this.on_destroy();
        for (let i in GAME_OBJECTS){
            const object = GAME_OBJECTS[i];
            if (object === this){
                GAME_OBJECTS.splice(i, 1);
                break;
            }
        }
    }

}

let last_timestamp;
const step = (timestamp)=>{
    for (let object of GAME_OBJECTS){
        if (!object.call_starttime){
            object.call_starttime = true;
            object.start();
        } else {
            object.timedelta = timestamp - last_timestamp;
            object.update();
        }
    }
    last_timestamp = timestamp;
    requestAnimationFrame(step);
}

requestAnimationFrame(step);
