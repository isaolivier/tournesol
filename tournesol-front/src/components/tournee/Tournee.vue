<template>
    <div>
        <dates @dayChanged="fetchData"></dates>

        <svg id="agenda" :height="nbHeures * hourInterval + 20">

            <!-- Heures -->
            <text v-for="h in (nbHeures + 1)" x="0" :y="(h - 1) * hourInterval + 15">
                {{parseInt(h) + parseInt(heureOuverture) - 1}}:00
            </text>

            <line v-for="h in (nbHeures + 1)" x1="50" x2="100%"
                  :y1="(h - 1) * hourInterval + 10"
                  :y2="(h - 1) * hourInterval + 10"
                  style="stroke:rgb(0,0,0);stroke-width:1"/>

            <line v-for="h in (nbHeures + 1)" x1="50" x2="100%"
                  :y1="(h - 1) * hourInterval + 10 + (hourInterval/2)"
                  :y2="(h - 1) * hourInterval + 10 + (hourInterval/2)"
                  style="stroke:rgb(180,180,180);stroke-width:0.5"/>

            <!-- Rendez-vous -->
            <rdv v-if="distances" v-for="(rdv, key, index) in rendezvous" :rdv="rdv" :distance="getDistance(key)" :key="key"/>
        </svg>
    </div>
</template>

<script>
  import {entreprise} from '../../bean/EntrepriseBean'
  import RendezVous from './RendezVous.vue'
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
        return moment.duration(entreprise.configuration.heureOuverture).hours()
      },
      heureFermeture: function () {
        return moment.duration(entreprise.configuration.heureFermeture).hours()
      },
      nbHeures: function () {
        // console.log(heureFermeture - heureOuverture)
        return moment.duration(entreprise.configuration.heureFermeture.diff(entreprise.configuration.heureOuverture)).hours()
      }
    },
    components: {
      'rdv': RendezVous,
      'dates': DateStrip
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
      },

      getDistance (index) {
        return this.distances[index]
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    #agenda {
        width: 100%;
        overflow-y: scroll;
    }
</style>
