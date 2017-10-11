/**
 * Created by draluy on 03/07/2017.
 */

export default{
  tournee: {
    hourInterval: 100 // nb d'intervalles en px entre 2 heures
  },
  back: {
    hostname: 'http://localhost:8081'
  },
  rdv: {
    dateFormat: 'YYYY-MM-DD', // Format de date envoyé au back
    rayons: [5, 10, 20, 50] // Différents rayons de recherche
  }
}
