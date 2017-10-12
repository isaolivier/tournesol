<template>
    <el-row>
        <el-col :span="23" :offset="1">
            <div class="proposition greybox">
                <el-radio class="radio" v-model="radio" label="1">{{proposition.date}}</el-radio>
                <div class="events">
                    <div :class="'event '+ e.class" :style="'flex-basis:'+e.duration+'%'" v-for="e in allEvents">
                        {{labelFromMinutes(e.duration)}}
                    </div>
                </div>
            </div>
        </el-col>
    </el-row>
</template>
<script>

  import {entreprise} from '../../bean/EntrepriseBean'
  import moment from 'moment'

  class Creneau {
    constructor () {
      this.class = ''
      this.duration = 0
      this.percentage = 0
    }
  }

  export default {
    name: 'proposition-date',
    props: {
      proposition: {
        type: Object,
        required: true
      }
    },
    data () {
      return {}
    },
    methods: {
      labelFromMinutes (minutes) {
        let duration = moment.duration(minutes, 'minutes')
        let label = ''
        if (duration.get('hours') > 0) {
          label = duration.get('hours') + 'h'
        }

        label = label + duration.get('minutes') + 'm'

        return label
      }
    },
    computed: {
      allEvents () {
        let getPercentageFromMinutes = function (minutes) {
          let heureDebut = moment.duration(entreprise.configuration.heureOuverture)
          let heureFin = moment.duration(entreprise.configuration.heureFermeture)

          let dureeOuvree = heureDebut.subtract(heureFin)
          console.log('dureeOuvree', dureeOuvree)
          return (minutes * 100 / Math.abs(dureeOuvree.asMinutes()))
        }

        let cursorHour = moment(this.proposition.date + ' ' + entreprise.configuration.heureOuverture)
        let events = []
        for (let event of this.proposition.events) {
          let minutesOfDay = cursorHour.hour() * 60 + cursorHour.minutes()
          let minutesOfEventStart = moment(event.start).hour() * 60 + moment(event.start).minutes()
          let minutesFromBeginningOfPreviousRdv = minutesOfEventStart - minutesOfDay

          if (minutesFromBeginningOfPreviousRdv > 0) {
            let creneau = new Creneau()
            creneau.class = 'vide'
            creneau.percentage = getPercentageFromMinutes(minutesFromBeginningOfPreviousRdv)
            creneau.duration = minutesFromBeginningOfPreviousRdv
            events.push(creneau)
          }

          let creneau = new Creneau()
          creneau.class = event.status
          let minutesOfEventEnd = moment(event.end).hour() * 60 + moment(event.end).minutes()
          let dureeCreneau = minutesOfEventEnd - minutesOfEventStart
          creneau.percentage = getPercentageFromMinutes(dureeCreneau)
          creneau.duration = dureeCreneau
          events.push(creneau)

          cursorHour = moment(event.end)
        }

        cursorHour = moment(this.proposition.date + ' ' + entreprise.configuration.heureFermeture)
        let minutesOfDay = cursorHour.hour() * 60 + cursorHour.minutes()
        let lastEvent = this.proposition.events[this.proposition.events.length - 1]
        let minutesOfEventEnd = moment(lastEvent.end).hour() * 60 + moment(lastEvent.end).minutes()
        let minutesFromEndOfPreviousRdv = minutesOfDay - minutesOfEventEnd

        if (minutesFromEndOfPreviousRdv > 0) {
          let creneau = new Creneau()
          creneau.class = 'vide'
          creneau.percentage = getPercentageFromMinutes(minutesFromEndOfPreviousRdv)
          creneau.duration = minutesFromEndOfPreviousRdv
          events.push(creneau)
        }

        return events
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

    .proposition {
        margin: 10px 0 0 0;
    }

    .proposition :first-child {
        padding-left: 5px;
    }

    .events {
        display: flex;
        flex-direction: row;
        height: 20px;
        padding: 0;
        line-height: 20px;
        text-align:center;
    }

    .events div {
        padding: 0
    }

    .event {
        border-left: #D1DBE5 1px solid;
    }

    .confirmed {
        background-color: rgba(242, 100, 7, 0.57);
    }

    .vide {
        background-color: rgba(19, 206, 102, 0.74);
    }
</style>
