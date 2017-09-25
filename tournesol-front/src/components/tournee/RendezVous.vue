<template>
    <svg>
        <rect x="90" :y="yDebut" width="80%" :height="height" style="fill:#EEF1F6;stroke:#D1DBE5;stroke-width:1"/>
        <text x="100" :y="yDebut + 25">{{rdv.client.civilite}} {{rdv.client.nom}}</text>
        <text x="100" :y="yDebut + 50">{{rdv.event.description}}</text>

        <a v-if="rdv.client.telephone" :href="'tel:' + rdv.client.telephone">
            <icon name="phone" scale="1.7" x="450px" :y="yDebut + 5"/>
        </a>

        <a v-if="rdv.client.portable" :href="'tel:' + rdv.client.portable">
            <icon name="phone" scale="1.7" x="500px" :y="yDebut + 5"/>
        </a>

        <a v-if="rdv.event.location" :href="'waze://?q=' + rdv.event.location" style="display: inline-block">
            <icon name="waze" scale="1.7" x="550px" :y="yDebut + 5"/>
        </a>

        <text x="100" :y="yDebut + 75">{{rdv.event.location}}</text>
    </svg>
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
