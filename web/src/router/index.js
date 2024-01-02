import { createRouter, createWebHistory } from 'vue-router';
import BattleView from '@/views/battle/BattleView.vue';
import RecordListView from '@/views/recordlist/RecordListView';
import RecordContentView from '@/views/recordlist/RecordContentView';
import myinfoView from '@/views/user/myinfo/myinfoView.vue';
import NotfoundView from '@/views/notfound/NotfoundView.vue';
import RankListView from '@/views/ranklist/RankListView.vue';
import UserAccountLoginView from "@/views/user/account/UserAccountLoginView";
import UserAccountRegisterView from "@/views/user/account/UserAccountRegisterView";
import store from "@/store/index";
import userProfileView from "@/views/myspace/UserProfileView";
import userListView from "@/views/myspace/UserListView";
import LuckyView from "@/views/lottery/LuckyView";
import userProfileInfo from "@/components/UserProfileInfo";
import userProfilePost from "@/components/UserProfilePost";
import userProfileWrite from "@/components/UserProfileWrite";
import AwardView from "@/views/user/myinfo/AwardView";


const routes = [
  {
    name: 'home',
    path: '/',
    redirect: '/battle/',
    meta: {
      requstAuth: true,
    },
  },
  {
    name: 'battle',
    path: '/battle/',
    component: BattleView,
    meta: {
      requstAuth: true,
    },
  },
  {
    name: 'record_list',
    path: '/recordlist/',
    component: RecordListView,
    meta: {
      requstAuth: true,
    },
  },
  {
    name: 'record_content',
    path: '/recordcontent/:recordId/',
    component: RecordContentView,
    meta: {
      requstAuth: true,
    },
  },
  {
    name: 'my_spaces',
    path: '/user/my_space/',
    component: userListView,
    meta: {
      requstAuth: true,
    }
  },
  {
    name: 'userprofile',
    path: '/user/profile/:userId/',
    component: userProfileView,
    meta: {
      requstAuth: true,
    }
  },
  {
    name: 'userProfileInfo',
    path: '/user/info/',
    component: userProfileInfo,
    meta: {
      requstAuth: true,
    }
  },
  {
    name: 'userProfilePost',
    path: '/user/post/',
    component: userProfilePost,
    meta: {
      requstAuth: true,
    }
  },
  {
    name: 'userProfileWrite',
    path: '/user/write/',
    component: userProfileWrite,
    meta: {
      requstAuth: true,
    }
  },
  {
    name: 'myinfo',
    path: '/user/myinfo/',
    component: myinfoView,
    meta: {
      requstAuth: true,
    },
  },
  {
    name: 'user_account_login',
    path: '/user/account/login/',
    component: UserAccountLoginView,
    meta: {
      requstAuth: false,
    },
  },
  {
    name: 'user_account_register',
    path: '/user/account/register/',
    component: UserAccountRegisterView,
    meta: {
      requstAuth: false,
    },
  },
  {
    name: '404',
    path: '/404/',
    component: NotfoundView,
  },
  {
    name: 'rank_list',
    path: '/ranklist/',
    component: RankListView,
    meta: {
      requstAuth: true,
    },
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/404/',
  },
  {
    name: 'lottery',
    path: '/lottery/',
    component: LuckyView,
    meta: {
      requstAuth: true,
    }
  },
  {
    name: 'myAwards',
    path: '/awards/',
    component: AwardView,
    meta: {
      requstAuth: true,
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) =>{
  if (!store.state.user.is_login && to.meta.requstAuth){
      next({name: 'user_account_login'});
  } else {
    next();
  }
})

export default router
