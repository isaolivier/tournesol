<template>
  <div class="container container-table">
    <!-- login Button -->
    <button v-if="displaySignIn" v-on:click="signIn"><icon name="power-off"></icon>&nbsp;Sign in</button>
    <button v-if="displaySignOut" v-on:click="signOut"><icon name="power-off"></icon>&nbsp;Sign out</button>
    <button v-on:click="fetchCals"><icon name="bolt"></icon>&nbsp;Fetch cals</button>
  </div>
</template>

<script>
import Vue from 'vue'
export default {
  name: 'Auth',
  data: function (router) {
    return {
      section: 'Auth',
      response: '',
      displaySignIn: true,
      displaySignOut: false
    }
  },
  methods: {
    signIn () {
      Vue.googleAuth().signIn(
        function (authCode) {
          console.log('authCode ', authCode)
          if (!window.gapi.auth2.getAuthInstance().isSignedIn.get()) {
            setTimeout(function () { console.log('Just waiting for GAPI update') }, 1000)
          }
          let data = {'code': authCode, 'email': window.gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getEmail()}
          Vue.http.post('http://localhost:8081/auth', data).then(
            function (response) {
              console.log('signIn OK')
              // if (response.body) {}
            },
            function (error) {
              console.log('signIn KO ', error)
            }
          )
        },
        function (error) {
          console.log('signIn KO - no auth code ', error)
        }
      )
    },
    signOut () {
      Vue.googleAuth().signOut(function () { console.log('signOut OK') }, function (error) { console.log('signOut KO ', error) })
    },
    fetchCals () {
      console.log('Fetching calendars')
      if (window.gapi.auth2.getAuthInstance().isSignedIn.get()) {
        Vue.http.get('http://localhost:8081/fetchCal?email=' + window.gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getEmail()).then(
          function (response) { console.log('Fetch calendars OK') },
          function (error) {
            console.log('Fetch calendars KO')
            console.log(error)
          }
        )
      } else { console.log('Fetching calendars KO - user is not signed in') }
    },
    updateSigninStatus (isSignedIn) {
      console.log('Update status ', isSignedIn)
      if (isSignedIn) {
        this.displaySignIn = false
        this.displaySignOut = true
      } else {
        this.displaySignIn = true
        this.displaySignOut = false
      }
    },
    debugVars () {
      console.log('window ', window)
      console.log('window.gapi ', window.gapi)
      console.log('window.gapi.auth2 ', window.gapi.auth2)
      console.log('window.gapi.auth2.getAuthInstance() ', window.gapi.auth2.getAuthInstance())
      console.log('window.gapi.auth2.getAuthInstance().currentUser ', window.gapi.auth2.getAuthInstance().currentUser)
      console.log('window.gapi.auth2.getAuthInstance().currentUser.get() ', window.gapi.auth2.getAuthInstance().currentUser.get())
      console.log('window.gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile() ', window.gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile())
      console.log('window.gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getEmail() ', window.gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getEmail())
    }
  },
  mounted () {
    console.log('mounted')
    setTimeout(function () {
      // this.debugVars()
      window.gapi.auth2.getAuthInstance().isSignedIn.listen(this.updateSigninStatus)
      this.updateSigninStatus(window.gapi.auth2.getAuthInstance().isSignedIn.get())
    }.bind(this), 1000)
  }
}
</script>
