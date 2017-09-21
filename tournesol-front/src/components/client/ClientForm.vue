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
                    <el-col :span="3">
                        <el-select v-model="form.civilite" placeholder="Choisir">
                            <el-option value="Mr" label="Mr"></el-option>
                            <el-option value="Mme" label="Mme"></el-option>
                            <el-option value="Mlle" label="Mlle"></el-option>
                            <el-option value="MrMme" label="Mr & Mme"></el-option>
                        </el-select>
                    </el-col>

                    <el-col :span="15"><el-input placeholder="NOM Prénom" v-model="form.nom"></el-input></el-col>

                    <el-col :span="6">
                        <div class="stars">
                            <i v-for="i in 5" :class="{'fa  fa-2x': true, 'fa-star':(i <= form.note), 'fa-star-o':(i > form.note)}" @click="setNote(i)"></i>
                        </div>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="18"><el-input placeholder="Société" v-model="form.societe"></el-input></el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="18">
                        <el-autocomplete
                                class="input-icon address"
                                v-model="fullAdresse"
                                :fetch-suggestions="searchPlaces"
                                placeholder="Saisir une adresse"
                                @select="handlePlaceSelect"></el-autocomplete>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="4"><el-input class="input-icon tel" placeholder="Téléphone" v-model="form.telephone"></el-input></el-col>
                    <el-col :span="4"><el-input class="input-icon mobile" placeholder="Portable" v-model="form.portable"></el-input></el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="8"><el-input class="input-icon mail" placeholder="Mail" v-model="form.email"></el-input></el-col>
                </el-row>

            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">Annuler</el-button>
                <el-button v-if="client" type="primary" @click="updateClient">Enregistrer</el-button>
                <el-button v-else type="primary" @click="createClient">Créer</el-button>
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
        this.form = {
          civilite: this.client.civilite,
          nom: this.client.nom,
          societe: this.client.societe,
          placeId: this.client.adresse.placeId,
          telephone: this.client.telephone,
          portable: this.client.portable,
          email: this.client.email,
          note: this.client.note
        }

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
      updateClient () {
        let clientResource = new ClientResource()
        clientResource.updateClient(this.form, (err) => {
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
        margin-right: 40px;
    }

    .input-icon:after {
        font-family: 'FontAwesome';
        position: absolute;
        right: 10px;
        top: 5px;
        font-size: 1.8em;
    }

    .input-icon.tel:after {
        content: '\f095';
    }

    .input-icon.mobile:after {
        content: '\f10b';
    }

    .input-icon.address:after {
        content: '\f041';
    }

    .input-icon.mail:after {
        content: '\f003';
    }

    .el-autocomplete {
        width: 100%;
    }

</style>
