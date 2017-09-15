// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import router from './router'
import VueResource from 'vue-resource'
import 'font-awesome/css/font-awesome.css'
import locale from 'element-ui/lib/locale/lang/fr'

Vue.config.productionTip = false

Vue.use(ElementUI, { locale })
Vue.use(VueResource)

import 'element-ui/lib/theme-default/index.css'
const AuthStatus = {
  initialized: false,
  signedIn: false
}
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App },
  data: AuthStatus
})
