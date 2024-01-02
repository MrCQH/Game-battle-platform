import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueLuckyCanvas from '@lucky-canvas/vue'
import {VueResource} from 'vue-resource';

createApp(App).use(store).use(router).use(VueLuckyCanvas).use(VueResource).mount('#app')
