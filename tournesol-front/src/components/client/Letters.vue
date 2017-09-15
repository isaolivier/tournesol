<template>
    <div class="letters">
        <ul>
            <li v-for="(letter, index) in letters" x="98%" :y="index * 20 + 30">{{letter}}</li>
        </ul>
    </div>
</template>

<script>
  export default {
    name: 'letters',
    props: {
      model: {
        type: Set,
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
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.letters{
    position: absolute;
    right:0;
    top:0;
    border: #d6d6b1 1px dotted ;
    height: 100%;
    overflow-y: hidden;
}

.letters ul{
    margin: 0;
    padding: 10px;
}

.letters ul li{
    list-style-type: none;
}
</style>
