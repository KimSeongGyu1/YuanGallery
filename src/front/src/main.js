import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import {router} from './router'
import VueCookie from 'vue-cookie'
import VueAnalytics from "vue-analytics";

Vue.config.productionTip = false

Vue.use(VueCookie);

Vue.use(VueAnalytics, {
  id: "UA-136033416-2",
  router
})

new Vue({
  vuetify,
  render: h => h(App),
  router
}).$mount('#app')
