<template>
  <div class="hello">
    <el-tabs type="border-card">
      <el-tab-pane>
        <span slot="label"><i class="el-icon-edit"></i> Client</span>
        <annuaire></annuaire>
      </el-tab-pane>
      <el-tab-pane>
        <span slot="label"><i class="el-icon-date"></i> Tournées</span>
        <tournee v-on:authInitialized="fetchData"></tournee>
      </el-tab-pane>
    </el-tabs>
  </div>

</template>

<script>
  import Annuaire from './client/Annuaire.vue'
  import Tournee from './tournee/Tournee.vue'

  export default {
    name: 'main',
    data () {
      return {
        msg: 'Welcome to Your Vue.js App'
      }
    },
    components: {
      // <my-component> will only be available in parent's template
      Tournee,
      'annuaire': Annuaire
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
