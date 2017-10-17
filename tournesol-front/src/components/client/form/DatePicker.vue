<template>
    <el-date-picker ref="datePicker"
                    v-on:click.native="renderCalendar"
                    :disabled="disabled" v-model="selectedDate"
                    type="date" placeholder="Date"
                    @change="updateDate"
                    format="dddd dd MMMM yyyy"
                    :picker-options="{ disabledDate: disabledDate, firstDayOfWeek: 1 }"
                    style="width: 100%;"></el-date-picker>
</template>

<script>
  import {RendezVousResource} from '../../../resource/RendezVousResource'
  import {entreprise} from '../../../bean/EntrepriseBean'
  import moment from 'moment'

  export default {
    name: 'date-picker',
    props: {
      date: null,
      disabled: false
    },
    data () {
      return {
        selectedDate: this.date
      }
    },
    computed: {
      now: function () {
        return moment().startOf('day')
      }
    },
    mounted () {
    },
    methods: {
      // Calcul des dates désactivées lors du choix des dates
      disabledDate (date) {
        let d = moment(date)
        if (this.now.isBefore(d)) {
          let joursOuvertures = entreprise.configuration.joursOuverture
          // console.log(joursOuvertures, d.format('DD-MM'), d.weekday(), joursOuvertures.includes(d.weekday()))
          return !joursOuvertures.includes(d.weekday() + 1)
        }
        return true
      },
      renderCalendar () {
        let self = this
        this.$watch(
          () => this.$refs.datePicker.picker.month,
          (month) => self.colorDates(month)
        )

        this.colorDates(this.$refs.datePicker.picker.month)
      },
      colorDates (month) {
        let self = this
        let rdvResource = new RendezVousResource()
        rdvResource.findFreeDays(month, this.$refs.datePicker.picker.year, (err, result) => {
          if (err) {
            this.error = err.toString()
          } else {
            let freeDates = result.map(e => moment(e).format('DD'))
            self.colorFreeDates(freeDates)
          }
        })
      },
      colorFreeDates (freeDates) {
        // Réinitialisation des cellules courantes
        let freeCells = document.querySelectorAll('.el-date-table td.free')
        Array.prototype.slice.call(freeCells).forEach(e => e.classList.remove('free'))

        let availableCells = document.querySelectorAll('.el-date-table td.available')

        Array.prototype.slice.call(availableCells)
          .filter(e => freeDates.includes(e.innerText) || freeDates.includes('0' + e.innerText))
          .forEach(e => e.classList.add('free'))
      },
      updateDate (d) {
        this.$emit('change', d)
        // console.log(this.$refs.datePicker.picker.month)
      }
    }
  }
</script>

<style>
    .free {
        color: white;
        background-color: green;
    }
</style>
