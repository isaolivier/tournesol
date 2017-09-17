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

                <el-form-item>
                    <el-radio-group v-model="form.civilite">
                        <el-radio-button label="Mr">Mr</el-radio-button>
                        <el-radio-button label="Mme">Mme</el-radio-button>
                        <el-radio-button label="MrMme">Mr & Mme</el-radio-button>
                    </el-radio-group>
                </el-form-item>

                <el-form-item>
                    <el-input placeholder="Nom" v-model="form.nom"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="Société" v-model="form.societe"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-input placeholder="Adresse" v-model="form.adresse.adresse"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="Code Postal" v-model="form.adresse.codePostal"
                              type="number" maxlength="5" size="small"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input placeholder="Localité" v-model="form.adresse.commune"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-input placeholder="Téléphone" v-model="form.telephone"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-input placeholder="Portable" v-model="form.portable"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-input placeholder="Mail" v-model="form.email"></el-input>
                </el-form-item>

                <el-form-item>
                    <i :class="iconStarOnOff(1)" @click="setNote(1)"></i>
                    <i :class="iconStarOnOff(2)" @click="setNote(2)"></i>
                    <i :class="iconStarOnOff(3)" @click="setNote(3)"></i>
                    <i :class="iconStarOnOff(4)" @click="setNote(4)"></i>
                    <i :class="iconStarOnOff(5)" @click="setNote(5)"></i>
                </el-form-item>
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
        form: {
          civilite: '',
          nom: '',
          societe: '',
          adresse: {
            adresse: '',
            codePostal: '',
            commune: ''
          },
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
        this.title = ''
      } else {
        this.title = 'Création d\'un client'
      }
    },
    methods: {
      showDialog () {
        this.dialogFormVisible = true
      },
      iconStarOnOff (index) {
        if (index <= this.form.note) {
          return 'el-icon-star-on'
        }
        return 'el-icon-star-off'
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

</style>
