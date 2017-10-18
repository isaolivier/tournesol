<template>
    <div class="segment" :style="'top:'+yDebut+'px;height:'+height+'px;'">
        <div class="bubble greybox">{{segment.distance}}<br/>{{segment.duration}}</div>
        <bubble-line></bubble-line>
    </div>
</template>

<script>
  import {entreprise} from '../../bean/EntrepriseBean'
  import Config from '../../bean/Constants'
  import BubbleLine from './BubbleLine.vue'

  export default {
    name: 'segment',
    props: {
      segment: {
        type: Object,
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
        let debut = new Date(this.segment.start).getHours() - entreprise.configuration.heureOuverture.hours()
        return debut * Config.tournee.hourInterval - 10
      },
      height: function () {
        let fin = new Date(this.segment.end).getHours() - entreprise.configuration.heureOuverture.hours()
        let computedHeight = fin * Config.tournee.hourInterval - this.yDebut + 30
        if (computedHeight < 40) {
          computedHeight = 40
        }
        return computedHeight
      }
    }
  }
</script>

<style scoped>
    .segment {
        position: absolute;
        left: 80vw;
        right: 20px;
    }

    .bubble {
        position: absolute;
        left: 20px;
        top: 50%;
        transform: translateY(-50%);
        padding: 10px;
    }
</style>
