<template>
  <div class="hello">
    <header>
      <h1>Tournesol</h1>
      <auth></auth>
    </header>
    <div class="content">
      <el-tabs type="border-card" v-if="this.store && this.store.initialized && this.store.signedIn">
        <el-tab-pane>
          <span slot="label"><i class="el-icon-edit"></i> Client</span>
          <annuaire></annuaire>
        </el-tab-pane>
        <el-tab-pane>
          <span slot="label"><i class="el-icon-date"></i> Tournées</span>
          <tournee></tournee>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>

</template>

<script>
  import Auth from './auth/Auth'
  import Annuaire from './client/Annuaire.vue'
  import Tournee from './tournee/Tournee.vue'

  export default {
    name: 'main',
    data () {
      return {
        store: this.$root.$data
      }
    },
    components: {
      // <my-component> will only be available in parent's template
      'auth': Auth,
      'tournee': Tournee,
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

  .hello{
    height: 100vh;
  }

  header{
    height: 150px;
    overflow:auto
  }

  .content{
    height: calc(100vh - 150px);
  }

  .content .el-tabs{
    height: 100%;
  }
</style>
