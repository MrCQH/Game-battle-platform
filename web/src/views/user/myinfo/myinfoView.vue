<template>
    <ContentView>
        <div class="row">
            <div class="col-3">
                <div class="card">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" style="width: 100%">
                    </div>
                </div>
            </div>
            <div class="col-9">
                <div class="card">
                    <div class="card-body">
                        <div class="bot-info">
                            <span style="font-size: 130%; margin-left: 0.5rem;">我的bot</span>

                            <!-- Button trigger modal -->
                            <button type="button" class="float-end btn btn-success" style="margin-top: -0.3rem" data-bs-toggle="modal" data-bs-target="#addbot">
                                创建bot
                            </button>

                            <!-- Modal -->
                            <div class="modal fade" id="addbot" tabindex="-1" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">bot信息</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="mb-3">
                                                <label for="title" class="form-label">标题</label>
                                                <input v-model="botadd.title" type="text" class="form-control" placeholder="请输入标题">
                                            </div>
                                        </div>
                                        <div class="modal-body">
                                            <div class="mb-3">
                                                <label for="description" class="form-label">简介</label>
                                                <textarea v-model="botadd.description" class="form-control"  rows="3" placeholder="请输入简介"></textarea>
                                            </div>
                                        </div>
                                        <div class="modal-body">
                                            <div class="mb-3">
                                                <label class="form-label">代码</label>
                                                <VAceEditor
                                                    v-model:value="botadd.content"
                                                    @init="editorInit"
                                                    lang="c_cpp"
                                                    theme="textmate"
                                                    style="height: 300px" />
                                            </div>
                                        </div>
                                        <div class="modal-footer">  
                                            <div class="error-message" >{{botadd.error_message}}</div>
                                            <button @click="add_bot" type="button" class="btn btn-primary">提交</button>
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">
                                        <div style="margin-left:0.5rem">bot名称</div>
                                    </th>
                                    <th scope="col">
                                        <div style="margin-left:3rem">创建时间</div>
                                    </th>
                                    <th scope="col">
                                        <div style="margin-left:3rem">选 项</div>
                                    </th>
                                </tr>
                            </thead>
                            <tbody v-for="bot_info in bots_info" :key="bot_info.id">
                                <tr>
                                    <td>{{bot_info.title}}</td>
                                    <td>{{bot_info.createtime}}</td>
                                    <td>
                                        <div class="col-md-10" style="display: none">
                                            <input type="text" class="form-control" id="name"  value="">
                                        </div>

                                        <!-- Button trigger modal -->
                                        <button type="button" class="btn btn-warning" style="margin-right:1rem" data-bs-toggle="modal" :data-bs-target="'#updata-bot-modal-' + bot_info.id">
                                            修改
                                        </button>

                                        <!-- Modal -->
                                        <div class="modal fade" :id="'updata-bot-modal-' + bot_info.id" tabindex="-1" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">bot信息</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="title" class="form-label">标题</label>
                                                            <input v-model="bot_info.title" type="text" class="form-control" id="title" placeholder="请输入标题">
                                                        </div>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="description" class="form-label">简介</label>
                                                            <textarea v-model="bot_info.description" class="form-control" id="description" rows="3" placeholder="请输入简介"></textarea>
                                                        </div>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label class="form-label">代码</label>
                                                            <VAceEditor
                                                                v-model:value="bot_info.content"
                                                                @init="editorInit"
                                                                lang="c_cpp"
                                                                theme="textmate"
                                                                style="height: 300px" />
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">  
                                                        <div class="error-message" >{{botadd.error_message}}</div>
                                                        <button @click="updata_bot(bot_info)" type="button" class="btn btn-primary">修改</button>
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- Button trigger modal -->
                                        <button type="button" class="btn btn-danger" data-bs-toggle="modal" :data-bs-target="'#remove-bot-modal-' + bot_info.id">
                                        删除
                                        </button>

                                        <!-- Modal -->
                                        <div class="modal fade" :id="'remove-bot-modal-' + bot_info.id" aria-labelledby="movetitle" aria-hidden="true" style="margin-top:15rem">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header remove-border">
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                <div class="modal-body" style="display:flex;justify-content: center;align-items: center;">
                                                    <h5 class="modal-title" id="movetitle" style="font-weight: bold;font-size: 24px;">你确定要删除该Bot</h5>
                                                </div>
                                                <div class="modal-footer remove-border">
                                                    <button @click="remove_bot(bot_info)" type="button" class="btn btn-danger">删除</button>
                                                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal">关闭</button>
                                                </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </ContentView>
</template>

<script>
import ContentView from "@/components/ContentView.vue";
import $ from "jquery";
import { useStore } from "vuex";
import {reactive, ref} from "vue";
import { Modal } from "bootstrap/dist/js/bootstrap";
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';
import baseUrl from "@/config/config";


export default {
    components: {
        VAceEditor,
        ContentView,
    },

    setup(){
        ace.config.set(
            "basePath", 
            "https://fastly.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/")
        const store = useStore();
        let bots_info = ref([]);
        let botadd = reactive({
            title: "",
            description: "",
            content: "",
            error_message: "",
        });

        const refresh_bots = ()=>{
            $.ajax({
                url: baseUrl.remoteHttpsUrl + "/api/user/bot/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp){
                    bots_info.value = resp;
                }
            });
        }

        refresh_bots();
        
        const add_bot = ()=>{
            botadd.error_message = "";
            $.ajax({
                url: baseUrl.remoteHttpsUrl + "/api/user/bot/add/",
                type: "POST",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    title: botadd.title,
                    description: botadd.description,
                    content: botadd.content,
                },
                success(resp){
                    if (resp.error_message === "success"){
                        botadd.title = "";
                        botadd.description = "";
                        botadd.content = "";
                        refresh_bots();
                        Modal.getInstance("#addbot").hide();
                    } else {
                        botadd.error_message = resp.error_message;
                    }
                }
            });
        }


        const remove_bot = (bot)=>{
            $.ajax({
                url: baseUrl.remoteHttpsUrl + "/api/user/bot/remove/",
                type: "POST",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    bot_id: bot.id,
                },
                success(resp){
                    if (resp.error_message === "success"){
                        refresh_bots();
                        Modal.getInstance("#remove-bot-modal-" + bot.id).hide();
                    }
                }
            });
        }

        const updata_bot = (bot)=>{
            botadd.error_message = "";
            $.ajax({
                url: baseUrl.remoteHttpsUrl + "/api/user/bot/modify/",
                type: "POST",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    bot_id: bot.id,
                    title: bot.title,
                    description: bot.description,
                    content: bot.content,
                },
                success(resp){
                    if (resp.error_message === "success"){
                        refresh_bots();
                        Modal.getInstance("#updata-bot-modal-" + bot.id).hide();
                    } else {
                        botadd.error_message = resp.error_message;
                    }
                }
            });
        }

        return {
            bots_info,
            botadd,
            add_bot,
            remove_bot,
            updata_bot,
        }
    }
}
</script>

<style scoped>
.error-message{
    color: red;
    margin-right: 2rem;
}
#movetitle{
    color: red;
}
.remove-border{
    border-bottom: 0;
    border-top: 0;
}
</style>