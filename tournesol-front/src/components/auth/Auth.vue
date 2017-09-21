<template>
  <div>
    <div class="alerts">
      &nbsp;
      <el-alert v-if="showError" type="error" title="" :closable="false">{{error}}</el-alert>
      <el-alert v-if="showSuccess" type="success" title="" :closable="false">{{success}}</el-alert>
    </div>
    <el-row>
      <el-col :span="6">
        <div class="grid-content">
          &nbsp;
          <el-alert v-if="debug" type="warning" title="Store " :closable="false"><small>{{store}}</small></el-alert>
        </div>
      </el-col>
      <el-col :span="1" :push="17">
        <div class="grid-content">
          &nbsp;
          <div v-if="store.initialized && store.signedIn" @click="signOut"><i class="fa fa-power-off signOut"></i></div>
          <div v-if="store.initialized && !store.signedIn" @click="promptUserConsent"><i class="fa fa-power-off signIn"></i></div>
          <div v-if="!store.initialized" @click="alert('Auth module not available')"><i class="fa fa-power-off noSign"></i></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { authService } from './auth-service'
import Constants from '../../bean/Constants'

export default {
  name: 'Auth',
  data: function (router) {
    return {
      section: 'Auth',
      store: this.$root.$data,
      showError: false,
      error: '&nbsp;',
      showSuccess: false,
      success: '&nbsp;',
      debug: false
    }
  },
  methods: {
    initService () {
      if (!this.store.initialized) {
        let gapiprops = {
          'client_id': '108454532704-ad5ips5206l0lutsqpmcbvh7229e3t0g.apps.googleusercontent.com',
          'scope': 'email profile https://www.googleapis.com/auth/calendar',
          'fetch_basic_profile': true,
          'prompt': 'consent',
          'remote_script_url': 'https://apis.google.com/js/api:client.js'
        }
        let authserviceprops = {
          'backendURL': Constants.back.hostname,
          'timeout': 2000,
          'authURL': '/auth',
          'aliveURL': '/isSessionAlive'
        }
        authService.init(gapiprops, authserviceprops, this.serviceInitCallBack, this.onSignInStatusChanged, this.onAuthOver)
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
    alert (message) {
      this.error = message
      this.showError = true
      window.setTimeout(function () {
        self.showError = false
      }, 5000)
    },
    onAuthOver (message, error) {
      // console.log('onAuthOver ', message, error, this.store.signedIn)
      let self = this
      if (message && message === 'success' && this.store.signedIn) {
        this.success = 'Bienvenue ' + authService.getUser().getBasicProfile().getEmail()
        this.showSuccess = true
        window.setTimeout(function () {
          self.showSuccess = false
        }, 5000)
      } else if (message && message === 'success' && !this.store.signedIn) {
        this.success = 'Vous avez été déconnecté'
        this.showSuccess = true
        window.setTimeout(function () {
          self.showSuccess = false
        }, 5000)
      } else {
        this.error = 'Une erreur est survenue ' + '\n' + message + '\n' + JSON.stringify(error)
        this.showError = true
        window.setTimeout(function () {
          self.showError = false
        }, 5000)
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
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.fa-icon {
  width: auto;
  height: 1em; /* or any other relative font sizes */

  /* You would have to include the following two lines to make this work in Safari */
  max-width: 100%;
  max-height: 100%;
}
.alerts {
  position: absolute;
  top: 0;
  right: 0;
}
.noSign {
  color: gray;
}
.signIn {
  color: red;
}
.signOut {
  color: green;
}
</style>
