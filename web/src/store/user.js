import $ from "jquery";
import baseUrl from "@/config/config.js"

const ModuleUser = {
    // 维护的数据
    state: {
        id: "",
        username: "",
        photo: "",
        token: "",
        is_login: false,
        pulling_info: true,
        followerCount: 0,
        refresh: "",
    },
    getters: {
    },
    // 同步调用
    mutations: {
        updateUser(state, user){
            state.username = user.username;
            state.id = user.id;
            state.photo = user.photo;
            state.is_login = user.is_login;
            state.followerCount = user.followerCount;
            state.refresh = user.refresh;
        },
        updateToken(state, token){
            state.token = token;
        },
        logout(state){
            state.username = "";
            state.id = "";
            state.photo = "";
            state.token = "";
            state.is_login = false;
            state.refresh = "";
            localStorage.clear("jwt_token");
        },
        updatePullingInfo(state, pulling_info){
            state.pulling_info = pulling_info;
        }
    },
    // 异步调用
    actions: {
        login(context, data){
            $.ajax({
                type: "post",
                url: baseUrl.remoteHttpsUrl + "/api/user/account/token/",
                data: {
                    username: data.username,
                    password: data.password,
                },
                success(resp){
                    if (resp.error_message === "success"){
                        localStorage.setItem("jwt_token", resp.token);
                        context.commit("updateToken", resp.token);
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp){
                    data.error(resp);
                }
            });
        },
        getinfo(context, data){
            $.ajax({
                url: baseUrl.remoteHttpsUrl + "/api/user/account/info/",
                headers:{
                    Authorization: "Bearer " + context.state.token,
                },
                type: "GET",
                success(resp){
                    if (resp.error_message === "success"){
                        context.commit("updateUser",{
                            ...resp,
                            is_login: true,
                        });
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp){
                    data.error(resp);
                }
            });
        }
    },
    modules: {
    }
}

export default ModuleUser;