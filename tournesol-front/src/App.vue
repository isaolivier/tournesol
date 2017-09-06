<template>
  <div id="app">
    <auth></auth>
    <router-view></router-view>
  </div>
</template>

<script>
import Auth from './components/Auth'
export default {
  name: 'app',
  components: {
    'auth': Auth
  },
  methods: {

  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
