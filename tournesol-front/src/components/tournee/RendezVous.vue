<template>
    <div class="greybox rdv" :style="'top:'+yDebut+'px;height:'+height+'px;'">
        {{rdv.event.start}}
        {{rdv.event.end}}
        <div v-if="rdv.client">{{rdv.client.civilite}} {{rdv.client.nom}}</div>
        <div v-if="rdv.client">{{rdv.event.description}}</div>

        <a v-if="rdv.client && rdv.client.telephone" :href="'tel:' + rdv.client.telephone">
            <i class="fa fa-phone"></i>
        </a>

        <a v-if="rdv.client && rdv.client.portable" :href="'tel:' + rdv.client.portable">
            <i class="fa fa-phone"></i>
        </a>

        <a v-if="rdv.event.location" :href="'waze://?q=' + rdv.event.location" style="display: inline-block">
            waze
        </a>

        <span>{{rdv.event.location}}</span>
    </div>
</template>

<script>
  import Config from '../../bean/Constants'

  export default {
    name: 'rdv',
    props: {
      rdv: {
        type: Object,
        required: true
      }
    },
    data () {
      return {}
    },
    computed: {
      yDebut: function () {
        let debut = new Date(this.rdv.event.start).getHours() - 8
        return debut * Config.tournee.hourInterval + 15
      },
      height: function () {
        let debut = new Date(this.rdv.event.start).getHours() - 8
        let fin = new Date(this.rdv.event.end).getHours() - 8
        return Config.tournee.hourInterval * (fin - debut) - 10
      }
    }
  }
</script>

<style scoped>
.rdv {
    position: absolute;
    left: 50px;
    right: 10vw;
}

</style>
