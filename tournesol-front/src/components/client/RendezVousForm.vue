<template>
    <div>
        <el-button type="text" @click="showDialog">Créer un rendez-vous</el-button>

        <el-dialog title="Création d'un rendez-vous" :visible.sync="dialogFormVisible">
            <el-form :model="form">

                <el-form-item>
                    <el-checkbox-group v-model="form.appareils">
                        <el-checkbox v-for="appareil in appareils" :key="appareil.id" :label="getAppareilLabel(appareil)"></el-checkbox>
                    </el-checkbox-group>
                </el-form-item>

                <el-form-item label="Choisir une date de rendez-vous" :label-width="formLabelWidth">
                    <el-date-picker
                            v-model="form.date"
                            type="datetime"
                            placeholder="Choisir la date et l'heure">
                    </el-date-picker>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">Annuler</el-button>
                <el-button type="primary" @click="dialogFormVisible = false">Créer</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
  import {AppareilResource} from '../../resource/AppareilResource'

  export default {
    name: 'rdvForm',
    props: {
      formLabelWidth: '120px',
      client: {
        type: Object,
        required: true
      }
    },
    data () {
      return {
        dialogFormVisible: false,
        appareils: [],
        form: {
          appareils: [],
          date: ''
        }
      }
    },
    methods: {
      showDialog () {
        this.dialogFormVisible = true
        this.fetchData()
      },
      fetchData: function () {
        console.log('Fetching appareils')

        let appareilResource = new AppareilResource()
        appareilResource.findAppareils(this.client, (err, result) => {
          this.loading = false
          if (err) {
            this.error = err.toString()
          } else {
            this.appareils = result
          }
        })
      },
      getAppareilLabel (appareil) {
        return appareil.denomination + ' [' + appareil.marque + ']'
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
