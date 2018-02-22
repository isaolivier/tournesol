<template>
    <div>
        <span v-if="rdv" @click="showDialog" class="fa-stack fa-lg">
          <i class="fa fa-circle fa-circle-edit fa-stack-2x"></i>
          <i class="fa fa-calendar-check-o fa-stack-1x fa-inverse"></i>
        </span>
        <span v-else @click="showDialog" class="fa-stack fa-lg">
          <i class="fa fa-circle fa-circle-add fa-stack-2x"></i>
          <i class="fa fa-calendar-plus-o fa-stack-1x fa-inverse"></i>
        </span>

        <el-dialog summary="Création d'un rendez-vous" :visible.sync="dialogFormVisible" v-on:close="rdvClose">

            <!-- ************************************************************* -->
            <!--                             ETAPES                            -->
            <!-- ************************************************************* -->
            <el-row>
                <el-col :offset="7" :span="16">
                    <el-steps space="100%" :active="etape" finish-status="success">
                        <el-step title="Choix d'une date"></el-step>
                        <el-step title="Détails"></el-step>
                    </el-steps>
                </el-col>
            </el-row>

            <!-- ************************************************************* -->
            <!--                             ENTETE                            -->
            <!-- ************************************************************* -->
            <span class="client-name">{{this.client.nom}}</span>
            <el-rate class="note" v-model="client.note" disabled disabled-void-color="#CCCCCC"></el-rate>


            <el-form v-if="etape === 1" :model="form" label-position="left" label-width="140px"
                     ref="form1">

                <!-- ************************************************************* -->
                <!--                             ADRESSE                           -->
                <!-- ************************************************************* -->
                <el-form-item label="Adresse" prop="placeId"
                    :rules="{required: true, message: 'Veuillez saisir une adresse valide'}">

                    <adresse-autocomplete :adresse="client.adresse" @select="updateLocation"></adresse-autocomplete>

                </el-form-item>

                <!-- ************************************************************* -->
                <!--                      DUREE PREVUE                             -->
                <!-- ************************************************************* -->
                <el-form-item label="Durée prévue">
                    <el-col :span="3">
                        <el-select v-model="duree">
                            <el-option v-for="item in durees" :key="item" :label="item" :value="item"></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>

                <!-- ************************************************************* -->
                <!--                     PROPOSITIONS DATE                         -->
                <!-- ************************************************************* -->
                <el-form-item label="Date" required>
                    <el-col :span="10">
                        <el-radio class="radio" v-model="choixDePropositions" label="proposition">Meilleures dates possibles dans un rayon de</el-radio>
                    </el-col>
                    <el-col :span="3">
                        <span></span>
                        <el-select :disabled="this.choixDePropositions === 'saisie_libre'" v-model="rayon"
                                   placeholder="Select" @change="findPropositionsRdv" style="display: inline">
                            <el-option v-for="item in rayons" :key="item" :label="item + ' km'" :value="item"></el-option>
                        </el-select>
                    </el-col>

                    <el-col :span="24">
                        <div v-if="propositions.length == 0">Aucune proposition trouvée</div>
                        <div v-else class="propositions">
                            <proposition-date v-for="proposition in propositions"
                                              :disabled="choixDePropositions != 'proposition'"
                                              :key="proposition.date+form.event.date+choixDePropositions"
                                              :dateSelected="form.event.date" :proposition="proposition"
                                              @change="updateDate">
                            </proposition-date>
                        </div>
                    </el-col>

                </el-form-item>

                <!-- ************************************************************* -->
                <!--                     CHOIX DATE                         -->
                <!-- ************************************************************* -->
                <el-form-item :disabled="true" prop="event.date"
                    :rules="{required: true, message: 'Veuillez saisir (ou choisir) une date'}">

                    <el-col :span="5">
                        <el-radio class="radio" v-model="choixDePropositions" label="saisie_libre">Choisir une date</el-radio>
                    </el-col>
                    <el-col :offset="1" :span="8">
                        <date-picker :date="form.event.date" :disabled="this.choixDePropositions === 'proposition'"
                                     @change="updateDate"></date-picker>
                    </el-col>
                </el-form-item>

                <!-- ************************************************************* -->
                <!--                       HEURES DEBUT / FIN                      -->
                <!-- ************************************************************* -->
                <el-form-item label="Heures" required>
                    <el-col :span="5">
                        <el-form-item prop="event.startTime"
                                :rules="{required: true, message: 'Veuillez saisir une heure de début'}">

                            <el-time-select v-model="form.event.startTime" placeholder="Début"
                                            @change="updateEndTime"
                                            :picker-options="{ start: heureOuverture, step: step, end: heureFermeture }"
                                            style="width: 100%;"></el-time-select>
                        </el-form-item>
                    </el-col>

                    <el-col :offset="1" :span="5">
                        <el-form-item prop="event.endTime"
                            :rules="{required: true, message: 'Veuillez saisir une heure de fin'}">

                            <el-time-select v-model="form.event.endTime" placeholder="Fin"
                                            :picker-options="{ start: heureOuverture, step: step, end: heureFermeture, minTime: form.event.startTime }"
                                            style="width: 100%;"></el-time-select>
                        </el-form-item>
                    </el-col>
                </el-form-item>
            </el-form>


            <el-form v-if="etape === 2" :model="form" label-position="top" label-width="120px" ref="form2">
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
  import Constants from '../../../bean/Constants'
  import {entreprise} from '../../../bean/EntrepriseBean'
  import moment from 'moment'
  import AdresseAutoComplete from './AdresseAutoComplete.vue'
  import {RendezVousResource} from '../../../resource/RendezVousResource'
  import PropositionDate from './PropositionDate.vue'
  import DatePicker from './DatePicker.vue'

  export default {
    name: 'rdvForm',
    components: {
      'adresse-autocomplete': AdresseAutoComplete,
      'proposition-date': PropositionDate,
      'date-picker': DatePicker
    },
    props: {
      client: {
        type: Object,
        required: true
      },
      rdv: {
        type: Object,
        required: false
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
      if (this.rdv) {
        this.form.date = this.rdv.event.start
        this.form.startTime = this.rdv.event.start
        this.form.endTime = this.rdv.event.end
      }
      if (!this.form.event.id) {
        let civilite = this.client.civilite ? this.client.civilite + ' ' : ''
        let nom = this.client.nom ? this.client.nom : ''
        this.form.event.summary = civilite + nom
      }
    },
    computed: {
      heureOuverture: function () {
        return moment(entreprise.configuration.heureOuverture).format('HH:mm')
      },
      heureFermeture: function () {
        return moment(entreprise.configuration.heureFermeture).format('HH:mm')
      },
      step: function () {
        return entreprise.configuration.timeStep
      },
      rayons: function () {
        return Constants.rdv.rayons
      },
      durees: function () {
        let result = []
        let step = entreprise.configuration.timeStep
        let current = moment.duration(step, 'HH:mm')

        for (let i = 0; current.asHours() <= 8; i++) {
          result.push(moment.utc(current.asMilliseconds()).format('HH:mm'))
          // console.log(current.asHours() + ', ' + moment.duration(step).asMinutes() + ', ' + result)
          current.add(step)
        }

        return result
      }
    },
    methods: {
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

      updateDate (date) {
        // console.log('Receiving date', date)
        this.form.event.date = date
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

    .client-name {
        vertical-align: text-top;
    }

    .note {
        display: inline-block;
        margin-left: 15px;
    }

    .fa-circle-add {
        color: #f25f5c;
    }

    .fa-circle {
        text-shadow: 1px 1px 3px black;
    }

    .fa-circle-edit {
        color: rgba(105, 214, 72, 0.95);
    }

    .propositions {
        max-height: 15em;
        overflow-y: auto;
    }

</style>
