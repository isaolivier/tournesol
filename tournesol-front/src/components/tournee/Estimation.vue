<template>
    <div class="estimation" :style="'top:'+yDebut+'px;height:'+height+'px;'" >
        <div class="bubble greybox">{{estimation.distance}}<br/>{{estimation.duration}}</div>
        <bubble-line></bubble-line>
    </div>
</template>

<script>
  import {entreprise} from '../../bean/EntrepriseBean'
  import Config from '../../bean/Constants'
  import BubbleLine from './BubbleLine.vue'

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
    components: {
      'bubble-line': BubbleLine
    },
    computed: {
      yDebut: function () {
        // Récupérer le rdv d'avant
        let rdvSource = this.rdvs.filter(rdv => rdv.event.id === this.estimation.sourceEventId)[0]
        if (rdvSource) {
          let debut = new Date(rdvSource.event.end).getHours() - entreprise.configuration.heureOuverture.hours()
          return debut * Config.tournee.hourInterval - 10
        }
      },
      height: function () {
        let rdvTarget = this.rdvs.filter(rdv => rdv.event.id === this.estimation.targetEventId)[0]
        if (rdvTarget) {
          let fin = new Date(rdvTarget.event.start).getHours() - entreprise.configuration.heureOuverture.hours()
          let computedHeight = fin * Config.tournee.hourInterval - this.yDebut + 30
          if (computedHeight < 40) {
            computedHeight = 40
          }
          return computedHeight
        }
      }
    }
  }
</script>

<style scoped>
.estimation {
    position: absolute;
    left: 80vw;
    right: 20px;
}

.bubble {
    position:absolute;
    left: 20px;
    top: 50%;
    transform: translateY(-50%);
    padding: 10px;
}
</style>
