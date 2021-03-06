import Vue from 'vue'
import moment from 'moment'
import VueResource from 'vue-resource'
import Constants from '../bean/Constants'
import {authService} from '../components/auth/auth-service'
import {RendezVousBean} from '../bean/RendezVousBean'

Vue.use(VueResource)

export class RendezVousResource {

  // Recherche des rendez-vous pour une date précise
  findRendezVous (result, date) {
    return Vue.http.get(Constants.back.hostname + '/rdvs?date=' + date,
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

  // Recherche des rendez-vous pour une date précise
  findClientRendezVous (dayRange, result) {
    return Vue.http.get(Constants.back.hostname + '/rdvs?dayRange=' + dayRange + '&clientRequired=true',
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

  // Rectourne la timeline, contenant également les infos de distance, pour une journée donnée
  getTimeline (result, date) {
    return Vue.http.get(Constants.back.hostname + '/rdv/timeline?date=' + date,
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

  // Recherche des dates disponibles pour le mois sélectionné
  findFreeDays (month, year, result) {
    let startDate = moment([year, month]).format(Constants.rdv.dateFormat)
    let endDate = moment(startDate).endOf('month').format(Constants.rdv.dateFormat)
    return Vue.http.get(Constants.back.hostname + '/rdv/free?startDate=' + startDate + '&endDate=' + endDate,
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

  createRendezVous (rdvForm, client, result) {
    let rdv = this.map(rdvForm, client)

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

  map (rdvForm, client) {
    let rdv = new RendezVousBean()
    rdv.placeId = rdvForm.placeId
    rdv.client = client.id
    rdv.adresseId = client.adresse.id
    rdv.appareils = rdvForm.appareils
    rdv.event.id = rdvForm.event.id
    rdv.event.date = moment(rdvForm.event.date).format(Constants.rdv.dateFormat)
    rdv.event.startTime = rdvForm.event.startTime
    rdv.event.endTime = rdvForm.event.endTime
    rdv.event.summary = rdvForm.event.summary
    rdv.event.description = rdvForm.event.description
    rdv.event.location = rdvForm.event.location
    rdv.event.status = rdvForm.event.status

    return rdv
  }
}
