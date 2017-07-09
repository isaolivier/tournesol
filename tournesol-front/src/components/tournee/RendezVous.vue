<template>
    <svg>
        <rect x="90" :y="yDebut" rx="15" ry="15" width="80%" :height="height"
              style="fill:white;stroke:lightblue;stroke-width:2;opacity:0.9"/>
            <text x="100" :y="yDebut + 25">{{rdv.client}}</text>

            <a href="http://isadanslesnuages.fr">
                <icon name="phone" scale="1.7" x="250px" :y="yDebut + 5"/>
            </a>

            <a href="http://isadanslesnuages.fr">
                <icon name="waze" scale="1.7" x="290px" :y="yDebut + 5"/>
            </a>
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
        let debut = this.rdv.dateDebut.getHours() - 8
        return debut * Config.tournee.hourInterval + 15
      },
      height: function () {
        let debut = this.rdv.dateDebut.getHours() - 8
        let fin = this.rdv.dateFin.getHours() - 8
        return Config.tournee.hourInterval * (fin - debut) - 10
      }
    }
  }
</script>
