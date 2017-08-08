<template>
    <div class="horiz-flex">
        <icon name="angle-left" scale="4"></icon>
        <div class="scrolling-container">
            <div class="horiz-flex" style="margin-left: -50%">
                <date v-for="date in days" :date="date" :highlight="date.dayOfYear() === dateCourante.dayOfYear()" :key="date.millisecond()"></date>
            </div>
        </div>
        <icon name="angle-right" scale="4"></icon>
    </div>
</template>

<script>
  import moment from 'moment'
  import DateVue from './Date.vue'

  export default {
    name: 'dates',
    data: function () {
      return {
        dateCourante: moment(),
        nbDates: 31
      }
    },
    computed: {
      days: function () {
        let days = []
        let nbDaysBefore = Math.floor(this.nbDates / 2)
        for (let i = 0; i < this.nbDates; i++) {
          let curDate = moment(this.dateCourante).add(i - nbDaysBefore, 'days')
          days.push(curDate)
        }
        return days
      }
    },
    components: {
      'date': DateVue
    }
  }
</script>

<style scoped>
    .horiz-flex {
        display:flex;
        flex-direction: row;
    }

    .scrolling-container{
        width: 100%;
        overflow-x: scroll;
        overflow: hidden;
        text-align: center;
    }
</style>
