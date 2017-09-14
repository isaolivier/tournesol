<template>
    <div class="horiz-flex">
        <i @click="shiftLeft" class="fa fa-angle-left fa-4x"></i>
        <div class="scrolling-container ">
            <div class="gradient"></div>
            <div class="horiz-flex center">
                <date v-for="date in days" :date="date" :highlight="date.dayOfYear() === dateCourante.dayOfYear()"
                      :key="date.millisecond()" @click.native="selectDay(date)"></date>
            </div>
            <div class=""></div>
        </div>

        <i @click="shiftRight" class="fa fa-angle-right fa-4x"></i>
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
        days: [],
        nbDates: 21
      }
    },
    created: function () {
      this.updateDays()
    },
    methods: {
      shiftLeft: function (event) {
        this.dateCourante.subtract(1, 'days')
        this.updateDays()
      },
      shiftRight: function (event) {
        this.dateCourante.add(1, 'days')
        this.updateDays()
      },
      selectDay: function (event) {
        this.dateCourante = event
        this.updateDays()
      },
      updateDays: function () {
        let days = []
        let nbDaysBefore = Math.floor(this.nbDates / 2)
        for (let i = 0; i < this.nbDates; i++) {
          let curDate = moment(this.dateCourante).add(i - nbDaysBefore, 'days')
          days.push(curDate)
        }
        this.days = days
      }
    },
    components: {
      'date': DateVue
    }
  }
</script>

<style scoped>
    .horiz-flex {
        display: flex;
        flex-direction: row;
    }
    .gradient{
        background: linear-gradient(to right, white, rgba(0,0,0,0) 10%, rgba(0,0,0,0) 90%, white );
        position:absolute;
        left:0; top:0;
        width: 100%;
        height:100%;
        pointer-events: none;
    }

    .scrolling-container {
        width: 100%;
        overflow-x: scroll;
        overflow: hidden;
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>
