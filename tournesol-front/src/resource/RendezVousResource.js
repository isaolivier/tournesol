import Vue from 'vue'
import VueResource from 'vue-resource'
import Constants from '../bean/Constants'
import {authService} from '../components/auth/auth-service'

Vue.use(VueResource)

export class RendezVousResource {

  // Recherche des rendez-vous pour une date précise
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

  // Recherche des dates possédant des rdvs existants pour une adresse donnée
  findPropositionRendezVous (days, distance, placeId, adresseId, result) {
    return Vue.http.get(Constants.back.hostname + '/rdv/search?dayRange=' + days + '&distanceRange=' + distance + '&placeId=' + placeId + '&adresseId=' + adresseId,
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
