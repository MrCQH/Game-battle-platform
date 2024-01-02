<template>
    <div class="card">
        <div class="card-body">
            <div class="row">
                <div class="col-3 img-field">
                    <img class="img-fluid" :src="user.photo" alt="头像无法显示">
                </div>
                <div class="col-9">
                    <div class="username">{{user.username}}</div>
                    <div class="fans">粉丝:{{user.followerCount}}</div>
                    <button @click="follow" v-if="!user.is_followed && !is_me" class="btn btn-info">+关注</button>
                    <button @click="unfollow" v-if="user.is_followed && !is_me" class="btn btn-info">取消关注</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex';
import $ from 'jquery';
import {useRoute} from "vue-router";
import { computed } from "vue";
import baseUrl from "@/config/config";

export default{
    name: 'UserProfileInfo',
    props: {
      user: {
        type: Object,
        required: true,
      },
    },
    setup(props, context){
      const route = useRoute()
      const is_me = computed(() => parseInt(route.params.userId) == store.state.user.id)
      const store = useStore();
      const follow = ()=>{
        $.ajax({
          type: "POST",
          url: baseUrl.remoteHttpsUrl + "/api/myspaces/updatefollower/",
          headers: {
              "Authorization": "Bearer " + store.state.user.token,
          },
          data: {
              target_id: props.user.id,
              is_followed: props.user.is_followed
          },
          success(resp){
              if (resp.result === "success"){
                  context.emit('follow');
              }
          },
          error(resp){
            console.log(resp);
          }
        });
      }

        const unfollow = ()=>{
          $.ajax({
            type: "POST",
            url: baseUrl.remoteHttpsUrl + "/api/myspaces/updatefollower/",
            headers: {
                "Authorization": "Bearer " + store.state.user.token,
            },
            data: {
                target_id: props.user.id,
                is_followed: props.user.is_followed
            },
            success(resp){
                if (resp.result === "success"){
                    context.emit('unfollow');
                }
            },
            error(resp){
              console.log(resp);
            }
          });
        }

        return {
          follow,
          unfollow,
          is_me
        }
    },

}
</script>

<style scoped>
img{
    border-radius: 50%;
}
.username{
    font-weight: bold;
}
.fans{
    font-size: 12px;
    color:gray;
}
button{
    padding: 2px 4px;
    font-size: 12px;
}
.img-field{
    display: flex;
    flex-direction: column;
    justify-content: center;
}
</style>