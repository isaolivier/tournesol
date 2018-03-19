/**
 * Created by draluy on 03/07/2017.
 */
export default {
  formLabelPosition: 'top',
  tournee: {
    hourInterval: 100 // nb d'intervalles en px entre 2 heures
  },
  back: {
    hostname: process.env.URL_BACK
  },
  rdv: {
    dateFormat: 'YYYY-MM-DD', // Format de date envoyé au back
    fullDateFormat: 'ddd DD MMMM YYYY',
    rayons: [5, 10, 20, 50] // Différents rayons de recherche
  }
}
