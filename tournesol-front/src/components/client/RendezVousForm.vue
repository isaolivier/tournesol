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
                        <el-checkbox v-for="appareil in appareils" :key="appareil.id" :label="appareil.id">{{getAppareilLabel(appareil)}}</el-checkbox>
                    </el-checkbox-group>
                </el-form-item>

                <el-date-picker
                        v-model="form.date"
                        type="date"
                        placeholder="Date"
                        :picker-options="{
                          disabledDate: disabledDate
                        }">
                </el-date-picker>

                <el-time-select
                        placeholder="Heure Début"
                        v-model="form.startTime"
                        :picker-options="{
                          start: this.heureOuverture,
                          step: this.step,
                          end: this.heureFermeture
                        }">

                </el-time-select>
                <el-time-select
                        placeholder="Heure Fin"
                        v-model="form.endTime"
                        :picker-options="{
                          start: this.heureOuverture,
                          step: this.step,
                          end: this.heureFermeture,
                          minTime: this.form.startTime
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
  import moment from 'moment'
  import Constants from '../../bean/Constants'
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
    data () {
      return {
        error: null,
        dialogFormVisible: false,
        heureOuverture: Constants.rdv.heuresOuverture[0] + ':00',
        heureFermeture: Constants.rdv.heuresOuverture[1] + ':00',
        step: Constants.rdv.timeStep,
        appareils: [],
        now: moment().startOf('day'),
        form: {
          appareils: [],
          client: this.client.id,
          date: '',
          startTime: this.heureOuverture,
          endTime: ''
        }
      }
    },
    watch: {
      // Ajout du temps de rdv par défaut lors du choix de l'heure de début
      'form.startTime': function (newStartTime) {
        let m = moment(newStartTime, 'HH:mm').add(Constants.rdv.tempsRdv, 'm')
        this.form.endTime = m.format('HH:mm')
      }
    },
    methods: {
      // Calcul des dates désactivées lors du choix des dates
      disabledDate (date) {
        return this.now > moment(date)
      },

      showDialog () {
        this.dialogFormVisible = true
        this.fetchData()
      },

      // Chargement des appareils
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

      // Calcul du label affiché pour un appreil
      getAppareilLabel (appareil) {
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
