<template>
    <div class="letter" :id="letter">
        <span class="fa-stack fa-lg">
          <i class="fa fa-circle fa-stack-2x"></i>
          <i class="fa fa-stack-1x fa-inverse">{{letter}}</i>
        </span>
        <hr>
    </div>

</template>

<script>
  export default {
    name: 'letter',
    props: {
      letter: {
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
    .letter{
        margin: 0 0 0 1em;
    }
hr{
    position: relative;
    top: -1.5em;
    left: -1.5em;
    margin: 0 5em 0 5em;
}
</style>
