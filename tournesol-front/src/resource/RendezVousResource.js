import Vue from 'vue'
import VueResource from 'vue-resource'
import Constants from '../bean/Constants'
import {authService} from '../components/auth/auth-service'

Vue.use(VueResource)

export class RendezVousResource {

  findRendezVous (result, dateStr) {
    return Vue.http.get(Constants.back.hostname + '/rdvs?date=' + dateStr,
      {
        headers: {
          'uid': authService.getAuthInfo().uid,
          'email': authService.getAuthInfo().email
        }
      }).then(response => {
        result(null, response.data)
      }, response => {
        result(response, null)
      })
  }

  createRendezVous (rdv, result) {
    return Vue.http.post(Constants.back.hostname + '/rdv', rdv,
      {
        headers: {
          'uid': authService.getAuthInfo().uid,
          'email': authService.getAuthInfo().email
        }
      }).then(response => {
        result(null)
      }, response => {
        result(response)
      })
  }
}
