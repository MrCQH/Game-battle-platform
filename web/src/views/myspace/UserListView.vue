<template>
  <ContentView>
    <div class="card" v-for="user in users" :key="user.id" @click="visit_a_post(user.id)">
      <div class="card-body">
        <div class="row">
            <div class="col-1 img-field">
              <img class="img-fluid" :src="user.photo" alt="图片无法显示">
            </div>
            <div class="col-11">
              <div class="username">{{user.username}}</div>
              <div class="followerCount">{{user.followerCount}}</div>
            </div>
        </div>       
      </div>
    </div>
  </ContentView>
</template>

<script>
import ContentView from '../../components/ContentView.vue';
import $ from 'jquery';
import { ref } from 'vue';
import router from '@/router/index';
import { useStore } from 'vuex';
import baseUrl from "@/config/config";

export default {
  name: 'UserListView',
  components: {
    ContentView,
  },
  setup(){
    let users = ref([]);
    const store = useStore();

    $.ajax({
      type: "get",
      url: baseUrl.remoteHttpsUrl + "/api/myspaces/usersList/",
      headers:{
        Authorization: 'Bearer ' + store.state.user.token,
      },
      success: (resp) =>{
        users.value = resp;
      }
    });

    const visit_a_post = (userId)=>{
      if (store.state.user.is_login)
        router.push({
          name: 'userprofile', 
          params: {
            userId: userId,
          },
        });
      else {
        router.push({name: 'login'});
      }
    }
    return {
      users,
      visit_a_post
    }
  }
}
</script>

<style scoped>
img{
  border-radius: 50%;
}
.username{
  font-weight: bold;
  height: 50%;
}
.followerCount{
  font-size: 14px;
  color: gray;
  height: 50%;
}
.card{
  cursor: pointer;
  margin-bottom: 1rem;
}
.card:hover{
  box-shadow: 2px 2px 10px lightgray;
  transition: 300ms;
}
.img-field{
    display: flex;
    flex-direction: column;
    justify-content: center;
}
</style>
