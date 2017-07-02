<template>

  <div>
    Slider des dates<br/>

    <svg :width="fullWidth" :height="fullHeight">

      <!-- Heures -->
      <text v-for="(hour, index) in hours" x="0" :y="index * hourIntervall + 15">{{hour}}:00</text>

      <line v-for="(hour, index) in hours" x1="50" :x2="lineWidth"
            :y1="index * hourIntervall + 10"
            :y2="index * hourIntervall + 10"
            style="stroke:rgb(0,0,0);stroke-width:1"/>

      <line v-for="(hour, index) in hours" x1="50" :x2="lineWidth"
            :y1="index * hourIntervall + 10 + (hourIntervall/2)"
            :y2="index * hourIntervall + 10 + (hourIntervall/2)"
            style="stroke:rgb(180,180,180);stroke-width:0.5"/>

      <!-- Rendez-vous -->
      <rdv :hourIntervall="hourIntervall" nom="Alexandre Durand" debut="1" fin="3"></rdv>
      <rdv :hourIntervall="hourIntervall" nom="David Raluy" debut="6" fin="7"></rdv>
    </svg>
  </div>

</template>

<script>
  import RendezVous from './RendezVous.vue'

  export default {
    name: 'tournee',

    data () {
      return {
        hours: [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18],
        fullWidth: window.innerWidth,
        fullHeight: 1000
      }
    },
    computed: {
      lineWidth: function () {
        return this.fullWidth - 100
      },
      hourIntervall: function () {
        return (this.fullHeight - 150) / 11
      }
    },
    methods: {
      handleWindowResize (event) {
        this.fullWidth = event.currentTarget.innerWidth
        this.fullHeight = event.currentTarget.innerHeight
      }
    },
    beforeDestroy: function () {
      window.removeEventListener('resize', this.handleWindowResize)
    },
    mounted () {
      window.addEventListener('resize', this.handleWindowResize)
    },
    components: {
      'rdv': RendezVous
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
