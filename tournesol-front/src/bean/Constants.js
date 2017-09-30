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
    timeStep: '00:30', // Steps utilisés pour l'affichage des heures dans le form de prise de rdv
    heureOuverture: '8:00',
    heureFermeture: '19:00',
    tempsRdv: 90, // Temps d'un rdv par défaut en minutes
    dateFormat: 'YYYY-MM-DD', // Format de date envoyé au back
    searchDays: 45, // Nombre de jours dans le futur sur lesquels est effectué la recherche de créneaux disponibles
    searchDistance: 50 // Rayon de recherche en km des rdvs proches
  }
}
