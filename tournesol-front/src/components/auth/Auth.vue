<template>
  <div class="container container-table">
    <small><small><div>
      <button v-on:click="promptUserConsent"><icon name="power-off"></icon>&nbsp;Sign in</button>
      <button v-on:click="signOut"><icon name="power-off"></icon>&nbsp;Sign out</button>
      &nbsp; Store: {{store}}
    </div></small></small>
  </div>
</template>

<script>
import { authService } from './auth-service'
export default {
  name: 'Auth',
  data: function (router) {
    return {
      section: 'Auth',
      store: this.$root.$data
    }
  },
  methods: {
    initService () {
      if (!this.store.initialized) {
        let gapiprops = {
          'client_id': '108454532704-ad5ips5206l0lutsqpmcbvh7229e3t0g.apps.googleusercontent.com',
          'scope': 'email profile https://www.googleapis.com/auth/calendar.readonly',
          'fetch_basic_profile': false,
          'prompt': 'consent',
          'remote_script_url': 'https://apis.google.com/js/api:client.js'
        }
        let authserviceprops = {
          'backendURL': 'http://localhost:8081',
          'timeout': 2000,
          'authURL': '/auth',
          'aliveURL': '/isSessionAlive'
        }
        authService.init(gapiprops, authserviceprops, this.serviceInitCallBack, this.onSignInStatusChanged)
      }
    },
    serviceInitCallBack () {
      this.store.initialized = true
    },
    onSignInStatusChanged (signedIn) {
      if (signedIn) {
        this.store.signedIn = true
      } else {
        this.store.signedIn = false
      }
    },
    promptUserConsent () {
      authService.promptUserConsent()
    },
    signOut () {
      authService.signOut()
    }
  },
  mounted () {
    this.initService()
  }
}
</script>
