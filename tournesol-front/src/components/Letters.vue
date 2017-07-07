<template>
    <svg>
        <rect x="97%" y="0" width="3%" height="100%" fill="white" stroke="#765373"></rect>
        <svg>
            <text v-for="(letter, index) in letters" x="98%" :y="index * 20 + 30">{{letter}}</text>
        </svg>
    </svg>
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

</style>
