<template>
    <div class="letters">
        <i @mousedown="startScrollUp" @mouseleave="stopScroll" @mouseup="stopScroll" @touchstart="startScrollUp"
           @touchend="stopScroll" @touchcancel="stopScroll" class="fa fa-chevron-up fa-2x" aria-hidden="true"></i>
        <ul>
            <li v-for="(letter, index) in letters">
                <letter :separator="false" :letter="letter"/>
            </li>
        </ul>
        <i @mousedown="startScrollDown" @mouseleave="stopScroll" @mouseup="stopScroll" @touchstart="startScrollDown"
           @touchend="stopScroll" @touchcancel="stopScroll" class="fa fa-chevron-down fa-2x" aria-hidden="true"></i>
    </div>
</template>

<script>
  import Letter from './Letter.vue'

  export default {
    name: 'letters',
    props: {
      model: {
        type: Array,
        required: true
      }
    },
    data () {
      return {
        interval: false,
        scroll: 0
      }
    },
    computed: {
      letters: function () {
        return Array.from(this.model).sort()
      }
    },
    components: {
      'letter': Letter
    },
    methods: {
      scrollTo (value) {
        let element = document.querySelector('.letters ul')
        element.scrollTop = value
      },
      startScrollUp: function (event) {
        if (!this.interval) {
          this.interval = setInterval(() => {
            this.scroll = this.scroll - 5
            this.scrollTo(this.scroll)
          }, 20)
        }
      },
      stopScroll: function (event) {
        clearInterval(this.interval)
        this.interval = false
      },
      startScrollDown: function (event) {
        if (!this.interval) {
          this.interval = setInterval(() => {
            this.scroll = this.scroll + 5
            this.scrollTo(this.scroll)
          }, 20)
        }
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .letters {
        position: fixed;
        right: 30px;
        top: 150px;
        bottom: 2em;
        border: none;
        height: calc(100% - 160px);
        overflow-y: hidden;
        width: auto;
    }

    .letters ul {
        margin: 0;
        padding: 0;
        height: calc(100% - 4em);
        overflow-y: hidden;
    }

    .letters ul li {
        list-style-type: none;
    }

    i {
        padding-left: 0.1em;
    }
</style>
