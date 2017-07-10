<template>
    <div style="height:80vh; width:100%">
        <div v-if="loading">Loading ...</div>
        <div v-if="error" x="50" :y="100">{{error}}</div>

        <svg width="100%" height="100%">
            <client v-for="(client, index) in clients" :rang="index" :client="client" :key="client.id"></client>
            <letters :model="letters"/>
        </svg>
    </div>
</template>

<script>
  import {ClientResource} from '../../resource/ClientResource'
  import Client from './Client.vue'
  import Letters from './Letters.vue'

  export default {
    name: 'annuaire',
    data () {
      return {
        clients: [],
        loading: false,
        error: null
      }
    },
    created () {
      this.fetchData()
    },
    computed: {
      letters: function () {
        let letters = new Set()
        this.clients.map(client => client.nom[0]
        ).forEach(letter => letters.add(letter))

        return letters
      }
    },
    components: {
      'client': Client,
      'letters': Letters
    },
    methods: {
      fetchData () {
        this.error = null
        this.loading = true
        let clientResource = new ClientResource()
        clientResource.findAllClient((err, result) => {
          this.loading = false
          if (err) {
            this.error = err.toString()
          } else {
            this.clients = result
          }
        })
      }
    }

  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
