/**
 * Created by draluy on 04/07/2017.
 */
import {EntrepriseConfigurationBean} from './EntrepriseConfigurationBean'
import Constants from './Constants'
import axios from 'axios'
import moment from 'moment'

class EntrepriseBean {

  constructor () {
    this.nom = null
    this.siret = null
    this.configuration = new EntrepriseConfigurationBean()
    this.req = axios.create({
      baseURL: Constants.back.hostname,
      timeout: 2000
    })
    this.load()
  }

  load () {
    let self = this

    this.req.get('/entreprise').then(
      function (response) {
        if (response.data) {
          console.log('Loaded entreprise configuration ' + response.data)
          self.nom = response.data.nom
          self.siret = response.data.siret
          let conf = response.data.configuration
          self.configuration.joursOuverture = conf.joursOuverture
          self.configuration.heureOuverture = moment(conf.heureOuverture, 'HH:mm')
          self.configuration.heureFermeture = moment(conf.heureFermeture, 'HH:mm')
          self.configuration.searchDistance = conf.searchDistance
          self.configuration.searchDays = conf.searchDays
          self.configuration.tempsRdv = conf.tempsRdv
          self.configuration.timeStep = moment.utc(conf.timeStep * 60 * 1000).format('HH:mm')
        } else {
          console.log('Entreprise not loaded')
        }
      },
      function (error) {
        console.log(error)
      }
    )
  }
}

let entreprise = new EntrepriseBean()
export {entreprise}
