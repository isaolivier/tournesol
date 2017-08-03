import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

export class RendezVousResource {

  findRendezVous (result) {
    return Vue.http.get('http://localhost:8081/rdvs').then(response => {
      // get body data
      console.log(response)
      return result(null, response.data)
    }, response => {
      return (response, null)
    })
  }
}
