/**
 * Created by draluy on 03/07/2017.
 */
import {EventBean} from './EventBean'

export class RendezVousBean {

  constructor () {
    this.placeId = null
    this.adresseId = null
    this.appareils = []
    this.client = null
    this.event = new EventBean()
  }

}
