import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

export class AppareilResource {

  findAppareils (client, result) {
    return Vue.http.get('http://localhost:8081/appareil/' + client.id).then(response => {
      // get body data
      console.log(response)
      result(null, response.data)
    }, response => {
      result(response, null)
    })
  }
}
