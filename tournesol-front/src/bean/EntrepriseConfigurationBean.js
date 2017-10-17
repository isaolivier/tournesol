/**
 * Created by draluy on 04/07/2017.
 */
import moment from 'moment'

export class EntrepriseConfigurationBean {

  constructor () {
    this.joursOuverture = [1, 2, 3, 4, 5]
    this.heureOuverture = moment('08:00', 'HH:mm')
    this.heureFermeture = moment('19:00', 'HH:mm')
    this.timeStep = '00:30'
    this.tempsRdv = 90
    this.searchDays = 45
    this.searchDistance = 50
  }
}
