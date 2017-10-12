<template>
    <el-row>
        <el-col :span="23" :offset="1">
            <div class="proposition greybox">
                <el-radio class="radio" v-model="date" :label="this.proposition.date" :disabled="disabled">{{proposition.date}}</el-radio>
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
    constructor (clazz, duration) {
      this.class = clazz
      this.duration = duration
    }
  }

  export default {
    name: 'proposition-date',
    props: {
      disabled: false,
      dateSelected: null,
      proposition: {
        type: Object,
        required: true
      }
    },
    data () {
      return {
        date: this.dateSelected
      }
    },
    watch: {
      // à chaque fois que la question change, cette fonction s'exécutera
      date: function (newDate) {
        // console.log('Emit proposition date', newDate)
        this.$emit('change', newDate)
      }
    },
    methods: {
      labelFromMinutes (minutes) {
        let duration = moment.duration(minutes, 'minutes')
        let label = ''
        if (duration.get('hours') > 0) {
          label = duration.get('hours') + 'h'
        }
        if (duration.get('minutes') > 0) {
          label = label + duration.get('minutes')
        }
        return label
      }
    },
    computed: {
      allEvents () {
        let heureOuverture = moment(entreprise.configuration.heureOuverture).format('HH:mm')
        let heureFermeture = moment(entreprise.configuration.heureFermeture).format('HH:mm')

        let getDuration = function (start, end) {
          let dureeEvent = moment.duration(end).asMinutes() - moment.duration(start).asMinutes()
          return dureeEvent
        }

        let cursorHour = heureOuverture
        let events = []

        for (let event of this.proposition.events) {
          let eventStart = moment(event.start).format('HH:mm')
          let eventEnd = moment(event.end).format('HH:mm')

          if (moment.duration(cursorHour).asMinutes() < moment.duration(eventStart).asMinutes()) {
            let creneau = new Creneau('vide', getDuration(cursorHour, eventStart))
            events.push(creneau)
          }

          let creneau = new Creneau(event.status, getDuration(eventStart, eventEnd))
          events.push(creneau)

          cursorHour = eventEnd
        }

        let lastEventEnd = cursorHour
        if (moment.duration(lastEventEnd).asMinutes() < moment.duration(heureFermeture).asMinutes()) {
          let creneau = new Creneau('vide', getDuration(lastEventEnd, heureFermeture))
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
        text-align: center;
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
