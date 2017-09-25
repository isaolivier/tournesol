<template>
    <div>
        <span @click="showDialog" class="fa-stack fa-lg">
          <i class="fa fa-circle fa-stack-2x"></i>
          <i class="fa fa-calendar-plus-o fa-stack-1x fa-inverse"></i>
        </span>

        <el-dialog summary="Création d'un rendez-vous" :visible.sync="dialogFormVisible" v-on:close="rdvClose">
            <el-row>
                <el-col :offset="6" :span="12">
                    <el-steps space="100%" :active="etape" finish-status="success">
                        <el-step title="Choix d'une date"></el-step>
                        <el-step title="Détails"></el-step>
                    </el-steps>
                </el-col>
            </el-row>

            <el-form :model="form" label-position="top" label-width="120px">
                <el-form-item v-if="etape === 1" label="Date" prop="event.date">
                    <el-date-picker v-model="date" type="date" placeholder="Date"
                                    :picker-options="{
                                  disabledDate: disabledDate
                                }" style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item  v-if="etape > 1" label="Heure"  prop="event.startTime">
                    <el-col :span="11">
                        <el-time-select v-model="form.event.startTime" placeholder="Heure Début" @change="updateEndTime"
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
                <el-form-item  v-if="etape > 1"
                        label="Titre"
                        prop="event.summary">
                    <el-input placeholder="Intitulé du rendez-vous" v-model="form.event.summary"></el-input>
                </el-form-item>
                <el-form-item v-if="etape > 1"
                        label="Détail"
                        prop="event.description">
                    <el-input type="textarea" placeholder="Détails" v-model="form.event.description"></el-input>
                </el-form-item>
                <el-form-item  v-if="etape > 1"
                        label="Adresse"
                        prop="event.description">
                    <adresse-autocomplete :adresse="adresse" @select="updateLocation"
                                          @fullAddress="initLocation"></adresse-autocomplete>
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
                <template v-if="etape === 2">
                    <el-button @click="dialogFormVisible = false">Annuler</el-button>
                    <el-button type="primary" @click="createRendezVous">Créer</el-button>
                </template>
                <template v-else>
                    <el-button @click="etapeSuivante" type="primary" icon="arrow-right">Suivant</el-button>
                </template>
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
        etape: 1,
        adresse: this.client.adresse,
        error: null,
        dialogFormVisible: false,
        heureOuverture: Constants.rdv.heuresOuverture[0] + ':00',
        heureFermeture: Constants.rdv.heuresOuverture[1] + ':00',
        date: '',
        step: Constants.rdv.timeStep,
        appareils: [],
        propositions: [],
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
            latitude: null,
            longitude: null,
            status: null
          }
        }
      }
    },
    created () {
      if (!this.form.event.id) {
        let civilite = this.client.civilite ? this.client.civilite + ' ' : ''
        let nom = this.client.nom ? this.client.nom : ''
        this.form.event.summary = civilite + nom
      }
    },
    methods: {
      // Calcul des dates désactivées lors du choix des dates
      disabledDate (date) {
        return this.now > moment(date)
      },

      // Ajout du temps de rdv par défaut lors du choix de l'heure de début
      updateEndTime () {
        if (this.form.event.startTime) {
          let m = moment(this.form.event.startTime, 'HH:mm').add(Constants.rdv.tempsRdv, 'm')
          this.form.event.endTime = m.format('HH:mm')
        } else {
          this.form.event.endTime = null
        }
      },

      showDialog () {
        this.dialogFormVisible = true
        this.fetchData()
        this.findPropositionsRdv()
      },

      updateLocation (selected) {
        // console.log('Place selected ' + selected.value)
        this.form.event.location = selected.value
      },
      initLocation (location) {
        this.form.event.location = location
      },
      etapeSuivante () {
        this.etape++
      },
      rdvClose () {
        this.etape = 1
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

      findPropositionsRdv: function () {
        let rdvResource = new RendezVousResource()
        rdvResource.findPropositionRendezVous(Constants.rdv.searchDays, Constants.rdv.searchDistance, this.client.adresse.id, (err, result) => {
          if (err) {
            this.error = err.toString()
          } else {
            this.propositions = result
          }
        })
      },

      createRendezVous () {
        this.form.event.date = moment(this.date).format(Constants.rdv.dateFormat)
        this.form.event.latitude = this.client.adresse.latitude
        this.form.event.longitude = this.client.adresse.longitude

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
