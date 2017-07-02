<template>

  <div>
    Slider des dates<br/>
    <svg :width="fullWidth" :height="fullHeight">

      <!-- Heures -->
      <text v-for="(hour, index) in hours" x="0" :y="index * hourIntervall + 15">{{hour}}:00</text>
      <line v-for="(hour, index) in hours" x1="50" :y1="index * hourIntervall + 10" :x2="lineWidth"
            :y2="index * hourIntervall + 10" style="stroke:rgb(0,0,0);stroke-width:1"/>
      <line v-for="(hour, index) in hours" x1="50" :y1="index * hourIntervall + 33" :x2="lineWidth"
            :y2="index * hourIntervall + 33" style="stroke:rgb(180,180,180);stroke-width:0.5"/>

      <!-- Rendez-vous -->
      <rect x="90" y="15" rx="15" ry="15" width="450" height="128"
            style="fill:white;stroke:lightblue;stroke-width:2;opacity:0.9"/>
      <text x="100" y="40">Alexandre Durand</text>

      <icon name="phone" scale="2" x="250px" y="18px"></icon>
      <icon name="waze" scale="2" x="290px"y="18px"></icon>

    </svg>
  </div>

</template>

<script>
  export default {
    name: 'tournee',

    data () {
      return {
        hours: [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18],
        events: [
          {begin: 8, end: 10},
          {begin: 14, end: 15}
        ],
        fullWidth: document.documentElement.clientWidth,
        fullHeight: document.documentElement.clientHeight - 200,
        lineWidth: document.documentElement.clientWidth - 200,
        hourIntervall: 46
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
