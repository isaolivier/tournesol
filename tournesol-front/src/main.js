// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import router from './router'
import Icon from 'vue-awesome/components/Icon'

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.component('icon', Icon)

import 'element-ui/lib/theme-default/index.css'
import 'vue-awesome/icons'

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App, Icon }
})
