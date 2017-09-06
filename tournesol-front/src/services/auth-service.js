import Vue from 'vue'
export default {
  signIn () {
    console.log('Service: signIn')
    window.gapi.load('auth2', function () {
      let authComp = window.gapi.auth2.init({
        client_id: '108454532704-b5fm2f7g6knii4q9nk41lp431ufssdh8.apps.googleusercontent.com',
        scope: 'profile email https://www.googleapis.com/auth/calendar',
        grant_type: 'authorization_code',
        access_type: 'offline'
      })
      authComp.grantOfflineAccess().then(function (authResult) {
        console.log('authResult ', authResult)
        Vue.http.post('http://localhost:8081/authcode', authResult['code']).then(
          function (response) {
            if (response.body) {
              console.log('signIn OK')
            }
          },
          function (error) {
            console.log('signIn KO')
            console.log(error)
          }
        )
      })
    })
  },
  signOut () {
    console.log('Service: signOut')
    window.gapi.load('auth2', function () {
      let authComp = window.gapi.auth2.init({
        client_id: '108454532704-b5fm2f7g6knii4q9nk41lp431ufssdh8.apps.googleusercontent.com',
        scope: 'profile email https://www.googleapis.com/auth/calendar',
        grant_type: 'authorization_code',
        access_type: 'offline'
      })
      authComp.signOut
    })
  },
  isSignedIn () {
    console.log('Service: isSignedIn')
  },
  test () {
    console.log('Service: test')
    Vue.http.get('http://localhost:8081/test').then(
      function (response) {
        if (response.body) {
          console.log('test OK')
        }
      },
      function (error) {
        console.log('test KO')
        console.log(error)
      }
    )
  }
}
