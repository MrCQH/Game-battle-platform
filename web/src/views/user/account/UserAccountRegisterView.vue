<template>
    <ContentView>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="register">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密码</label>
                        <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码" autocomplete>
                    </div>
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">确认密码</label>
                        <input v-model="confirmPassword" type="password" class="form-control" id="confirmPassword" placeholder="请再次输入密码" autocomplete>
                    </div>
                    <img @click="refreshImg" :src="verifyImg">
                    <br>
                    <br>
                    <div class="mb-3">
                      <input v-model="verifyCode" type="text" class="form-control" id="verifyCode" placeholder="请输入验证码" autocomplete>
                    </div>
                    <div class="error-message">{{error_message}}</div>
                    <button type="submit" class="btn btn-success">注册</button>
                </form>
            </div>
        </div>
    </ContentView>
</template>

<script>
import ContentView from '@/components/ContentView.vue';
import $ from  "jquery";
import {ref} from "vue";
import router from "@/router/index";
import baseUrl from "@/config/config";

export default{
    name: "UserAccountRegisterView",
    components: {
      ContentView,
    },
    setup(){
        const username = ref("");
        const password = ref("");
        const confirmPassword = ref("");
        let error_message = ref("");
        let verifyCode = ref("");
        let verifyImg = ref("");

        const refreshImg = () => {
          $.ajax({
            type: "GET",
            url: baseUrl.remoteHttpsUrl + '/api/user/account/getVerifyImage/',
            success(resp){
              if (resp.error_message === "success"){
                verifyImg.value = resp.url
              }
            },
          });
        }
        refreshImg()

        const register = ()=>{
            $.ajax({
                type: "post",
                url: baseUrl.remoteHttpsUrl + "/api/user/account/register/",
                data: {
                  username: username.value,
                  password: password.value,
                  confirmPassword: confirmPassword.value,
                  verifyCode: verifyCode.value
                },
                success(resp){
                  if (resp.error_message === "success"){
                      router.push({name: 'user_account_login'});
                      error_message.value = "";
                  } else {
                    error_message.value = resp.error_message;
                    refreshImg();
                    verifyCode.value = "";
                  }
              },
          });
      };

      return {
        username,
        password,
        confirmPassword,
        error_message,
        register,
        baseUrl,
        verifyCode,
        refreshImg,
        verifyImg
      }
    },
}
</script>

<style scoped>
button{
    width: 100%;
}
.error-message{
    color: red;
    margin-bottom: 1rem;
}
</style>