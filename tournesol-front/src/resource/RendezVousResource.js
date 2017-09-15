import Vue from 'vue'
import VueResource from 'vue-resource'
import {authService} from '../components/auth/auth-service'

Vue.use(VueResource)

export class RendezVousResource {

  findRendezVous (result) {
    return Vue.http.get('http://localhost:8081/rdvs',
      {
        headers: {
          'uid': authService.getAuthInfo().uid,
          'email': authService.getAuthInfo().email
        }
      }).then(response => {
        // get body data
        console.log(response)
        result(null, response.data)
      }, response => {
        result(response, null)
      })
  }

  createRendezVous (rdv, result) {
    return Vue.http.post('http://localhost:8081/rdv', rdv,
      {
        headers: {
          'uid': authService.getAuthInfo().uid,
          'email': authService.getAuthInfo().email
        }
      }).then(response => {
        console.log(response)
        result(null)
      }, response => {
        result(response)
      })
  }
}
