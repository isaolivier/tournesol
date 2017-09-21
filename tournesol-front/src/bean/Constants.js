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
    heuresOuverture: [8, 19], // Heures d'ouverture 8h -> 19h
    tempsRdv: 90, // Temps d'un rdv par défaut en minutes
    dateFormat: 'YYYY-MM-DD'
  }
}
