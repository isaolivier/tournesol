<template>
    <el-autocomplete
            class="input-icon address"
            v-model="fullAddress"
            :fetch-suggestions="searchPlaces"
            placeholder="Saisir une adresse"
            @select="handlePlaceSelect"></el-autocomplete>
</template>

<script>

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
        this.fullAddress = this.adresse.numero + ' ' +
          this.adresse.voie + ', ' +
          this.adresse.codePostal + ' ' +
          this.adresse.commune
        this.$emit('fullAddress', this.fullAddress)
      }
    },
    methods: {
      searchPlaces (queryString, cb) {
        let google = window.google || {}

        let displaySuggestions = function (predictions, status) {
          var results = []
          if (status !== google.maps.places.PlacesServiceStatus.OK) {
            cb(status)
            return
          }
          predictions.forEach(function (prediction) {
            results.push({'value': prediction.description, 'place': prediction.place_id})
          })
          cb(results)
        }

        if (queryString && queryString.length > 3) {
          console.log('Search places for \'' + queryString + '\'')
          let service = new google.maps.places.AutocompleteService()
          service.getPlacePredictions({input: queryString, componentRestrictions: {country: 'fr'}}, displaySuggestions)
        } else {
          cb([])
        }
      },
      handlePlaceSelect (place) {
        this.$emit('select', place)
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .el-autocomplete {
        width: 100%;
    }

    .input-icon.address:after {
        content: '\f041';
    }

    .input-icon:after {
        font-family: 'FontAwesome';
        position: absolute;
        right: 10px;
        top: 5px;
        font-size: 1.8em;
        color: #CCC
    }
</style>
