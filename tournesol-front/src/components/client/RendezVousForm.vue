<template>
    <div>
        <span @click="showDialog" class="fa-stack fa-lg">
          <i class="fa fa-circle fa-stack-2x"></i>
          <i class="fa fa-calendar-plus-o fa-stack-1x fa-inverse"></i>
        </span>
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

    .fa-circle{
        color: #f25f5c;
        text-shadow: 1px 1px 3px black;
    }

</style>
