<template>
  <ContentView>
    <table class="table table-striped table-hover" style="text-align:center;">
      <thead>
      <tr>
        <th scope="col">
          <div>玩家A</div>
        </th>
        <th scope="col">
          <div>玩家B</div>
        </th>
        <th scope="col">
          <div>对局结果</div>
        </th>
        <th scope="col">
          <div>创建时间</div>
        </th>
        <th scope="col">
          <div>操作</div>
        </th>
      </tr>
      </thead>
      <tbody v-for="record in records" :key="record.record.id">
      <tr>
        <td>
          <img :src="record.a_user_photo" alt="" class="record-user-photo">
          &nbsp;
          <span class="record-username">{{record.a_username}}</span>
        </td>
        <td>
          <img :src="record.b_user_photo" class="record-user-photo">
          <span class="record-username">{{record.b_username}}</span>
        </td>
        <td>{{record.result}}</td>
        <td>{{record.record.createtime}}</td>
        <td>
          <button @click="open_record_content(record.record.id)" type="button" class="btn btn-primary">查看录像</button>
        </td>
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
import router from '@/router';
import baseUrl from "@/config/config";

export default{
  name: "RecordListView",
  components: { ContentView },
  setup(){
    const store = useStore();
    let records = ref([]);
    let current_page = 1;
    let pages_count = 0;
    let pages = ref([]);

    let click_page = page =>{
      if (page === -2) page = current_page - 1;
      else if (page === -1) page = current_page + 1;
      let max_page = parseInt(Math.ceil(pages_count / 10));

      if (page >= 1 && page <= max_page){
        pull_page(page);
      }
    }

    const update_pages = () =>{
      let max_page = parseInt(Math.ceil(pages_count / 10));
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
        url: baseUrl.remoteHttpsUrl + "/api/record/getList/",
        type: "get",
        data:{
          page,
        },
        headers: {
          Authorization: "Bearer " + store.state.user.token,
        },
        success(resp){
          records.value = resp.record_info;
          pages_count = resp.total_page_count;
          update_pages();
        },
        error(resp){
          console.log(resp);
        }
      });
    };

    const string2map2D = map =>{
      let g = [];
      for (let i = 0, k = 0; i < 13; i ++){
        let line = [];
        for (let j = 0; j < 14; k ++, j ++){
          if(map[k] === '0') line.push(0);
          else line.push(1);
        }
        g.push(line);
      }
      return g;
    }

    const open_record_content = recordId =>{
      for (const record of records.value){
        if (record.record.id === recordId){
          store.commit("updateIsRecord", true);
          store.commit("updateGame", {
            map: string2map2D(record.record.map),
            a_id: record.record.aId,
            a_sx: record.record.aSx,
            a_sy: record.record.aSy,
            b_id: record.record.bId,
            b_sx: record.record.bSx,
            b_sy: record.record.bSy,
          })
          store.commit("updateSteps", {
            a_steps: record.record.aSteps,
            b_steps: record.record.bSteps,
          });
          store.commit("updateRecordLoser", {
            record_loser: record.record.loser,
          });
          router.push({
            name: "record_content",
            params: {
              recordId,
            },
          })
          break;
        }
      }
    }

    pull_page(current_page);

    return {
      records,
      open_record_content,
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