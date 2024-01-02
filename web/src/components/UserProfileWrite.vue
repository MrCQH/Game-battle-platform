<template>
    <div class="card edit-area">
        <div class="card-body">
            <div class="mb-3">
                <label for="edit-area" class="form-label">编辑区</label>
                <textarea v-model="content" class="form-control" id="edit-area" rows="3"></textarea>
                <button @click="post_a_post" class="btn btn-success">发帖</button>
            </div>
        </div>
    </div>
</template>

<script>
import {ref} from 'vue';
import $ from 'jquery';
import { useStore } from 'vuex';
import baseUrl from "@/config/config";

export default{
    name:'UserProfileWrite',
    
    setup(props, context){
        let content = ref('');
        const store = useStore();

        const post_a_post = ()=>{
            $.ajax({
                type: "POST",
                url: baseUrl.remoteHttpsUrl + "/api/myspaces/post/",
                data:{
                    content: content.value,
                },
                headers: {
                    'Authorization': "Bearer " + store.state.user.token,
                },
                success(resp){
                    if (resp.result === "success"){
                        context.emit('submit', content.value);
                        content.value = '';
                    }
                }
            });
        }

        return {
            content,
            post_a_post
        }
    }
}
</script>

<style scoped>
.edit-area{
    margin-top: 10px;
}
button{
    padding: 2px 4px;
    font-size: 12px;
    margin-top: 10px;
    margin-left: 80%;
}
</style>