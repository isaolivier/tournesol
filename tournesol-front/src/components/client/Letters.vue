<template>
    <div class="letters">
        <ul>
            <li v-for="(letter, index) in letters" x="98%" :y="index * 20 + 30">
                <letter :separator="false" :letter="letter"/>
            </li>
        </ul>
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
      return {}
    },
    computed: {
      letters: function () {
        return Array.from(this.model).sort()
      }
    },
    components: {
      'letter': Letter
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .letters {
        position: fixed;
        right: 30px;
        top: 150px;
        border: none;
        height: 100%;
        overflow-y: hidden;
    }

    .letters ul {
        margin: 0;
        padding: 0px;
    }

    .letters ul li {
        list-style-type: none;
    }
</style>
