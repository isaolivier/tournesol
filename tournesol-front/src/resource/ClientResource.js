import Vue from 'vue'
import VueResource from 'vue-resource'
import Constants from '../bean/Constants'
Vue.use(VueResource)

export class ClientResource {

  findAllClient (result) {
    return Vue.http.get(Constants.back.hostname + '/client').then(response => {
      // get body data
      console.log(response)
      result(null, response.data)
    }, response => {
      result(response, null)
    })
  }

  createClient (client, result) {
    return Vue.http.post('http://localhost:8081/client', client).then(response => {
      console.log(response)
      result(null)
    }, response => {
      result(response)
    })
  }
}
