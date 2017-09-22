<template>
    <div :class="[dateClass, { highlight: highlight, eow: isEOW}]">
        <div class="dow">{{dow}}</div>
        <div class="nb">{{nb}}</div>
        <div class="month">{{month}}</div>
    </div>
</template>

<script>
  export default {
    name: 'date',
    data: function () {
      return {
        dateClass: 'date'
      }
    },
    props: {
      date: {
        type: Object,
        required: true
      },
      highlight: {
        type: Boolean,
        required: false
      }
    },
    computed: {
      dow: function () {
        return this.date.format('ddd')
      },
      nb: function () {
        return this.date.date()
      },
      month: function () {
        return this.date.format('MMM')
      },
      isEOW: function () {
        return this.date.day() === 6 || this.date.day() === 0
      }
    }
  }
</script>

<style scoped>
    .date {
        width: 80px;
        display: block;
        flex-shrink: 0;
        border: solid 1px #2c3e50;
        margin: 0px 2px;
        padding : 0 0 4px 0;
        text-align: center;

    }

    .highlight {
        border: solid 2px orange;
        background-color: orange;
        color: white;
        text-shadow: 1px 1px 3px #333;
    }

    .eow {
        color: grey;
        border: solid 1px #aaa;
        width: 50px;
        background-color: #eee;
    }

    .dow {
        font-size : 10pt;
        text-align: left;
        padding-left: 3px;
    }

    .nb, .month {
        font-size : 14pt;
    }
</style>
