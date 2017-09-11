<template>
  <div class="container container-table">
    <small>
      <small>
        <div>
          <button v-on:click="promptUserConsent"><icon name="power-off"></icon>&nbsp;Sign in</button>
          <button v-on:click="signOut"><icon name="power-off"></icon>&nbsp;Sign out</button>
          <button v-on:click="fetchCal"><icon name="bolt"></icon>&nbsp;Fetch cal</button>
        </div>
        <div>
          <p>Cal: {{cal}}</p>
        </div>
      </small>
    </small>
  </div>
</template>

<script>
import { authService } from './auth-service'
export default {
  name: 'Auth',
  data: function (router) {
    return {
      section: 'Auth',
      serviceInitialized: false,
      cal: {}
    }
  },
  computed: {
    isSignedIn: function () {
      authService.isSignedIn
    }
  },
  methods: {
    initService () {
      if (!this.serviceInitialized) {
        let gapiprops = {
          'client_id': '108454532704-ad5ips5206l0lutsqpmcbvh7229e3t0g.apps.googleusercontent.com',
          'fetch_basic_profile': false,
          'scope': 'email profile https://www.googleapis.com/auth/calendar.readonly',
          'prompt': 'consent',
          'remote_script_url': 'https://apis.google.com/js/api:client.js'
        }
        let authserviceprops = {
          'backendURL': 'http://localhost:8081',
          'timeout': 2000,
          'authURL': '/auth',
          'aliveURL': '/isSessionAlive'
        }
        authService.init(gapiprops, authserviceprops, this.initAuthServiceCallBack, this.onSignedInChanged)
      }
    },
    initAuthServiceCallBack (e) {
      console.log('Auth service initialized')
      this.serviceInitialized = true
      this.$emit('authInitialized')
    },
    onSignedInChanged (signedIn) {
      // console.log('onSignedInChanged ', signedIn)
      if (signedIn) {
        console.log('Signed in')
        this.$emit('signedIn')
      } else {
        console.log('Signed out')
        this.$emit('signedOut')
      }
    },
    promptUserConsent () {
      authService.promptUserConsent()
    },
    signOut () {
      authService.signOut()
    },
    fetchCal () {
      authService.fetchCal(this.fetchCalCallBack)
    },
    fetchCalCallBack (cal) { this.cal = cal }
  },
  mounted () {
    this.initService()
  }
}
</script>
