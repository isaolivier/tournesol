import Vue from 'vue'
import VueResource from 'vue-resource'
import Constants from '../bean/Constants'
Vue.use(VueResource)

export class ClientResource {

  findAllClient (result) {
    return Vue.http.get(Constants.back.hostname + '/client').then(function (response) {
      // get body data
      result(null, response.data)
    }, response => {
      result(response)
    })
  }

  createClient (client, result) {
    return Vue.http.post(Constants.back.hostname + '/client', client).then(response => {
      result(null)
    }, response => {
      result(response)
    })
  }

  updateClient (client, result) {
    return Vue.http.put(Constants.back.hostname + '/client', client).then(response => {
      result(null)
    }, response => {
      result(response)
    })
  }

  deleteClient (client, result) {
    return Vue.http.delete(Constants.back.hostname + '/client/' + client).then(response => {
      result(null)
    }, response => {
      result(response)
    })
  }
}
