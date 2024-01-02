import { GameObject } from "./GameObject";
import { Wall } from "./Wall";
import { Snack } from './snack';

export class GameMap extends GameObject{
    constructor(ctx, parent, store){
        super();

        this.store = store;
        this.ctx = ctx;
        this.parent = parent; // 根据父元素动态变化的map大小
        this.L = 0; // 一个单位的长度

        this.rows = 13;
        this.cols = 14;

        this.inner_walls_count = 20;
        this.walls = [];

        this.snacks = [
            new Snack({id: 0, color: "#4b76e5", r: this.rows - 2, c: 1}, this),
            new Snack({id: 1, color: "#ea544a", r: 1, c: this.cols - 2}, this),
        ];
    }
    
    check_valid(cell){
        for (const wall of this.walls){
            if (wall.r === cell.r && wall.c === cell.c) return false;
        }

        for (const snack of this.snacks){
            let k = snack.cells.length;
            if (snack.check_tail_increasing()){
                k --;
            }
            for (let i = 0; i < k; i ++){
                if (cell.r === snack.cells[i].r && cell.c === snack.cells[i].c) return false;
            }
        }
        return true;
    }


    create_walls(){
        const g = this.store.state.battle.gamemap;

        // 画墙
        for (let r = 0; r < this.rows; r ++){
            for (let c = 0; c < this.cols; c++){
                if (g[r][c]){
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }
    }

    add_listening_events(){
        if (this.store.state.record.is_record){
            let k = 0;
            const [snack0, snack1] = this.snacks;
            const a_steps = this.store.state.record.a_steps;
            const b_steps = this.store.state.record.b_steps;
            const loser = this.store.state.record.record_loser;
            const interval_id = setInterval( ()=>{
                if (k >= a_steps.length - 1){
                    if (loser === "all" || loser === "a"){
                        snack0.status = "die";
                    }
                    if (loser === "all" || loser === "b"){
                        snack1.status = "die";
                    }
                    clearInterval(interval_id);
                } else{
                    snack0.set_direction(parseInt(a_steps[k]));
                    snack1.set_direction(parseInt(b_steps[k]));
                    k ++;
                }
            }, 300); // 每300ms, 执行一次
        } else {
            this.ctx.canvas.focus();

            this.ctx.canvas.addEventListener("keydown", e =>{
                let d = -1;
                if (e.key === 'w' || e.key === 'W') d = 0;
                else if (e.key === 'd' || e.key === 'D') d = 1;
                else if (e.key === 's' || e.key === 'S') d = 2;
                else if (e.key === 'a' || e.key === 'A') d = 3;
                if (d >= 0){
                    this.store.state.battle.socket.send(JSON.stringify({
                        event: "move",
                        direction: d,
                    }))
                }
            });
        }
    }

    start(){
        this.create_walls();
        this.add_listening_events();
    }

    update_size(){
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows)); // 墙之间存在浮点数问题,有缝
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }

    check_ready(){ // 判断两条蛇是否都准备好下一回合了
        for (const snack of this.snacks){
            if (snack.direction === -1) return false;
            if (snack.status !== "idle") return false;
        }
        return true;
    }

    next_step(){
        for (const snack of this.snacks){
            snack.next_step();
        }
    }

    update(){
        this.update_size();
        if (this.check_ready()){
            this.next_step();    
        }
        this.render();
    }

    render(){
        const color_even = "#b2d45f", color_odd = "#a9cd56";
        for (let r = 0; r < this.rows; r ++){
            for (let c = 0; c < this.cols; c ++){
                if ((r + c) % 2 === 0){
                    this.ctx.fillStyle = color_even;
                } else {
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);
            }
        }
    }
}