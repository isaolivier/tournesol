<template>

  <div>
    Slider des dates<br/>
    <icon name="car" scale="2"></icon>
    <svg :width="fullWidth" :height="fullHeight">

      <!-- Heures -->
      <text v-for="(hour, index) in hours" x="0" :y="index * hourIntervall + 15" >{{hour}}:00</text>
      <line v-for="(hour, index) in hours" x1="50" :y1="index * hourIntervall + 10" :x2="lineWidth" :y2="index * hourIntervall + 10" style="stroke:rgb(0,0,0);stroke-width:1"/>
      <line v-for="(hour, index) in hours" x1="50" :y1="index * hourIntervall + 33" :x2="lineWidth" :y2="index * hourIntervall + 33" style="stroke:rgb(180,180,180);stroke-width:0.5"/>

      <!-- Rendez-vous -->
      <rect x="90" y="15" rx="15" ry="15" width="450" height="128" style="fill:white;stroke:lightblue;stroke-width:2;opacity:0.9" />
      <text x="100" y="40" class="fa-car">
        Alexandre Durand
      </text>

      <!-- Icône voiture bcp trop grand -->
      <!--
      <path d="M480 1088q0-66-47-113t-113-47-113 47-47 113 47 113 113 47 113-47 47-113zM516 768h1016l-89-357q-2-8-14-17.5t-21-9.5h-768q-9 0-21 9.5t-14 17.5zM1888 1088q0-66-47-113t-113-47-113 47-47 113 47 113 113 47 113-47 47-113zM2048 992v384q0 14-9 23t-23 9h-96v128q0 80-56 136t-136 56-136-56-56-136v-128h-1024v128q0 80-56 136t-136 56-136-56-56-136v-128h-96q-14 0-23-9t-9-23v-384q0-93 65.5-158.5t158.5-65.5h28l105-419q23-94 104-157.5t179-63.5h768q98 0 179 63.5t104 157.5l105 419h28q93 0 158.5 65.5t65.5 158.5z"></path>
      -->
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
          { begin: 8, end: 10 },
          { begin: 14, end: 15 }
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
