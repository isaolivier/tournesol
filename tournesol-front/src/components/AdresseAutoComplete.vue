<template>
    <el-autocomplete
            class="input-icon address"
            v-model="fullAddress"
            :fetch-suggestions="searchPlaces"
            placeholder="Saisir une adresse"
            @select="handlePlaceSelect" style="width:100%"></el-autocomplete>
</template>

<script>
  import {PlaceBean} from '../bean/PlaceBean'

  export default {
    name: 'adresse-autocomplete',
    props: {
      adresse: null
    },
    data () {
      return {
        fullAddress: ''
      }
    },
    created () {
      if (this.adresse) {
        let numero = this.adresse.numero ? this.adresse.numero + ' ' : ''
        let voie = this.adresse.voie ? this.adresse.voie + ', ' : ''
        let codePostal = this.adresse.codePostal ? this.adresse.codePostal + ' ' : ''
        let commune = this.adresse.commune ? this.adresse.commune : ''
        this.fullAddress = numero + voie + codePostal + commune
        this.$emit('fullAddress', this.fullAddress)
      }
    },
    methods: {
      searchPlaces (queryString, cb) {
        let google = window.google || {}

        let displaySuggestions = function (predictions, status) {
          let results = []
          if (status === google.maps.places.PlacesServiceStatus.OK) {
            predictions.forEach(function (prediction) {
              results.push(new PlaceBean(prediction.description, prediction.place_id))
            })
          }

          if (results.length === 0) {
            this.$emit('select', new PlaceBean(queryString, null))
          }
          if (results.length === 1) {
            this.$emit('select', results[0])
          }
          cb(results)
        }.bind(this)

        if (queryString && queryString.length > 3) {
          // console.log('Search places for \'' + queryString + '\'')
          let service = new google.maps.places.AutocompleteService()
          service.getPlacePredictions({input: queryString, componentRestrictions: {country: 'fr'}}, displaySuggestions)
        } else {
          this.$emit('select', {'value': queryString, 'place': null})
          cb([])
        }
      },
      handlePlaceSelect (place) {
        // console.log('selected')
        this.$emit('select', place)
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
    .input-icon.address:after {
        content: '\f041'
    }
</style>
