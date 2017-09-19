import Vue from 'vue'
import VueResource from 'vue-resource'
import Constants from '../bean/Constants'
Vue.use(VueResource)

export class AppareilResource {

  findAppareils (client, result) {
    return Vue.http.get(Constants.back.hostname + '/appareil/' + client.id).then(response => {
      result(null, response.data)
    }, response => {
      result(response, null)
    })
  }
}
