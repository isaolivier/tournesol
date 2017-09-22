<template>
    <div>
        <div v-if="loading">Loading ...</div>
        <div v-if="error" x="50" :y="100">{{error}}</div>

        <client-form></client-form>
        <el-row :gutter="20">
            <el-col :span="12" :offset="6">
                <el-input  class="input-search" icon="search" placeholder="Rechercher" v-model="recherche"></el-input>
            </el-col>
        </el-row>

        <template v-for="(client, index) in filteredClients">
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
  import ClientForm from './ClientForm.vue'

  export default {
    name: 'annuaire',
    data () {
      return {
        recherche: null,
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
      },
      filteredClients: function () {
        if (this.recherche) {
          let search = this.recherche.toUpperCase()
          return this.clients.filter(client => {
            return client.nom.toUpperCase().includes(search) ||
              (client.telephone && client.telephone.toUpperCase().includes(search)) ||
              (client.portable && client.portable.toUpperCase().includes(search))
          })
        } else {
          return this.elements
        }
      }
    },
    components: {
      'client-form': ClientForm,
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
