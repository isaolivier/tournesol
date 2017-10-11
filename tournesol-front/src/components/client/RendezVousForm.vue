<template>
    <div>
        <span @click="showDialog" class="fa-stack fa-lg">
          <i class="fa fa-circle fa-stack-2x"></i>
          <i class="fa fa-calendar-plus-o fa-stack-1x fa-inverse"></i>
        </span>

        <el-dialog summary="Création d'un rendez-vous" :visible.sync="dialogFormVisible" v-on:close="rdvClose">

            <!-- ************************************************************* -->
            <!--                             ETAPES                            -->
            <!-- ************************************************************* -->
            <el-row>
                <el-col :offset="6" :span="12">
                    <el-steps space="100%" :active="etape" finish-status="success">
                        <el-step title="Choix d'une date"></el-step>
                        <el-step title="Détails"></el-step>
                    </el-steps>
                </el-col>
            </el-row>

            <!-- ************************************************************* -->
            <!--                             ENTETE                            -->
            <!-- ************************************************************* -->
            Nom: {{this.client.nom}}
            <el-rate class="note" v-model="client.note" disabled disabled-void-color="#CCCCCC"></el-rate>


            <el-form v-if="etape === 1" :model="form" :rules="rules" label-position="top" label-width="120px" ref="form1">

                <!-- ************************************************************* -->
                <!--                             ADRESSE                           -->
                <!-- ************************************************************* -->
                <el-form-item label="Adresse" prop="placeId">
                    <adresse-autocomplete :adresse="client.adresse" @select="updateLocation"></adresse-autocomplete>
                </el-form-item>

                <!-- ************************************************************* -->
                <!--                      DUREE PREVUE                             -->
                <!-- ************************************************************* -->
                <el-form-item label="Durée prévue du rendez-vous">
                    <el-time-select v-model="duree"
                                    :picker-options="{ start: '0:30', step: '00:30', end: '8:00' }"
                                    placeholder="Durée prévue">
                    </el-time-select>
                </el-form-item>

                <!-- ************************************************************* -->
                <!--                              DATE                             -->
                <!-- ************************************************************* -->
                <el-form-item v-if="propositions.length > 0">
                    <el-radio class="radio" v-model="choixDePropositions" label="proposition">Meilleures dates
                        possibles dans un rayon de
                    </el-radio>
                    <el-select :disabled="this.choixDePropositions === 'saisie_libre'" v-model="rayon"
                               placeholder="Select" size="small">
                        <el-option
                                v-for="item in [{'value1':'50', 'label':'50km'}]"
                                :key="item.value1"
                                :label="item.label"
                                :value="item.value1">
                        </el-option>
                    </el-select>
                    <proposition-date v-for="proposition in propositions" :key="proposition.date"
                                      :proposition="proposition"></proposition-date>
                </el-form-item>
                <el-form-item :disabled="true" prop="event.date">
                    <el-radio v-if="propositions.length > 0" class="radio" v-model="choixDePropositions" label="saisie_libre">
                        Choisir une date
                    </el-radio>
                    <el-date-picker :disabled="this.choixDePropositions === 'proposition'" v-model="form.event.date"
                                    type="date" placeholder="Date"
                                    :picker-options="{ disabledDate: disabledDate }"
                                    style="width: 100%;"></el-date-picker>
                </el-form-item>
            </el-form>


            <el-form v-if="etape === 2" :model="form" :rules="rules" label-position="top" label-width="120px" ref="form2">
                <!-- ************************************************************* -->
                <!--                       HEURES DEBUT / FIN                      -->
                <!-- ************************************************************* -->
                <el-form-item>
                    <el-col :span="11">
                        <el-form-item prop="event.startTime" label="Heure de début" required>
                            <el-time-select v-model="form.event.startTime" placeholder="Heure Début"
                                            @change="updateEndTime"
                                            :picker-options="{ start: heureOuverture, step: step, end: heureFermeture }"
                                            style="width: 100%;"></el-time-select>
                        </el-form-item>
                    </el-col>
                    <el-col class="line" :span="2">&nbsp;</el-col>
                    <el-col :span="11">
                        <el-form-item prop="event.endTime" label="Heure de fin" required>
                            <el-time-select v-model="form.event.endTime" placeholder="Heure Fin"
                                            :picker-options="{ start: heureOuverture, step: step, end: heureFermeture, minTime: this.form.startTime }"
                                            style="width: 100%;"></el-time-select>
                        </el-form-item>
                    </el-col>
                </el-form-item>
                <!-- ************************************************************* -->
                <!--                       INTITULE RDV                            -->
                <!-- ************************************************************* -->
                <el-form-item label="Titre" prop="summary">
                    <el-input placeholder="Intitulé du rendez-vous" v-model="form.event.summary"></el-input>
                </el-form-item>
                <!-- ************************************************************* -->
                <!--                        DETAILS RDV                            -->
                <!-- ************************************************************* -->
                <el-form-item label="Détail" prop="description">
                    <el-input type="textarea" placeholder="Détails" v-model="form.event.description"></el-input>
                </el-form-item>
                <el-form-item v-if="form.appareils.length > 0" label="Appareil" prop="appareils">
                    <el-checkbox-group v-model="form.appareils">
                        <el-checkbox v-for="appareil in model.appareils" :key="appareil.id" :label="appareil.id">
                            {{appareil.denomination + ' [' + appareil.marque + ']'}}
                        </el-checkbox>
                    </el-checkbox-group>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <template v-if="etape === 1">
                    <el-button @click="etapeSuivante" type="primary" icon="arrow-right">Suivant</el-button>
                    <el-button @click="dialogFormVisible = false">Annuler</el-button>
                </template>
                <template v-else>
                    <el-button @click="etape = 1" icon="arrow-left">Précédent</el-button>
                    <el-button type="primary" @click="createRendezVous">Créer</el-button>
                    <el-button @click="dialogFormVisible = false">Annuler</el-button>
                </template>
            </span>
        </el-dialog>
    </div>
</template>

<script>
  import {entreprise} from '../../bean/EntrepriseBean'
  import moment from 'moment'
  import AdresseAutoComplete from '../AdresseAutoComplete.vue'
  import {RendezVousResource} from '../../resource/RendezVousResource'
  import PropositionDate from './PropositionDate.vue'

  export default {
    name: 'rdvForm',
    components: {
      'adresse-autocomplete': AdresseAutoComplete,
      'proposition-date': PropositionDate
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
        error: null,
        dialogFormVisible: false,
        propositions: [],
        rayon: entreprise.configuration.searchDistance,
        duree: moment('00:00', 'HH:mm').add(entreprise.configuration.tempsRdv, 'm').format('HH:mm'),
        choixDePropositions: 'saisie_libre',
        form: {
          placeId: null,
          appareils: [],
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
        },
        rules: {
          placeId: [{required: true, message: 'Veuillez saisir une adresse valide', trigger: 'change'}],
          'event.date': [{type: 'date', required: true, message: 'Veuillez saisir une date', trigger: 'change'}],
          'event.startTime': [{required: true, message: 'Veuillez saisir une heure de début', trigger: 'change'}],
          'event.endTime': [{required: true, message: 'Veuillez saisir une heure de fin', trigger: 'change'}]
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
    computed: {
      now: function () {
        return moment().startOf('day')
      },
      heureOuverture: function () {
        return entreprise.configuration.heureOuverture
      },
      heureFermeture: function () {
        return entreprise.configuration.heureFermeture
      },
      step: function () {
        return entreprise.configuration.timeStep
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
          let m = moment(this.form.event.startTime, 'HH:mm')
            .add({hours: this.duree.split(':')[0], minutes: this.duree.split(':')[1]})
          this.form.event.endTime = m.format('HH:mm')
        } else {
          this.form.event.endTime = null
        }
      },

      showDialog () {
        this.dialogFormVisible = true
      },

      updateLocation (selected) {
        // console.log('Place selected ' + selected.value)
        this.form.event.location = selected.value
        if (selected.place !== this.form.placeId) {
          this.form.placeId = selected.place
          this.findPropositionsRdv()
        }
      },
      etapeSuivante () {
        this.$refs['form1'].validate((valid) => {
          if (valid) {
            this.etape++
          } else {
            // console.log('Formulaire non validé')
          }
        })
      },
      rdvClose () {
        this.etape = 1
      },
      findPropositionsRdv: function () {
        if (this.form.placeId === null) {
          this.propositions = []
        } else {
          let rdvResource = new RendezVousResource()
          rdvResource.findPropositionRendezVous(entreprise.configuration.searchDays, this.rayon, this.form.placeId, this.client.adresse.id, (err, result) => {
            if (err) {
              this.error = err.toString()
            } else {
              this.propositions = result
              this.initDateChoice()
            }
          })
        }
      },
      initDateChoice () {
        let hasPropositions = this.propositions.length > 0
        if (hasPropositions) {
          this.choixDePropositions = 'proposition'
        } else {
          this.choixDePropositions = 'saisie_libre'
        }
      },
      createRendezVous () {
        this.$refs['form2'].validate((valid) => {
          if (valid) {
            let rdvResource = new RendezVousResource()
            rdvResource.createRendezVous(this.form, this.client, (err) => {
              if (err) {
                this.error = err.toString()
              } else {
                this.dialogFormVisible = false
              }
            })
          } else {
            // console.log('Formulaire non validé')
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
