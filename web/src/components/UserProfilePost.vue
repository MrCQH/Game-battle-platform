<template>
    <div class="card">
        <div class="card-body">
            <div v-for="post in posts.posts" :key="post.id">
                <div class="card single-card">
                    <div class="card-body">
                        {{ post.content }}
                        <button @click="delete_a_post(post.id)" v-if="is_me" class="btn btn-danger btn-sm">删除</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import $ from 'jquery';
import baseUrl from "@/config/config";

export default{
    name: 'UserProfilePost',
    props: {
        posts: {
            type: Object,
            required: true,
        },
        user: {
            type: Object,
            required: true,
        },
    },
    setup(props, context){
        const store = useStore();
        let is_me = computed(()=> props.user.id == store.state.user.id);

        const delete_a_post = (post_id)=>{
            $.ajax({
                type: "DELETE",
                url: baseUrl.remoteHttpsUrl + "/api/myspace/post/",
                data: {
                    post_id
                },
                headers: {
                    'Authorization': 'Bearer ' + store.state.user.token,
                },
                success(resp){
                    if (resp.result === "success"){
                        context.emit("delete_a_post", post_id);
                    }
                }
            });
        }

        return {
            is_me,
            delete_a_post,
        }
    }
}
</script>

<style scoped>
.single-card{
    margin-bottom: 10px;
}
button{
    float: right;
}
</style>