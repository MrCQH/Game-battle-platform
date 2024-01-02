<template>
    <ContentView>
        <table class="table table-striped table-hover" style="text-align:center;">
            <thead>
                <tr>
                    <th scope="col">
                        <div style="margin-left:0.5rem">玩家</div>
                    </th>
                    <th scope="col">
                        <div style="margin-left:3rem">天梯分</div>
                    </th>
                </tr>
            </thead>
            <tbody v-for="user in users" :key="user.id">
                <tr>
                    <td>
                        <img :src="user.photo" alt="" class="record-user-photo">
                        &nbsp;
                        <span class="record-username">{{user.username}}</span>
                    </td>
                    <td>{{user.rating}}</td>
                </tr>
            </tbody>
        </table>
        <nav aria-label="..." style="float:right;">
            <ul class="pagination">
                <li class="page-item">
                    <a class="page-link" href="#" @click="click_page(-2)">上一页</a>
                </li>
                <li :class="'page-item ' + page.is_active" v-for="page in pages" :key="page.number" @click="click_page(page.number)">
                    <a class="page-link" href="#">{{page.number}}</a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="#" @click="click_page(-1)">下一页</a>
                </li>
            </ul>
        </nav>
    </ContentView>
</template>

<script>
import ContentView from '@/components/ContentView.vue';
import { ref } from 'vue';
import $ from 'jquery';
import { useStore } from "vuex";
import baseUrl from "@/config/config";

export default{
    name: "RecordListView",
    components: { ContentView },
    setup(){
        const store = useStore();
        let users = ref([]);
        let current_page = 1;
        let users_count = 0;
        let pages = ref([]);

        let click_page = page =>{
            if (page === -2) page = current_page - 1;
            else if (page === -1) page = current_page + 1;
            let max_page = parseInt(Math.ceil(users_count / 10));

            if (page >= 1 && page <= max_page){
                pull_page(page);
            }
        }

        const update_pages = () =>{
            let max_page = parseInt(Math.ceil(users_count / 10));
            let new_pages = [];
            for (let i = current_page - 2; i <= current_page + 2; i ++){
                if (i >= 1 && i <= max_page){
                    new_pages.push({
                        number: i,
                        is_active: current_page === i ? "active" : "",
                    });
                }
            }
            pages.value = new_pages;
        }

        const pull_page = page =>{
            current_page = page;
            $.ajax({
                url: baseUrl.remoteHttpsUrl + "/api/rank/list/",
                type: "get",
                data:{
                    page,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp){
                    users.value = resp.users;
                    users_count = resp.users_count;
                    update_pages();
                },
                error(resp){
                    console.log(resp);
                }
            });
        };

        pull_page(current_page);
        
        return {
            users,
            pages,
            click_page,
        }
    }
}
</script>

<style scoped>
.record-user-photo{
    border-radius: 50%;
    width: 4vh;
}

button:focus{
    outline: none;
    box-shadow: none;
}

a:focus{
    outline: none;
    box-shadow: none;
}

</style>