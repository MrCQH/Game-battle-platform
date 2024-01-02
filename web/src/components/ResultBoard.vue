<template>
    <div class="user-board">
        <div class="user-result" v-if="$store.state.battle.loser === 'a' && $store.state.battle.a_id === parseInt($store.state.user.id)">
            Lose
        </div>
        <div class="user-result" v-else-if="$store.state.battle.loser === 'b' && $store.state.battle.b_id === parseInt($store.state.user.id)">
            Lose
        </div>
        <div class="user-result" v-else-if="$store.state.battle.loser === 'all'">
            Draw
        </div>
        <div class="user-result" v-else>
            Win
        </div>
        <div class="user-btn">
            <button @click="btn_cli" type="button" class="btn btn-primary btn-sm">重开!</button>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex';

export default{
    setup(){
        const store = useStore();
        const btn_cli = ()=>{
            store.commit("updateLoser", 'none');
            store.commit("updateOpponent", {
                username: "我的对手",
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
            });
            store.commit("updateStatus", 'matching');
        }
        return {
            btn_cli,
        }
    }
}
</script>

<style scoped>
.user-board{
    width: 35vw;
    height: 25vh;
    position: absolute;
    top: 30vh;
    left: 32.5vw;
    background-color: rgba(148, 183, 212, 0.5);
}

.user-result{
    text-align: center;
    padding-top: 3vh;
    font-size: 36px;
    font-style: italic;
    font-weight: 600;
    color: white;
}

.user-btn{
    text-align: center;
    padding-top: 3vh;
}

.user-btn>button:focus{
    outline: none;
    box-shadow: none;
}
</style>