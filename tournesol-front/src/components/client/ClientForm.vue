<template>
    <div>
            <div v-if="client" class="edit"  @click="showDialog">
                <span class="clickable">
                    <i class="fa fa-pencil fa-2x"></i>
                </span>
            </div>
            <span v-else class="fa-stack fa-3x add-button">
              <i class="fa fa-circle fa-stack-2x"></i>
              <i class="fa fa-plus fa-stack-1x fa-inverse"></i>
            </span>

        <el-dialog :summary="this.summary" :visible.sync="dialogFormVisible">
            <el-form>
                <el-row :gutter="20">
                    <el-col :span="5">
                        <el-select v-model="form.civilite" placeholder="Choisir">
                            <el-option value="Mr" label="Mr"></el-option>
                            <el-option value="Mme" label="Mme"></el-option>
                            <el-option value="Mlle" label="Mlle"></el-option>
                            <el-option value="MrMme" label="Mr & Mme"></el-option>
                        </el-select>
                    </el-col>

                    <el-col :span="19">
                        <el-input placeholder="NOM Prénom" v-model="form.nom"></el-input>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :offset="5" :span="19">
                        <el-input placeholder="Société" v-model="form.societe"></el-input>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col  :span="21" :offset="5">
                        <el-rate
                             v-model= "form.note"
                        :colors="['#99A9BF', '#F7BA2A', '#FF9900']">
                        </el-rate>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                        <adresse-autocomplete :adresse="adresse" @select="updatePlaceId"></adresse-autocomplete>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-input class="input-icon tel" placeholder="Téléphone" v-model="form.telephone"></el-input>
                    </el-col>
                    <el-col :span="12">
                        <el-input class="input-icon mobile" placeholder="Portable" v-model="form.portable"></el-input>
                    </el-col>
                </el-row>

                <el-row :gutter="20">
                    <el-col :span="24">
                        <el-input class="input-icon mail" placeholder="Mail" v-model="form.email"></el-input>
                    </el-col>
                </el-row>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button v-if="client" type="primary" @click="updateClient" size="large">Enregistrer</el-button>
                <el-button v-else type="primary" @click="createClient" size="large">Créer</el-button>
                <el-button @click="dialogFormVisible = false" size="large">Annuler</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
  import {ClientResource} from '../../resource/ClientResource'
  import AdresseAutoComplete from '../AdresseAutoComplete.vue'

  export default {
    name: 'clientForm',
    props: {
      formLabelWidth: '120px',
      client: {
        type: Object,
        required: false
      }
    },
    components: {
      'adresse-autocomplete': AdresseAutoComplete
    },
    data () {
      return {
        adresse: null,
        summary: '',
        error: null,
        dialogFormVisible: false,
        form: {
          civilite: '',
          nom: '',
          societe: '',
          placeId: null,
          adresseId: null,
          telephone: '',
          portable: '',
          email: '',
          note: 0
        }
      }
    },
    created () {
      if (this.client) {
        this.adresse = this.client.adresse
        this.form = {
          id: this.client.id,
          civilite: this.client.civilite,
          nom: this.client.nom,
          societe: this.client.societe,
          placeId: this.client.adresse.placeId,
          adressId: this.client.adresse.id,
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
      showDialog () {
        this.dialogFormVisible = true
      },
      updatePlaceId (selected) {
        console.log('PlaceId selected ' + selected.place)
        this.form.placeId = selected.place
      },
      iconStarOff (index) {
        if (index <= this.form.note) {
          return ''
        }
        return '-o'
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
        margin-bottom: 15px;
    }

    .input-icon:after {
        font-family: 'FontAwesome';
        position: absolute;
        right: 10px;
        top: 5px;
        font-size: 1.8em;
        color: #CCC
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

    .add-button {
        position: fixed;
        right: 50px;
        bottom: 40px;
    }

    .edit {
        float: right;
        margin-right: 10px;
    }
</style>
