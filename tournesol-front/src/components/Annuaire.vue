<template>
    <svg width="100%">
        <client v-for="(client, index) in clients" :rang="index" :client="client" :key="client.id"></client>
        <letters :model="letters"/>
    </svg>
</template>

<script>
  import {ClientBean} from '../bean/ClientBean'
  import Client from './Client.vue'
  import Letters from './Letters.vue'

  export default {
    name: 'annuaire',
    data () {
      return {
        clients: []
      }
    },
    mounted: function () {
      let clientBean = new ClientBean()
      clientBean.prenom = 'Alex'
      clientBean.nom = 'Durand'
      this.clients.push(clientBean)

      clientBean = new ClientBean()
      clientBean.prenom = 'David'
      clientBean.nom = 'Raluy'
      this.clients.push(clientBean)
    },
    computed: {
      letters: function () {
        let letters = new Set()
        this.clients.map(client => client.nom[0]).forEach(letter => letters.add(letter))
        return letters
      }
    },
    components: {
      'client': Client,
      'letters': Letters
    }

  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
