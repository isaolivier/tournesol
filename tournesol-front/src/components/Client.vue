<template>
    <svg>
        <rect x="10" :y="(rang) * 50 + 25" width="90%" height="40" fill="white" stroke="#765373"/>
        <text x="50" :y="(rang + 1) * 50">{{client.nom}}</text>
        <text x="150" :y="(rang + 1) * 50">{{client.prenom}}</text>
    </svg>
</template>

<script>
  // import {ClientBean} from '../bean/ClientBean'

  export default {
    name: 'annuaire',
    props: {
      client: {
        type: Object,
        required: true
      },
      rang: {
        type: Number,
        required: true
      }
    },
    data () {
      return {}
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
