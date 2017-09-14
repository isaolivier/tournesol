import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

export class ClientResource {

  findAllClient (result) {
    return Vue.http.get('http://localhost:8081/client').then(response => {
      // get body data
      console.log(response)
      result(null, response.data)
    }, response => {
      result(response, null)
    })
  }
}
