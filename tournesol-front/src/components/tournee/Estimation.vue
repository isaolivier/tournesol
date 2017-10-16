<template>
    <div class="greybox estimation" :style="'top:'+yDebut+'px;height:'+height+'px;'">
        estimation: {{estimation.distance}},{{estimation.duration}}
    </div>
</template>

<script>
  import {entreprise} from '../../bean/EntrepriseBean'
  import Config from '../../bean/Constants'

  export default {
    name: 'rdv',
    props: {
      estimation: {
        type: Object,
        required: true
      },
      rdvs: {
        type: Array,
        required: true
      }
    },
    data () {
      return {}
    },
    computed: {
      yDebut: function () {
        // Récupérer le rdv d'avant
        let rdvSource = this.rdvs.filter(rdv => rdv.event.id === this.estimation.sourceEventId)[0]
        if (rdvSource) {
          let debut = new Date(rdvSource.event.end).getHours() - entreprise.configuration.heureOuverture.hours()
          return debut * Config.tournee.hourInterval
        }
      },
      height: function () {
        let rdvTarget = this.rdvs.filter(rdv => rdv.event.id === this.estimation.targetEventId)[0]
        if (rdvTarget) {
          let fin = new Date(rdvTarget.event.start).getHours() - entreprise.configuration.heureOuverture.hours()
          return fin * Config.tournee.hourInterval - this.yDebut
        }
      }
    }
  }
</script>

<style scoped>
.estimation {
    position: absolute;
    left: 50vw;
    right: 20px;
}

</style>
