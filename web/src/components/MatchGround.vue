<template>
    <div class="matchground">
        <div class="row">
            <div class="col-4">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-4">
                <div class="user-select-bot">
                    <select v-model="select_bot" class="form-select" aria-label="Default select example">
                        <option value="-1" selected>亲自出马!</option>
                        <option v-for="bot in bots" :key="bot.id" :value="bot.id">{{bot.title}}</option>
                    </select>
                </div>
            </div>
            <div class="col-4">
                <div class="user-photo">
                    <img :src="$store.state.battle.opponent_photo" alt="">
                </div>
                <div class="user-username">
                    {{$store.state.battle.opponent_username}}
                </div>
            </div>
            <div class="col-12" style="text-align: center; margin-top: 8vh;">
                <button @click="btn_info_update" type="button" class="btn btn-info btn-lg">{{btn_info}}</button>
            </div>
        </div>
    </div>
</template>

<script>
import {ref} from "vue";
import { useStore } from "vuex";
import $ from "jquery";
import baseUrl from "@/config/config";

export default{
    setup(){
        const store = useStore();
        let btn_info = ref("开始匹配");
        let bots = ref([]);
        let select_bot = ref("-1");

        const btn_info_update = ()=>{
            if (btn_info.value === "开始匹配"){
                btn_info.value = "取消";
                store.state.battle.socket.send(JSON.stringify({
                    event: "start-matching",
                    bot_id: select_bot.value,
                }));
            } else {
                btn_info.value = "开始匹配";
                store.state.battle.socket.send(JSON.stringify({
                    event: "stop-matching",
                }));
            }
        }

        const refresh_bots = ()=>{
                $.ajax({
                url: baseUrl.remoteHttpsUrl + "/api/user/bot/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp){
                    bots.value = resp;
                }
            });
        }

        refresh_bots();

        return {
            btn_info,
            btn_info_update,
            bots,
            select_bot,
        }
    }
}

</script>

<style scoped>
.matchground{
    width: 70vw;
    height: 60vh;
    margin: 40px auto;
    background-color: rgba(230,230,250, 0.8);
}

.user-photo{
    text-align: center;
    padding-top: 8vh;
}

.user-photo > img{
    border-radius: 50%;
    width: 20vh;
}

.user-username{
    font-size: 24px;
    text-align: center;
    padding-top: 1vh;
}

/* 去掉btn的highlight */
.btn:focus { 
  outline: none;
  box-shadow: none;
}

.user-select-bot > select{
    margin: 0 auto;
    width: 80%;
    margin-top: 15vh;
}

.user-select-bot > select:focus{
    outline: none;
    box-shadow: none;
}

</style>