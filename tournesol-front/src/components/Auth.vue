<template>
  <div class="container container-table">
    <!-- login Button -->
    <button v-on:click="signIn">Sign in with Google</button>
    <button v-on:click="signOut">Sign out</button>
    <button v-on:click="test">Test</button>
  </div>
</template>

<script>
import AuthService from '../services/auth-service.js'
export default {
  name: 'Auth',
  data: function (router) {
    return {
      section: 'Auth',
      response: ''
    }
  },
  methods: {
    signIn () {
      console.log('VUE: signIn')
      AuthService.signIn()
    },
    signOut () {
      console.log('VUE: signOut')
      AuthService.signOut()
    },
    test () {
      console.log('VUE: test')
      AuthService.test()
    }
  }
}
</script>
