<template>
    <div>
        <dates @dayChanged="fetchData"></dates>

        <div id="agenda" :style="'height:'+(nbHeures * hourInterval + 100)+'px'">

            <!-- Heures -->
            <div  class="hour" v-for="h in (nbHeures + 1)" :style="'top:'+((h - 1) * hourInterval + 50)+'px'">
                {{parseInt(h) + parseInt(heureOuverture) - 1}}:00
            </div>

            <hr v-for="h in (nbHeures + 1)"
                  :style="'top:'+((h - 1) * hourInterval + 10)+'px'"
                  />

            <hr v-for="h in (nbHeures + 1)" x1="50" x2="100%"
                :style="'top:'+((h - 1) * hourInterval + 10 + (hourInterval/2))+'px'"
                  />

            <!-- Rendez-vous -->
        </div>
        <rdv v-for="(rdv, key, index) in rendezvous" :rdv="rdv" :key="rdv.event.id"/>
        <estimation v-if="distances" v-for="(distance, key, index) in distances" :estimation="distance" :rdvs="rendezvous" :key="distance.sourceEventId"/>
    </div>
</template>

<script>
  import {entreprise} from '../../bean/EntrepriseBean'
  import RendezVous from './RendezVous.vue'
  import Estimation from './Estimation.vue'
  import DateStrip from './DateStrip.vue'
  import Constants from '../../bean/Constants'
  import moment from 'moment'
  import {RendezVousResource} from '../../resource/RendezVousResource'

  export default {
    name: 'tournee',
    data () {
      return {
        error: null,
        hourInterval: Constants.tournee.hourInterval,
        agenda: null,
        rendezvous: null,
        distances: null,
        currentDate: moment()
      }
    },
    created () {
      this.fetchData(moment())
    },
    computed: {
      heureOuverture: function () {
        let heureOuverture = moment(entreprise.configuration.heureOuverture).format('HH:mm')
        return moment.duration(heureOuverture).asHours()
      },
      heureFermeture: function () {
        let heureFermeture = moment(entreprise.configuration.heureFermeture).format('HH:mm')
        return moment.duration(heureFermeture).asHours()
      },
      nbHeures: function () {
        let heureOuverture = moment(entreprise.configuration.heureOuverture).format('HH:mm')
        let heureFermeture = moment(entreprise.configuration.heureFermeture).format('HH:mm')
        let result = Math.round((moment.duration(heureFermeture).asMinutes() - moment.duration(heureOuverture).asMinutes()) / 60)
        // console.log('nb heures', result)
        return result
      }
    },
    components: {
      'rdv': RendezVous,
      'dates': DateStrip,
      'estimation': Estimation
    },
    methods: {
      fetchData: function (date) {
        this.error = null
        let rdvResource = new RendezVousResource()

        rdvResource.findRendezVous((err, result) => {
          if (err) {
            this.error = err.toString()
          } else {
            this.rendezvous = result
          }
        }, date.format(Constants.rdv.dateFormat))

        rdvResource.findDistanceRendezVous((err, result) => {
          if (err) {
            this.error = err.toString()
          } else {
            this.distances = result
          }
        }, date.format(Constants.rdv.dateFormat))
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    hr, .hour {
        position: absolute
    }
    hr {
        left: 50px;
        width:100%;
    }

    #agenda {
        width: 100%;
        overflow-y: hidden;
        position: relative;
    }
</style>
