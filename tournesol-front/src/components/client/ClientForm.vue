<template>
    <div>
        <!--
        <span @click="showDialog" class="fa-stack fa-lg">>
            <i class="fa fa-pencil fa-2x"></i>
        </span>
        -->

        <span @click="showDialog">
          <i v-if="client" class="fa fa-pencil fa-2x"></i>
          <i class="fa fa-plus fa-2x" v-else></i>
        </span>

        <el-dialog :title="this.title" :visible.sync="dialogFormVisible">
            <el-form :model="form">

                <el-row :gutter="20">
                    <el-col :span="6">
                        <el-radio-group v-model="form.civilite">
                            <el-radio-button label="Mr">Mr</el-radio-button>
                            <el-radio-button label="Mme">Mme</el-radio-button>
                            <el-radio-button label="MrMme">Mr & Mme</el-radio-button>
                        </el-radio-group>
                    </el-col>

                    <el-col :span="18">
                        <el-input placeholder="Nom" v-model="form.nom"></el-input>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-input placeholder="Société" v-model="form.societe"></el-input>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-autocomplete
                                v-model="fullAdresse"
                                :fetch-suggestions="searchPlaces"
                                placeholder="Saisir une adresse"
                                @select="handlePlaceSelect"></el-autocomplete>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="4">
                        <el-input placeholder="Téléphone" v-model="form.telephone"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input placeholder="Portable" v-model="form.portable"></el-input>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="8">
                        <el-input placeholder="Mail" v-model="form.email"></el-input>
                    </el-col>
                    <el-col :span="8">
                        <div class="stars">
                            <i :class="'fa fa-star' + iconStarOff(1) + ' fa-2x'" @click="setNote(1)"></i>
                            <i :class="'fa fa-star' + iconStarOff(2) + ' fa-2x'" @click="setNote(2)"></i>
                            <i :class="'fa fa-star' + iconStarOff(3) + ' fa-2x'" @click="setNote(3)"></i>
                            <i :class="'fa fa-star' + iconStarOff(4) + ' fa-2x'" @click="setNote(4)"></i>
                            <i :class="'fa fa-star' + iconStarOff(5) + ' fa-2x'" @click="setNote(5)"></i>
                        </div>
                    </el-col>
                </el-row>

            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">Annuler</el-button>
                <el-button type="primary" @click="createClient">Créer</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
  import {ClientResource} from '../../resource/ClientResource'

  export default {
    name: 'clientForm',
    props: {
      formLabelWidth: '120px',
      client: {
        type: Object,
        required: false
      }
    },
    data () {
      return {
        title: '',
        error: null,
        dialogFormVisible: false,
        fullAdresse: '',
        form: {
          civilite: '',
          nom: '',
          societe: '',
          placeId: null,
          telephone: '',
          portable: '',
          email: '',
          note: 0
        }
      }
    },
    created () {
      if (this.client) {
        this.form = this.client
        this.form.placeId = this.client.adresse.placeId
        this.fullAdresse = this.client.adresse.numero + ' ' +
          this.client.adresse.voie + ', ' +
          this.client.adresse.commune + ' ' + this.client.adresse.codePostal

        this.title = ''
      } else {
        this.title = 'Création d\'un client'
      }
    },
    methods: {
      showDialog () {
        this.dialogFormVisible = true
      },
      iconStarOff (index) {
        if (index <= this.form.note) {
          return ''
        }
        return '-o'
      },
      setNote (note) {
        this.form.note = note
      },
      createClient () {
        let clientResource = new ClientResource()
        clientResource.createClient(this.form, (err) => {
          if (err) {
            this.error = err.toString()
          } else {
            this.dialogFormVisible = false
          }
        })
      },
      searchPlaces (queryString, cb) {
        let google = window.google || {}

        let displaySuggestions = function (predictions, status) {
          var results = []
          if (status !== google.maps.places.PlacesServiceStatus.OK) {
            cb(status)
            return
          }
          predictions.forEach(function (prediction) {
            results.push({'value': prediction.description, 'placeId': prediction.place_id})
          })
          cb(results)
        }

        if (queryString && queryString.length > 3) {
          console.log('Search places for \'' + queryString + '\'')
          let service = new google.maps.places.AutocompleteService()
          service.getPlacePredictions({input: queryString, componentRestrictions: {country: 'fr'}}, displaySuggestions)
        } else {
          cb([])
        }
      },
      handlePlaceSelect (placeId) {
        console.log(placeId)
        this.form.placeId = placeId.placeId
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

    .fa-circle {
        color: #f25f5c;
        text-shadow: 1px 1px 3px black;
    }

    .el-row {
        margin-bottom: 20px;
    }

    .stars {
        float: right;
        margin: 20px 50px 0 0;
    }

</style>
