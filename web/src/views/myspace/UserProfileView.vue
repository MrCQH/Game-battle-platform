<template>
  <ContentView>
    <div class="row">
      <div class="col-3">
        <UserProfileInfo @follow="follow" @unfollow="unfollow" :user="user"/>
        <UserProfileWrite v-if="is_me" @submit="submit"/>
      </div>
      <div class="col-9">
        <UserProfilePost @delete_a_post="delete_a_post" :posts="posts" :user="user" />
      </div>
    </div>
  </ContentView>
</template>

<script>
import ContentView from '@/components/ContentView.vue';
import UserProfileInfo from '../../components/UserProfileInfo.vue';
import UserProfilePost from '../../components/UserProfilePost.vue';
import UserProfileWrite from '../../components/UserProfileWrite.vue';
import {reactive} from 'vue';
import { useRoute } from 'vue-router';
import $ from 'jquery';
import { useStore } from 'vuex';
import { computed } from 'vue';
import baseUrl from "@/config/config";

export default {
  name: 'UserProfileView',
  components: {
    ContentView,
    UserProfileInfo,
    UserProfilePost,
    UserProfileWrite,
  },
  setup(){
    const route = useRoute();
    const userId = parseInt(route.params.userId);
    const store = useStore();
    const user = reactive({});
    const posts = reactive({});
    const is_me = computed(()=> userId == store.state.user.id);

    const delete_a_post = (post_id)=>{
      posts.posts = posts.posts.filter((post)=> post_id !== post.id); // filter是遍历的意思
      posts.count = posts.posts.length;
    }

    $.ajax({
      type: "GET",
      url: baseUrl.remoteHttpsUrl + "/api/myspaces/getInfo/",
      data: {
        userId: userId
      },
      headers: {
        Authorization: 'Bearer ' + store.state.user.token,
      },
      success(resp){
        user.username = resp.username;
        user.followerCount = resp.followerCount;
        user.photo = resp.photo;
        user.id = resp.id;
        user.is_followed = resp.is_followed;
      }
    });

    $.ajax({
      url: baseUrl.remoteHttpsUrl + "/api/myspaces/post/",
      type: "GET",
      headers: {
        'Authorization': 'Bearer ' + store.state.user.token,
      },
      data:{
        userId: userId,
      },
      success(resp){
        console.log(resp)
        posts.posts = resp.posts;
        posts.count = resp.count;
      }
    });

    const follow = ()=>{
      if (user.is_followed) return;
      user.is_followed = true;
      user.followerCount ++;
    }

    const unfollow = ()=>{
      if (!user.is_followed) return;
      user.is_followed = false;
      user.followerCount --;
    }

    const submit = (content)=>{
      posts.count ++;
      posts.posts.unshift({
        id: posts.count,
        userId: store.state.user.id,
        content:content,
      });
    }

    return{
      user,
      follow,
      unfollow,
      posts,
      submit,
      is_me,
      delete_a_post,
    };
  }
}
</script>

<style scoped>

</style>
