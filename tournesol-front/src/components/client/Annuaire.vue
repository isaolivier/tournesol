<template>
    <div>
        <div v-if="loading">Loading ...</div>
        <div v-if="error" x="50" :y="100">{{error}}</div>
        <template v-for="(client, index) in elements">
            <template v-if="client.nom">
                <client :rang="index" :client="client" :key="client.id"></client>
            </template>
            <template v-else>
                <letter :letter="client"/>
            </template>
        </template>
        <letters :model="letters"/>
    </div>
</template>

<script>
  import {ClientResource} from '../../resource/ClientResource'
  import Client from './Client.vue'
  import Letters from './Letters.vue'
  import Letter from './Letter.vue'

  export default {
    name: 'annuaire',
    data () {
      return {
        clients: [],
        elements: [],
        loading: false,
        error: null,
        currentLetter: null
      }
    },
    created () {
      this.fetchData()
    },
    computed: {
      letters: function () {
        let letters = []
        this.clients.map(client => client.nom[0]
        ).forEach(letter => letters.push(letter))

        this.currentLetter = letters[0]
        return letters
      }
    },
    components: {
      'client': Client,
      'letters': Letters,
      'letter': Letter
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
            let letters = this.letters
            let curIndexLetter = 0
            for (let client of this.clients) {
              if (letters[curIndexLetter] === client.nom.charAt(0)) {
                this.elements.push(letters[curIndexLetter])
                curIndexLetter++
              }
              this.elements.push(client)
            }
          }
        })
      }
    }

  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
