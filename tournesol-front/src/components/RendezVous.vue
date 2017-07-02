<template>
  <svg width="800" height="800">

    <rect x="90" :y="yDebut" rx="15" ry="15" width="450" :height="height"
          style="fill:white;stroke:lightblue;stroke-width:2;opacity:0.9"/>
    <text x="100" :y="yDebut + 25">{{nom}}</text>

    <a href="http://isadanslesnuages.fr">
      <icon name="phone" scale="1.7" x="250px" :y="yDebut + 5"/>
    </a>

    <a href="http://isadanslesnuages.fr">
      <icon name="waze" scale="1.7" x="290px" :y="yDebut + 5"/>
    </a>
  </svg>
</template>

<script>
  export default {
    name: 'rdv',
    props: ['hourIntervall', 'nom', 'debut', 'fin'],
    data () {
      return {
      }
    },
    computed: {
      yDebut: function () {
        return this.debut * this.hourIntervall + 15
      },
      height: function () {
        return this.hourIntervall * (this.fin - this.debut) - 10
      }
    }
  }
</script>
