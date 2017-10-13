<template>
    <div>
        <div v-if="client" class="edit" @click="showDialog">
                <span class="clickable">
                    <i class="fa fa-pencil fa-2x"></i>
                </span>
        </div>
        <div v-else @click="showDialog">
                <span class="fa-stack fa-3x add-button">
                  <i class="fa fa-circle fa-stack-2x"></i>
                  <i class="fa fa-plus fa-stack-1x fa-inverse"></i>
                </span>
        </div>

        <el-dialog :summary="this.summary" :visible.sync="dialogFormVisible">

            <el-form :model="form" :rules="rules" label-position="top" ref="form">

                <!-- ************************************************************* -->
                <!--                            SOCIETE                            -->
                <!-- ************************************************************* -->
                <el-form-item>
                    <el-col :offset="5" :span="19">
                        <el-form-item label="Société" prop="societe">
                            <el-input placeholder="Société" v-model="form.societe"></el-input>
                        </el-form-item>
                    </el-col>
                </el-form-item>

                <!-- ************************************************************* -->
                <!--                        CIVLITE - NOM                          -->
                <!-- ************************************************************* -->

                <el-form-item>
                    <el-col :span="4">
                        <el-form-item label="Mr / Mme" prop="civlite">
                            <el-select v-model="form.civilite" placeholder="Choisir">
                                <el-option value="Mr" label="Mr"></el-option>
                                <el-option value="Mme" label="Mme"></el-option>
                                <el-option value="Mlle" label="Mlle"></el-option>
                                <el-option value="MrMme" label="Mr & Mme"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col class="line" :span="1">&nbsp;</el-col>
                    <el-col :span="19">
                        <el-form-item label="NOM Prénom" prop="nom">
                            <el-input placeholder="NOM Prénom" v-model="form.nom"></el-input>
                        </el-form-item>
                    </el-col>
                </el-form-item>


                <!-- ************************************************************* -->
                <!--                              NOTE                             -->
                <!-- ************************************************************* -->
                <el-form-item>
                    <el-col :offset="5" :span="19">
                        <el-form-item label="" prop="note">
                            <el-rate v-model="form.note" :colors="['#99A9BF', '#F7BA2A', '#FF9900']"></el-rate>
                        </el-form-item>
                    </el-col>
                </el-form-item>
                <!-- ************************************************************* -->
                <!--                           ADRESSE                             -->
                <!-- ************************************************************* -->
                <el-form-item>
                    <el-col :span="24">
                        <el-form-item label="Adresse" prop="adresse">
                            <adresse-autocomplete :adresse="getClientAdresse()"
                                                  @select="updatePlaceId"></adresse-autocomplete>
                        </el-form-item>
                    </el-col>
                </el-form-item>

                <!-- ************************************************************* -->
                <!--                           TELEPHONES                          -->
                <!-- ************************************************************* -->
                <el-form-item>
                    <el-col :span="11">
                        <el-form-item label="Téléphone" prop="telephone" class="el-form-item-label">
                            <el-input class="input-icon tel" placeholder="Téléphone"
                                      v-model="form.telephone"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col class="line" :span="2">&nbsp;</el-col>
                    <el-col :span="11">
                        <el-form-item label="Mobile" prop="mobile">
                            <el-input class="input-icon mobile" placeholder="Mobile" v-model="form.portable"></el-input>
                        </el-form-item>
                    </el-col>
                </el-form-item>

                <!-- ************************************************************* -->
                <!--                               MAIL                            -->
                <!-- ************************************************************* -->
                <el-form-item>
                    <el-col :span="24">
                        <el-form-item label="Mail" prop="mail">
                            <el-input class="input-icon mail" placeholder="Mail" v-model="form.email"></el-input>
                        </el-form-item>
                    </el-col>
                </el-form-item>

            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button type="danger" icon="delete" class="trashbin" @click="deleteClient"></el-button>
                <el-button v-if="client" type="primary" @click="updateClient">Enregistrer</el-button>
                <el-button v-else type="primary" @click="createClient">Créer</el-button>
                <el-button @click="dialogFormVisible = false">Annuler</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
  import {ClientResource} from '../../../resource/ClientResource'
  import AdresseAutoComplete from './AdresseAutoComplete.vue'

  export default {
    name: 'clientForm',
    components: {
      'adresse-autocomplete': AdresseAutoComplete
    },
    props: {
      client: {
        type: Object,
        required: false
      }
    },
    data () {
      let validateAdresse = (rule, value, callback) => {
        if (!this.form.placeId || this.form.placeId === '') {
          callback(new Error('Veuillez saisir une adresse valide'))
        } else {
          callback()
        }
      }

      let validateSteNom = (rule, value, callback) => {
        if ((this.form.societe && this.form.societe !== '') || (this.form.nom && this.form.nom !== '')) {
          callback()
        } else {
          callback(new Error('Veuillez saisir un nom de société et/ou un nom de client'))
        }
      }

      return {
        gutter: 20,
        adresse: null,
        summary: '',
        error: null,
        dialogFormVisible: false,
        form: {
          civilite: '',
          nom: '',
          societe: '',
          placeId: '',
          adresseId: null,
          telephone: '',
          portable: '',
          email: '',
          note: 0
        },
        rules: {
          nom: [{validator: validateSteNom, trigger: 'blur'}],
          adresse: [{validator: validateAdresse, trigger: 'blur'}],
          mail: [{type: 'email', message: 'Veuillez saisir une adresse mail valide'}]
        }
      }
    },
    created () {
      if (this.client) {
        this.form = {
          id: this.client.id,
          civilite: this.client.civilite,
          nom: this.client.nom,
          societe: this.client.societe,
          placeId: this.client.adresse ? this.client.adresse.placeId : null,
          adresseId: this.client.adresse ? this.client.adresse.id : null,
          telephone: this.client.telephone,
          portable: this.client.portable,
          email: this.client.email,
          note: this.client.note
        }
      } else {
        this.summary = 'Création d\'un client'
      }
    },
    methods: {
      getClientAdresse () {
        // console.log('Init client adresse ' + this.client ? this.client.adresse : null)
        return this.client ? this.client.adresse : null
      },

      showDialog () {
        this.dialogFormVisible = true
      },
      updatePlaceId (selected) {
        // console.log('PlaceId selected ' + selected.place)
        this.form.placeId = selected.place
      },
      createClient () {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            let clientResource = new ClientResource()
            clientResource.createClient(this.form, (err) => {
              if (err) {
                this.error = err.toString()
              } else {
                this.$emit('change', {'operation': 'create', 'client': this.form})
                this.dialogFormVisible = false
              }
            })
          } else {
            // console.log('Formulaire non validé')
          }
        })
      },
      updateClient () {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            let clientResource = new ClientResource()
            clientResource.updateClient(this.form, (err) => {
              if (err) {
                this.error = err.toString()
              } else {
                this.$emit('change', {'operation': 'update', 'client': this.form})
                this.dialogFormVisible = false
              }
            })
          } else {
            // console.log('Formulaire non validé')
          }
        })
      },
      deleteClient () {
        let clientResource = new ClientResource()
        clientResource.deleteClient(this.form.id, (err) => {
          if (err) {
            this.error = err.toString()
          } else {
            this.$emit('change', {'operation': 'delete', 'client': this.form})
            this.dialogFormVisible = false
          }
        })
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

    .input-icon.tel:after {
        content: '\f095';
    }

    .input-icon.mobile:after {
        content: '\f10b';
    }

    .input-icon.mail:after {
        content: '\f003';
    }

    .add-button {
        position: fixed;
        right: 50px;
        bottom: 40px;
    }

    .edit {
        float: right;
        margin-right: 10px;
    }

    .trashbin {
        float: left;
    }
</style>
