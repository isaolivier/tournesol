<template>
    <div>
        <div v-if="loading">Loading ...</div>
        <div v-if="error" x="50" :y="100">{{error}}</div>

        <client-form></client-form>
        <el-row id="search" :gutter="20">
            <el-col :span="12" :offset="12">
                <el-input class="input-search" suffix-icon="search" placeholder="Rechercher" v-model="recherche"></el-input>
            </el-col>
        </el-row>

        <template v-for="(client, index) in filteredClients">
            <template v-if="client.id">
                <client :rang="index" :client="client" :rdv="findRendezVous(client)" :key="client.id"
                        @create="updateClients" @update="updateClients" @delete="updateClients"></client>
            </template>
            <template v-else>
                <letter :letter="client" :separator="true"/>
            </template>
        </template>
        <letters :model="letters"/>
    </div>
</template>

<script>
  import {ClientResource} from '../../resource/ClientResource'
  import {RendezVousResource} from '../../resource/RendezVousResource'
  import Client from './Client.vue'
  import Letters from './letters/Letters.vue'
  import Letter from './letters/Letter.vue'
  import ClientForm from './form/ClientForm.vue'

  export default {
    name: 'annuaire',
    data () {
      return {
        recherche: null,
        clients: [],
        rdvs: [],
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
        this.clients.map(client => client.societe ? client.societe[0] : (client.nom ? client.nom[0] : '?')
        ).forEach(letter => {
          if (letter && !letters.includes(letter)) {
            letters.push(letter)
          }
        })

        this.currentLetter = letters[0]
        return letters
      },
      filteredClients: function () {
        if (this.recherche) {
          let search = this.recherche.toUpperCase()
          return this.clients.filter(client => {
            return client.nom && (client.nom.toUpperCase().includes(search) ||
              (client.societe && client.societe.toUpperCase().includes(search)) ||
              (client.telephone && client.telephone.toUpperCase().includes(search)) ||
              (client.portable && client.portable.toUpperCase().includes(search)))
          })
        } else {
          return this.visualItems
        }
      },
      // Construction des éléments visuels : clients + spéarateurs lettres
      visualItems: function () {
        let result = []
        let letters = this.letters
        let curIndexLetter = 0
        for (let client of this.clients) {
          let clientLetter = client.societe ? client.societe.charAt(0) : (client.nom ? client.nom.charAt(0) : '?')
          if (letters[curIndexLetter] === clientLetter) {
            result.push(clientLetter)
            curIndexLetter++
          }
          result.push(client)
        }
        return result
      }
    },
    components: {
      'client-form': ClientForm,
      'client': Client,
      'letters': Letters,
      'letter': Letter
    },
    methods: {
      updateClients (value) {
        this.fetchData()
      },
      findRendezVous (client) {
        return this.rdvs.find(r => r.client.id === client.id)
      },
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

        let rdvResource = new RendezVousResource()
        rdvResource.findClientRendezVous(180, (err, result) => {
          if (err) {
            this.error = err.toString()
          } else {
            this.rdvs = result
          }
        })
      }
    }

  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
    #search {
        position: fixed;
        top: 103px;
        right: 2px;
        left: 200px;
    }
</style>
