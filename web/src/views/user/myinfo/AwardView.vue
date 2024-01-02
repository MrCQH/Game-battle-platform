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
                            <span style="font-size: 130%; margin-left: 0.5rem;">我的奖励</span>


                            <button @click="pull_award_info()"  type="button" class="float-end btn btn-success" style="margin-top: -0.3rem" >
                                刷新
                            </button>

                        </div>
                        <hr>
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">
                                        <div style="margin-left:0.5rem">奖励名称</div>
                                    </th>
                                    <th scope="col">
                                        <div style="margin-left:3rem">获得时间</div>
                                    </th>
                                    <th scope="col">
                                        <div style="margin-left:3rem">奖励描述</div>
                                    </th>
                                </tr>
                            </thead>
                            <tbody v-for="award_info in award_list" :key="award_info.id">
                                <tr>
                                    <td>{{award_info.awardName}}</td>
                                    <td>{{award_info.grantDate}}</td>
                                    <td>{{award_info.awardContent}}</td>
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
import { ref } from "vue";
import baseUrl from "@/config/config";


export default {
    components: {
        ContentView,
    },

    setup(){
      const store = useStore();
      let cur_page = 1;
      const rows = 5; // 一页多少行
      let pages_count = 0;
      let award_list = ref([]);
      let pages = ref([]);

      const update_award_info = ()=>{

      }

      const pull_award_info = page => {
        cur_page = page;
        $.ajax({
          url: baseUrl.remoteHttpsUrl + "/api/user/account/queryAwardListPage/",
          type: "get",
          headers: {
            Authorization: "Bearer " + store.state.user.token,
          },
          data: {
            uId: store.state.user.username,
            page,
            rows,
          },
          success(resp){
            pages_count = resp.count;
            award_list.value = resp.data;
            // console.log(award_list.value);
            update_pages();
          }
        });
      }

      let click_page = page =>{
        if (page === -2) page = cur_page - 1;
        else if (page === -1) page = cur_page + 1;
        let max_page = parseInt(Math.ceil(pages_count / rows));

        if (page >= 1 && page <= max_page){
          pull_award_info(page);
        }
      }

      const update_pages = () =>{
        let max_page = parseInt(Math.ceil(pages_count / rows));
        let new_pages = [];
        for (let i = cur_page - 2; i <= cur_page + 2; i ++){
          if (i >= 1 && i <= max_page){
            new_pages.push({
              number: i,
              is_active: cur_page === i ? "active" : "",
            });
          }
        }
        pages.value = new_pages;
      }

        pull_award_info(cur_page);

        return {
          update_award_info,
          pull_award_info,
          award_list,
          pages_count,
          pages,
          click_page
        }
    }
}
</script>

<style scoped>
button:focus{
  outline: none;
  box-shadow: none;
}

a:focus{
  outline: none;
  box-shadow: none;
}
</style>