<template>
    <svg>
        <client v-for="(client, index) in clients" :rang="index" :client="client"></client>
    </svg>
</template>

<script>
  import {ClientBean} from '../bean/ClientBean'
  import Client from './Client.vue'

  export default {
    name: 'annuaire',
    data () {
      return {
        clients: []
      }
    },
    mounted: function () {
      let clientBean = new ClientBean()
      clientBean.prenom = 'David'
      clientBean.nom = 'Raluy'
      this.clients.push(clientBean)
      clientBean = new ClientBean()
      clientBean.prenom = 'Alex'
      clientBean.nom = 'Durand'
      this.clients.push(clientBean)
    },
    components: {
      'client': Client
    }

  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
