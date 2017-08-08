<template>
    <div :class="[dateClass, { highlight: highlight}]">
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
      }
    }
  }
</script>

<style scoped>
    .date {
        width: 80px;
        display: block;
        flex-shrink: 0;
        border: solid 1px black;
        margin: 0px 2px;
    }

    .highlight {
        border: solid 2px orange;
    }
</style>
