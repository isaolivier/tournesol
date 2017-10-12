<template>
    <div >
        <!--
        {"date":"2017-10-02","events":[{"id":"6aejdi6h2kir0npmekrflo6p2k","summary":"Mme BIDEGARAY Nicole","description":null,"location":"Avenue Aristide Briand, 33600 Pessac","status":"confirmed","start":"2017-10-02T08:30:00+02:00","end":"2017-10-02T10:00:00+02:00","colorId":null}]}
        //-->
        <el-radio class="radio" v-model="radio" label="1">{{proposition.date}}</el-radio>
        <div class="events">
            <div :class="e.class" :style="'flex-basis:'+e.duration+'%'" v-for="e in allEvents">{{e.label}}</div>
        </div>
    </div>
</template>
<script>

  import {entreprise} from '../../bean/EntrepriseBean'
  import moment from 'moment'

  class Creneau {
    constructor () {
      this.class = ''
      this.duration = 0
      this.label = ''
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
            creneau.duration = getPercentageFromMinutes(minutesFromBeginningOfPreviousRdv)
            creneau.label = minutesFromBeginningOfPreviousRdv
            events.push(creneau)
          }

          let creneau = new Creneau()
          creneau.class = event.status
          let minutesOfEventEnd = moment(event.end).hour() * 60 + moment(event.end).minutes()
          let dureeCreneau = minutesOfEventEnd - minutesOfEventStart
          creneau.duration = getPercentageFromMinutes(dureeCreneau)
          creneau.label = dureeCreneau
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
          creneau.duration = getPercentageFromMinutes(minutesFromEndOfPreviousRdv)
          creneau.label = minutesFromEndOfPreviousRdv
          events.push(creneau)
        }

        return events
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.events {
    display:flex;
    flex-direction: row;
    height: 20px;
    padding: 0
}

.events div {
    padding: 0
}

.confirmed {
    background-color: rgb(167, 0, 0);
}

.vide {
    background-color: lightgray;
}
</style>
