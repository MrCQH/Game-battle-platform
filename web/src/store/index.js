import { createStore } from 'vuex'
import ModuleUser from './user'
import ModuleBattle from './battle';
import ModuleRecord from './record';

export default createStore({
  // 维护的数据
  state: {
  },
  getters: {
  },
  // 同步调用
  mutations: {
  },
  // 异步调用
  actions: {
  },
  // 相当于root-pom, import进来
  modules: {
    user: ModuleUser,
    battle: ModuleBattle,
    record: ModuleRecord,
  }
})
