<template>

    <div>
        Slider des dates<br/>

        <svg class="agenda" :height="nbHeures * hourInterval + 20">

            <!-- Heures -->
            <text v-for="h in (nbHeures + 1)" x="0" :y="(h - 1) * hourInterval + 15">{{h
                + heuresOuverture[0] - 1}}:00
            </text>

            <line v-for="h in (nbHeures + 1)" x1="50" :x2="svgWidth"
                  :y1="(h - 1) * hourInterval + 10"
                  :y2="(h - 1) * hourInterval + 10"
                  style="stroke:rgb(0,0,0);stroke-width:1"/>

            <line v-for="h in (nbHeures + 1)" x1="50" :x2="svgWidth"
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
  import {RendezVousBean} from '../bean/RendezVousBean.js'
  import Constants from '../bean/Constants'

  export default {
    name: 'tournee',

    data () {
      return {
        heuresOuverture: [8, 18],
        rendezvous: [],
        hourInterval: Constants.tournee.hourInterval
      }
    },
    mounted: function () {
      let rdv1 = new RendezVousBean(new Date(2017, 3, 1, 8, 30), new Date(2017, 3, 1, 10), 'Alex Durand', null)
      let rdv2 = new RendezVousBean(new Date(2017, 3, 1, 14), new Date(2017, 3, 1, 16), 'David Raluy', null)

      this.rendezvous.push(rdv1)
      this.rendezvous.push(rdv2)
    },
    computed: {
      svgWidth: function () {
        return document.querySelector('svg.agenda').getBoundingClientRect().width - 10
      },
      nbHeures: function () {
        return this.heuresOuverture[1] - this.heuresOuverture[0]
      }
    },
    components: {
      'rdv': RendezVous
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .agenda {
        width: 100%;
        overflow-y: scroll;
    }
</style>
