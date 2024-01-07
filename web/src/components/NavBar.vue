<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <router-link class="navbar-brand" :to="{name: 'home'}">游戏对战平台</router-link>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <router-link :class="route_name == 'battle' ? 'nav-link active' : 'nav-link'" aria-current="page" :to="{name: 'battle'}">对战</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :class="route_name == 'record_list' ? 'nav-link active' : 'nav-link'" :to="{name: 'record_list'}">对局列表</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :class="route_name == 'rank_list'? 'nav-link active' : 'nav-link'" :to="{name: 'rank_list'}">排行榜</router-link>
                    </li>
                  <li class="nav-item">
                    <router-link :class="route_name == 'my_spaces'? 'nav-link active' : 'nav-link'" :to="{name: 'my_spaces'}">我的空间</router-link>
                  </li>
<!--                  <li class="nav-item">-->
<!--                    <router-link :class="route_name == 'lottery'? 'nav-link active' : 'nav-link'" :to="{name: 'lottery'}">幸运抽奖</router-link>-->
<!--                  </li>-->
                </ul>
                <ul v-if="$store.state.user.is_login" class="navbar-nav">
                    <ul class="navbar-nav">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                {{$store.state.user.username}}
                            </a>
                            <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
                                <li><router-link class="dropdown-item" :to="{name: 'myinfo'}">我的信息</router-link></li>
                                <li><router-link class="dropdown-item" :to="{name: 'myAwards'}">我的奖励</router-link></li>
                                <li><router-link @click="logout" class="dropdown-item" :to="{name: 'user_account_login'}">退出</router-link></li>
                            </ul>
                        </li>
                    </ul>
                </ul>
                <ul v-else-if="!$store.state.user.pulling_info" class="navbar-nav">
                    <ul class="navbar-nav">
                        <li class="nav-item ">
                            <router-link class="nav-link" :to="{name: 'user_account_login'}" role="button">
                                登录
                            </router-link>
                        </li>
                        <li class="nav-item">
                            <router-link class="nav-link" :to="{name: 'user_account_register'}" role="button">
                                注册
                            </router-link>
                        </li>
                    </ul>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script>
import { useRoute } from 'vue-router';
import { computed } from 'vue';
import { useStore } from 'vuex';

export default{
    name: 'NavBar',
    setup(){
        const route = useRoute();
        const store = useStore();

        let route_name = computed(()=> route.name);
        const logout = ()=>{
            store.commit("logout");
        }

        return {
            route_name,
            logout,
        }
    }
}
</script>

<style scoped>
</style>