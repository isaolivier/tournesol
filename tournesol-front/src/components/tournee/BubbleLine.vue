<template>
    <div class="bubble-line">
        <i class="fa fa-circle-o fa-lg start" aria-hidden="true"></i>
        <i class="line" aria-hidden="true"></i>
        <i class="fa fa-circle-o fa-lg end" aria-hidden="true"></i>

    </div>
</template>

<script>

  export default {
    name: 'bubble-line',
    props: {

    },
    data () {
      return {}
    }
  }
</script>

<style scoped>
.bubble-line {
    position: absolute;
    top:0;
    bottom:0;
}

.start {
     position: absolute;
     top:0;
 }

.line {
    position: absolute;
    top:1em;
    left: 0.5em;
    bottom:1em;
    border-left: dashed 3px black;
}
.end {
    position: absolute;
    bottom:0;
}
</style>
