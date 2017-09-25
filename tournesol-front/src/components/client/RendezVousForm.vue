<template>
    <div>
        <span @click="showDialog" class="fa-stack fa-lg">
          <i class="fa fa-circle fa-stack-2x"></i>
          <i class="fa fa-calendar-plus-o fa-stack-1x fa-inverse"></i>
        </span>

        <el-dialog summary="Création d'un rendez-vous" :visible.sync="dialogFormVisible">
            <el-form :model="form" label-position="top" label-width="120px">
                <el-form-item label="Date" prop="event.date">
                        <el-date-picker v-model="form.event.date" type="date" placeholder="Date"
                                :picker-options="{
                                  disabledDate: disabledDate
                                }" style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="Heure">
                    <el-col :span="11">
                        <el-time-select v-model="form.event.startTime" placeholder="Heure Début"
                                :picker-options="{
                                  start: this.heureOuverture,
                                  step: this.step,
                                  end: this.heureFermeture
                                }" style="width: 100%;"></el-time-select>
                    </el-col>
                    <el-col class="line" :span="2">&nbsp;</el-col>
                    <el-col :span="11">
                        <el-time-select v-model="form.event.endTime" placeholder="Heure Fin"
                                :picker-options="{
                                  start: this.heureOuverture,
                                  step: this.step,
                                  end: this.heureFermeture,
                                  minTime: this.form.startTime
                                }" style="width: 100%;"></el-time-select>
                    </el-col>
                </el-form-item>
                <el-form-item
                        label="Titre"
                        prop="event.summary">
                    <el-input placeholder="Intitulé du rendez-vous" v-model="form.event.summary"></el-input>
                </el-form-item>
                <el-form-item
                        label="Détail"
                        prop="event.description">
                    <el-input type="textarea" placeholder="Détails" v-model="form.event.description"></el-input>
                </el-form-item>
                <el-form-item
                        label="Adresse"
                        prop="form.event.description">
                    <adresse-autocomplete :adresse="adresse" @select="updateLocation" @fullAddress="initLocation"></adresse-autocomplete>
                </el-form-item>
                <el-form-item v-if="appareils.length > 0" label="Appareil" prop="appareils">
                    <el-checkbox-group v-model="form.appareils">
                        <el-checkbox v-for="appareil in appareils" :key="appareil.id" :label="appareil.id">
                            {{appareil.denomination + ' [' + appareil.marque + ']'}}
                        </el-checkbox>
                    </el-checkbox-group>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="danger" icon="delete" class="trashbin"></el-button>
                <el-button @click="dialogFormVisible = false">Annuler</el-button>
                <el-button type="primary" @click="createRendezVous">Créer</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
  import moment from 'moment'
  import Constants from '../../bean/Constants'
  import AdresseAutoComplete from '../AdresseAutoComplete.vue'
  import {AppareilResource} from '../../resource/AppareilResource'
  import {RendezVousResource} from '../../resource/RendezVousResource'

  export default {
    name: 'rdvForm',
    components: {
      'adresse-autocomplete': AdresseAutoComplete
    },
    props: {
      formLabelWidth: '120px',
      client: {
        type: Object,
        required: true
      }
    },
    data () {
      return {
        adresse: this.client.adresse,
        error: null,
        dialogFormVisible: false,
        heureOuverture: Constants.rdv.heuresOuverture[0] + ':00',
        heureFermeture: Constants.rdv.heuresOuverture[1] + ':00',
        date: '',
        step: Constants.rdv.timeStep,
        appareils: [],
        now: moment().startOf('day'),
        form: {
          appareils: [],
          client: this.client.id,
          event: {
            id: '',
            date: '',
            startTime: this.heureOuverture,
            endTime: '',
            summary: '',
            description: '',
            location: '',
            status: null
          }
        }
      }
    },
    created () {
      if (!this.form.event.id) {
        if (this.client.nom && this.client.civilite) {
          this.form.event.summary = this.client.civilite + ' ' + this.client.nom
        }
      }
    },
    watch: {
      // Ajout du temps de rdv par défaut lors du choix de l'heure de début
      'form.event.startTime': function (newStartTime) {
        let m = moment(newStartTime, 'HH:mm').add(Constants.rdv.tempsRdv, 'm')
        this.form.event.endTime = m.format('HH:mm')
      },
      date: function (newDate) {
        this.form.event.date = moment(newDate).format(Constants.rdv.dateFormat)
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

      updateLocation (selected) {
        // console.log('Place selected ' + selected.value)
        this.form.event.location = selected.value
      },
      initLocation (location) {
        this.form.event.location = location
      },
      // Chargement des appareils
      fetchData: function () {
        // console.log('Fetching appareils')

        let appareilResource = new AppareilResource()
        appareilResource.findAppareils(this.client, (err, result) => {
          if (err) {
            this.error = err.toString()
          } else {
            this.appareils = result
          }
        })
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

    .trashbin{
        float: left;
    }
</style>
