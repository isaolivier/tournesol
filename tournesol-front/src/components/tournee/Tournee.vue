<template>

    <div>
        Slider des dates<br/>
        <dates></dates>

        <svg id="agenda" :height="nbHeures * hourInterval + 20">

            <!-- Heures -->
            <text v-for="h in (nbHeures + 1)" x="0" :y="(h - 1) * hourInterval + 15">{{h
            + heuresOuverture[0] - 1}}:00
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
            <rdv v-for="(rdv, key, index) in rendezvous" :rdv="rdv" :key="key"/>
        </svg>
    </div>

</template>

<script>
  import RendezVous from './RendezVous.vue'
  import DateStrip from './DateStrip.vue'
  import Constants from '../../bean/Constants'
  import {RendezVousResource} from '../../resource/RendezVousResource'

  export default {
    name: 'tournee',

    data () {
      return {
        loading: false,
        error: null,
        heuresOuverture: [8, 18],
        rendezvous: [],
        hourInterval: Constants.tournee.hourInterval,
        agenda: null
      }
    },
    created () {
      this.fetchData()
    },
    computed: {
      nbHeures: function () {
        return this.heuresOuverture[1] - this.heuresOuverture[0]
      }
    },
    components: {
      'rdv': RendezVous,
      'dates': DateStrip
    },
    methods: {
      fetchData () {
        this.error = null
        this.loading = true
        let rdvResource = new RendezVousResource()
        rdvResource.findRendezVous((err, result) => {
          this.loading = false
          if (err) {
            this.error = err.toString()
          } else {
            this.rendezvous = result
          }
        })
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
