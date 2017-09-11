<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4">
        <small>
          <div class="grid-content">
            <el-alert v-if="debug" type="warning" title="" :closable="false">{{store}}</el-alert>
          </div>
        </small>
      </el-col>
      <el-col :span="1" :push="19">
        <div class="grid-content">
          <div v-if="store.initialized && store.signedIn" v-on:click="signOut"><icon name="power-off" class="signOut"></icon></div>
          <div v-if="store.initialized && !store.signedIn" v-on:click="promptUserConsent"><icon name="power-off" class="signIn"></icon></div>
          <div v-if="!store.initialized" v-on:click="alert('Auth module not available')"><icon name="power-off" class="noSign"></icon></div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="8" :push="16">
        <small>
          <div class="grid-content">
            <el-alert v-if="showError" type="error" title="">{{error}}</el-alert>
            <el-alert v-if="showSuccess" type="success" title="">{{success}}</el-alert>
          </div>
        </small>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { authService } from './auth-service'
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
      debug: true
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
      let self = this
      if (message && message === 'success' && this.store.signedIn) {
        this.success = 'Bienvenue ' + JSON.stringify(authService.getUser()) + '\n' + message
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
.noSign {
  color: gray;
}
.signIn {
  color: red;
}
.signOut {
  color: green;
}
.power-buttons {
  position: absolute;
  top: 5;
  right: 5;
}
</style>
