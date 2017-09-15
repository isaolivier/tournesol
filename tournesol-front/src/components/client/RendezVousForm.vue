<template>
    <div>
        <span @click="showDialog" class="fa-stack fa-lg">
          <i class="fa fa-circle fa-stack-2x"></i>
          <i class="fa fa-calendar-plus-o fa-stack-1x fa-inverse"></i>
        </span>

        <el-alert v-if="dialogFormVisible" type="error" title="" :closable="false">{{error}}</el-alert>

        <el-dialog title="Création d'un rendez-vous" :visible.sync="dialogFormVisible">
            <el-form :model="form">

                <el-form-item>
                    <el-checkbox-group v-model="form.appareils">
                        <el-checkbox v-for="appareil in appareils" :key="appareil.id" :label="appareil.id">{{getAppareilLabel(appareil)}}</el-checkbox>
                    </el-checkbox-group>
                </el-form-item>

                <el-date-picker
                        v-model="form.date"
                        type="date"
                        placeholder="Date">
                </el-date-picker>

                <el-time-select
                        placeholder="Heure Début"
                        v-model="form.startTime"
                        :picker-options="{
                          start: '08:30',
                          step: '00:30',
                          end: '18:30'
                        }">

                </el-time-select>
                <el-time-select
                        placeholder="Heure Fin"
                        v-model="form.endTime"
                        :picker-options="{
                          start: '08:00',
                          step: '00:30',
                          end: '18:30',
                          minTime: startTime
                        }">

                </el-time-select>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">Annuler</el-button>
                <el-button type="primary" @click="createRendezVous">Créer</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
  import {AppareilResource} from '../../resource/AppareilResource'
  import {RendezVousResource} from '../../resource/RendezVousResource'

  export default {
    name: 'rdvForm',
    props: {
      formLabelWidth: '120px',
      client: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        error: null,
        dialogFormVisible: false,
        appareils: [],
        form: {
          appareils: [],
          client: this.client.id,
          date: '',
          startTime: '',
          endTime: ''
        }
      }
    },
    methods: {
      showDialog() {
        this.dialogFormVisible = true
        this.fetchData()
      },
      fetchData: function () {
        console.log('Fetching appareils')

        let appareilResource = new AppareilResource()
        appareilResource.findAppareils(this.client, (err, result) => {
          if (err) {
            this.error = err.toString()
          } else {
            this.appareils = result
          }
        })
      },
      getAppareilLabel(appareil) {
        return appareil.denomination + ' [' + appareil.marque + ']'
      },
      createRendezVous () {
        let rdvResource = new RendezVousResource()
        rdvResource.createRendezVous(this.form, (err) => {
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
