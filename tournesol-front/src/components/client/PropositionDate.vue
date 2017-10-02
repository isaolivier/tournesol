<template>
    <div>
        <!--
        {"date":"2017-10-02","events":[{"id":"6aejdi6h2kir0npmekrflo6p2k","summary":"Mme BIDEGARAY Nicole","description":null,"location":"Avenue Aristide Briand, 33600 Pessac","status":"confirmed","start":"2017-10-02T08:30:00+02:00","end":"2017-10-02T10:00:00+02:00","colorId":null}]}
        //-->
        {{proposition.date}}
        {{proposition.events.length}}
    </div>
</template>
<script>

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
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
