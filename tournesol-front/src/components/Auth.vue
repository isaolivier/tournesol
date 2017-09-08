<template>
  <div class="container container-table">
    <!-- login Button -->
    <button v-if="!isSignedIn" v-on:click="promptUserConsent"><icon name="power-off"></icon>&nbsp;Sign in</button>
    <button v-if="!isSignedOut" v-on:click="disconnect"><icon name="power-off"></icon>&nbsp;Sign out</button>
    <button v-on:click="fetchCals"><icon name="bolt"></icon>&nbsp;Fetch cals</button>
    <p v-if="!isSignedOut">Email: {{email}}</p>
    <p v-if="!isSignedOut">Cals: {{cals}}</p>
  </div>
</template>

<script>
import Vue from 'vue'
export default {
  name: 'Auth',
  data: function (router) {
    return {
      debug: true,
      client_id: '108454532704-ad5ips5206l0lutsqpmcbvh7229e3t0g.apps.googleusercontent.com',
      scopes: 'email https://www.googleapis.com/auth/calendar',
      offlineAccessOptions: {'access_type': 'offline', 'approval_prompt': 'force'},
      section: 'Auth',
      response: '',
      isSignedIn: false,
      isSignedOut: true,
      authCode: '',
      user: '',
      email: '',
      cals: ''
    }
  },
  methods: {
    log (params) {
      if (this.debug === true) {
        console.log(params)
      }
    },
    init () {
      let self = this
      self.log('Init')
      window.gapi.load('auth2', function () {
        window.gapi.auth2.init({
          client_id: self.client_id,
          scope: self.scopes
        }).then(function () {
          // Better watch user object as it's updated after isSignedIn object
          // window.gapi.auth2.getAuthInstance().isSignedIn.listen(self.updateSigninStatus)
          // self.updateSigninStatus(window.gapi.auth2.getAuthInstance().isSignedIn.get())
          window.gapi.auth2.getAuthInstance().currentUser.listen(self.updateUser)
          self.updateUser(window.gapi.auth2.getAuthInstance().currentUser.get())
        })
      })
    },
    updateSigninStatus (isSignedIn) {
      let self = this
      self.log('updateSigninStatus ', isSignedIn)
      if (isSignedIn) {
        console.log('User is signed in')
        self.isSignedIn = true
        self.isSignedOut = false
      } else {
        console.log('User is not signed in')
        self.isSignedIn = false
        self.isSignedOut = true
      }
    },
    updateUser (user) {
      let self = this
      self.log('updateUser ', user.getBasicProfile())
      if (user && user.getBasicProfile() && user.getBasicProfile().getEmail()) {
        self.user = user.getBasicProfile()
        self.email = user.getBasicProfile().getEmail()
        self.logIntoServer(user)
        self.updateSigninStatus(window.gapi.auth2.getAuthInstance().isSignedIn.get())
      } else {
        self.updateSigninStatus(false)
      }
    },
    logIntoServer (user) {
      let self = this
      if (self.authCode !== '') {
        let data = {'code': self.authCode, 'email': user.getBasicProfile().getEmail()}
        Vue.http.post('http://localhost:8081/auth', data).then(
          response => {
            self.log('signIn OK')
            self.log(response.body)
          },
          error => {
            self.log('signIn KO ', error)
            self.disconnect()
          }
        )
      }
    },
    promptUserConsent () {
      let self = this
      if (window.gapi.auth2) {
        // Use that scope offline / force else the retrieved auth code will not allow to acquire a refresh token (hence token will expire in time and user has to give consent again)
        window.gapi.auth2.getAuthInstance().grantOfflineAccess(this.offlineAccessOptions).then(
          function (authCode) {
            self.authCode = authCode['code']
          },
          function (error) {
            self.log('signIn KO - no auth code ', error)
            self.authCode = ''
          }
        )
      }
    },
    disconnect () {
      let self = this
      if (window.gapi.auth2) {
        window.gapi.auth2.getAuthInstance().signOut(function () { self.log('signOut OK') }, function (error) { self.log('signOut KO ', error) })
      }
    },
    fetchCals () {
      let self = this
      self.log('Fetching calendars')
      if (window.gapi.auth2.getAuthInstance() && window.gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile()) {
        Vue.http.get('http://localhost:8081/fetchCal?email=' + window.gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getEmail()).then(
          response => {
            self.log('Fetch calendars OK')
            self.log(response.body)
            self.cals = response.body
          },
          error => {
            self.log('Fetch calendars KO')
            self.log(error)
          }
        )
      } else {
        self.log('Could not fetch calendars as user is not logged in')
      }
    }
  },
  mounted () {
    let self = this
    self.log('mounted')
    if (window.gapi) {
      self.init()
      return
    }
    let script = document.createElement('script')
    script.onload = function () {
      if (!window.gapi && !window.gapi.auth2) {
        return void (self.error('No google sigin script included'))
      }
      self.init()
    }
    script.async = true
    script.defer = true
    script.src = 'https://apis.google.com/js/api.js'
    document.getElementsByTagName('head')[0].appendChild(script)
  }
}
</script>
