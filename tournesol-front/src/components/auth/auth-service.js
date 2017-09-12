var axios = require('axios')
// Please consider that what might appear as dead code is actually debug code :D please don't strip
class AuthService {
  constructor () {
    // Google OAuth2 module props
    this.gapiprops = null
    // Service props
    // { desc it }
    this.serviceprops = null
    // The Google API module
    this.gapi = null
    // The Auth status for listeners
    // plugged onto user profile regarding state as this one is available the last
    this.isServiceInitialized = false
    this.isSignedIn = false
    // The current user profile
    this.user = null
    // The AuthInfo used in exchanges with the backend
    this.authInfo = null
    // Axios HTTP client_id
    this.axios = null
    // External callbacks
    // Service init callback
    this.initcallback = null
    // Auth over callback
    this.authovercallback = null
    // Signed in/out callback
    this.signedinchangedcallback = null
  }
  sampleGAPIProps () {
    return {
      'client_id': 'SOMETHINGREAL.apps.googleusercontent.com',
      'fetch_basic_profile': false,
      'scope': 'profile email https://www.googleapis.com/auth/calendar.readonly',
      'prompt': 'consent',
      'remote_script_url': 'https://apis.google.com/js/api:client.js'
    }
  }
  sampleServiceProps () {
    return {
      'backendURL': 'http://localhost:8081',
      'timeout': 2000,
      'authURL': '/auth',
      'aliveURL': '/isSessionAlive'
    }
  }
  init (gapiprops, serviceprops, initcallback, signedinchangedcallback, authovercallback) {
    // console.log('Init')
    if (!this.isServiceInitialized) {
      this.gapiprops = gapiprops
      this.serviceprops = serviceprops
      this.initcallback = initcallback
      this.signedinchangedcallback = signedinchangedcallback
      this.authovercallback = authovercallback
      // console.log('Google API props ', JSON.stringify(this.gapiprops))
      // console.log('Service props ', JSON.stringify(this.serviceprops))
      this._loadRemoteScript()
      this.axios = axios.create({
        baseURL: this.serviceprops['backendURL'],
        timeout: this.serviceprops['timeout']
      })
    }
  }
  // Externally exposed functions
  promptUserConsent () {
    if (this.gapi && this.gapi.auth2 && !this.authInfo['uid'] && !this.authInfo['authcode']) {
      let self = this
      let scope = {
        scope: self.gapiprops['scope'],
        prompt: self.gapiprops['prompt'],
        fetch_basic_profile: self.gapiprops['fetch_basic_profile']
      }
      // console.log('Prompting user consent for ', scope)
      this.gapi.auth2.getAuthInstance().grantOfflineAccess(scope).then(
        function (authCode) {
          // console.log('User consent OK - authcode ', JSON.stringify(authCode))
          self.authInfo['authcode'] = authCode['code']
        },
        function (error) {
          self.authInfo = {}
          self.authovercallback('User consent KO - no auth code ', error)
        }
      )
    }
    // else { console.log('Already signed in') }
  }
  signOut () {
    if (this.gapi && this.gapi.auth2 && (this.authInfo['uid'] || this.authInfo['authcode'])) {
      this.gapi.auth2.getAuthInstance().currentUser.get().disconnect()
      this._setSignedOut()
      this.authovercallback('success')
    }
    // else { console.log('signOut - already signed out') }
    this.authInfo = {}
    this._clearLocalStorage()
  }
  getAuthInfo () { return this.authInfo }
  getUser () { return this.user }
  isServiceInitialized () { return this.isServiceInitialized }
  isSignedIn () { return this.isSignedIn }
  // Internal functions
  // Auth component init
  _loadRemoteScript () {
    if (!this.gapi) {
      // console.log('Loading Google API ', this.gapiprops['remote_script_url'])
      let script = document.createElement('script')
      script.src = this.gapiprops['remote_script_url']
      script.async = true
      script.defer = true
      let self = this
      script.onload = function () {
        self._onScriptLoaded()
      }
      document.getElementsByTagName('head')[0].appendChild(script)
    }
    // else { console.log('Google API ', this.gapiprops['remote_script_url'], ' already loaded !') }
  }
  _onScriptLoaded () {
    // console.log('Google API ', this.gapiprops['remote_script_url'], ' loaded !')
    if (window.gapi) {
      this.gapi = window.gapi
      this._loadOAuth2Module()
    }
    // else { return void (console.error('Google API not found. Aborting')) }
  }
  _loadOAuth2Module () {
    let self = this
    // console.log('Loading OAuth2')
    this.gapi.load('auth2', {
      callback: function () {
        self.gapi.auth2.init({
          client_id: self.gapiprops['client_id'],
          scope: self.gapiprops['scope'],
          fetch_basic_profile: self.gapiprops['fetch_basic_profile']
        }).then(function () { self._onOAuth2ModuleLoaded() })
      },
      onerror: function () { console.log('An error occured while loading OAuth2 component') },
      timeout: 5000,
      ontimeout: function () { console.log('OAuth2 could not be loaded within 5s ...') }
    })
  }
  _onOAuth2ModuleLoaded () {
    // console.log('OAuth2 component loaded')
    this.authInfo = {}
    let self = this
    // Listen currentUser changes & trigger first event
    this.gapi.auth2.getAuthInstance().currentUser.listen(self._onUserUpdated.bind(self))
    this._onUserUpdated(this.gapi.auth2.getAuthInstance().currentUser.get())
    this.isServiceInitialized = true
    // Launch external init callback
    if (this.initcallback) { this.initcallback(this.isServiceInitialized) }
  }
  _onUserUpdated (user) {
    // console.log('_onUserUpdated ', user)
    let previousAuthInfo = this.authInfo
    this.authInfo = {}
    if (user && user.getBasicProfile()) {
      this.user = user
      this.authInfo['email'] = user.getBasicProfile().getEmail()
    } else {
      console.log('User has no basic profile ', user)
    }
    if (previousAuthInfo) {
      if (previousAuthInfo['uid']) { this.authInfo['uid'] = previousAuthInfo['uid'] }
      if (previousAuthInfo['authcode']) { this.authInfo['authcode'] = previousAuthInfo['authcode'] }
    }
    this._checkState()
  }
  _checkState () {
    // console.log('_checkState authInfo', JSON.stringify(this.authInfo))
    if (this.authInfo['authcode']) {
      // console.log('authcode share step')
      if (this.authInfo['email']) {
        // console.log('Status: authcode share step (sending authorization to backend)')
        this._sendAuthCodeToBackEnd()
      }
      // else { console.log('Status: authcode share step (waiting for user profile update)') }
    } else {
      this._loadLocalStorage()
      // console.log('loaded authInfo', JSON.stringify(this.authInfo))
      this._isSessionAlive()
      // if (!this.authInfo['uid'] && !this.authInfo['email']) {
      //   console.log('Status: signed out')
      // } else if (this.authInfo['uid'] && this.authInfo['email'] && this._isSessionAlive()) {
      //   console.log('Status: signed in (session with backend is alive)')
      // }
    }
  }
  // Backend access methods
  _sendAuthCodeToBackEnd () {
    // console.log('_sendAuthCodeToBackEnd', this.authInfo)
    if (this.authInfo && this.authInfo['authcode']) {
      let self = this
      // Performing a POST request
      this.axios.post(this.serviceprops['authURL'], self.authInfo).then(
        function (response) {
          if (response.data) {
            // console.log('signIn OK')
            self.authInfo['uid'] = response.data.uid
            self.authInfo['authcode'] = null
            self._updateLocalStorage()
            self._setSignedIn()
            self.authovercallback('success')
          } else {
            self.signOut()
            self.authovercallback('signIn KO - clearing data & signing out' + JSON.stringify(response))
          }
        },
        function (error) {
          self.signOut(error)
          self.authovercallback('An error occured while sending authcode to backend - clearing data & signing out ', error)
        }
      )
    } else { console.log('Could not send authcode to backend as it\'s emtpy') }
  }
  _isSessionAlive () {
    // console.log('_isSessionAlive ', this.authInfo)
    let alive = false
    if (this.authInfo && this.authInfo['uid'] && this.authInfo['email']) {
      let self = this
      // Performing a POST request
      this.axios.post(this.serviceprops['aliveURL'], self.authInfo).then(
        function (response) {
          if (!response.data) {
            // console.log('session is not alive - clearing data & signing out')
            self.signOut()
          } else {
            // console.log('session is alive')
            self._setSignedIn()
          }
        }, function (error) { console.error('An error occured while prompting backend for session status ', error) }
      )
    }
    return alive
  }
  _setSignedIn () {
    this.isSignedIn = true
    // Launch external signed in callback
    if (this.signedinchangedcallback) { this.signedinchangedcallback(this.isSignedIn) }
  }
  _setSignedOut () {
    this.isSignedIn = false
    // Launch external signed in callback
    if (this.signedinchangedcallback) { this.signedinchangedcallback(this.isSignedIn) }
  }
  // Storage management
  _clearLocalStorage () {
    // console.log('Clearing local storage')
    if (window.localStorage) {
      window.localStorage.removeItem('uid')
      window.localStorage.removeItem('authcode')
      // console.log(JSON.stringify(window.localStorage))
    }
  }
  _loadLocalStorage () {
    // console.log('Loading local storage')
    if (window.localStorage) {
      if (window.localStorage.getItem('uid')) { this.authInfo['uid'] = window.localStorage.getItem('uid') }
      if (window.localStorage.getItem('authcode')) { this.authInfo['authcode'] = window.localStorage.getItem('authcode') }
      // console.log(JSON.stringify(window.localStorage))
    }
  }
  _updateLocalStorage () {
    // console.log('Updating local storage')
    if (window.localStorage && this.authInfo) {
      if (this.authInfo['uid']) { window.localStorage.setItem('uid', this.authInfo['uid']) }
      if (this.authInfo['authcode']) { window.localStorage.setItem('authcode', this.authInfo['authcode']) }
      // console.log(JSON.stringify(window.localStorage))
    }
  }
}
let authService = new AuthService()
export { authService }
