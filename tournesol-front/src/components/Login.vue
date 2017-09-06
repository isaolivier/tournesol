<template>
    <el-row :gutter="20">
        <el-col :span="18"><div class="grid-content">&nbsp;</div></el-col>
        <el-col :span="3">
          <!-- <div class="grid-content">
            <g-signin-button
                    :params="googleSignInParams"
                    @success="onSignInSuccess"
                    @error="onSignInError">
                Sign in with Google
            </g-signin-button>
          </div> -->
        </el-col>
        <el-col :span="3">
          <div class="grid-content">
            <!-- <button v-on:click="signOut();">Sign out</button> -->
          </div>
        </el-col>
    </el-row>
</template>

<script>
  export default {
    name: 'Login',
    data: function (router) {
      return {
        googleSignInParams: {
          client_id: '108454532704-ad5ips5206l0lutsqpmcbvh7229e3t0g.apps.googleusercontent.com',
          scope: 'https://www.googleapis.com/auth/calendar'
        }
      }
    },
    methods: {
      signOut () {
        console.log('Sign out')
        window.gapi.auth2.getAuthInstance().signOut().then(function () {
          console.log('User signed out.')
        })
      },
      onSignInSuccess (googleUser) {
        console.log('onSignInSuccess')
        console.log('googleUser: ')
        console.log(googleUser)
        console.log('googleUser: ')
        console.log(googleUser)
        // `googleUser` is the GoogleUser object that represents the just-signed-in user.
        // See https://developers.google.com/identity/sign-in/web/reference#users
        const profile = googleUser.getBasicProfile()
        const idToken = googleUser.getAuthResponse().id_token

        console.log('profile', profile)
        console.log('token', idToken)

        // TODO stocker le user pour passer le client_id au back pour une authent implicite
        if (window.localStorage) {
          window.localStorage.setItem('user', JSON.stringify(profile))
          window.localStorage.setItem('token', idToken)
        }
        // POST token to back for validation and storage
        // GET /someUrl
        this.$http.post('/authcode').then(response => {
          // success callback
          console.log('authcode POSTed to BE successfully')
          this.$router.push('/home')
        }, response => {
          // error callback
          console.log('Could not POST authcode to BE')
        })
      },
      onSignInError (error) {
        // `error` contains any error occurred.
        console.log('OH NOES', error)
      }
    }
  }
</script>
<style>
    .g-signin-button {
        /* This is where you control how the button looks. Be creative! */
        display: inline-block;
        padding: 4px 8px;
        border-radius: 3px;
        background-color: #3c82f7;
        color: #fff;
        box-shadow: 0 3px 0 #0f69ff;
    }
</style>
