<template>
    <a class="letter" @click="scrollToSection(letter)" :id="separator?letter:false">
        <span class="fa-stack fa-lg">
          <i class="fa fa-circle fa-stack-2x"></i>
          <i class="fa fa-stack-1x fa-inverse">{{letter}}</i>
        </span>
        <hr v-if="separator"/>
    </a>

</template>

<script>
  export default {
    name: 'letter',
    props: {
      letter: {
        required: true
      },
      separator: {
        type: Boolean,
        required: false,
        default: true
      }
    },
    data () {
      return {}
    },
    methods: {
      scrollToSection (letter) {
        let element = document.querySelector('#' + letter)
        let parentRect = document.querySelector('.el-tabs__content')
        parentRect.scrollTop = element.offsetTop
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .letter {
        margin: 0 0 0 1em;
    }

    hr {
        position: relative;
        top: -1.5em;
        left: -1.5em;
        margin: 0 5em 0 5em;
    }
</style>
