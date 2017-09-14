<template>
    <div class="client">
        <span class="client-nom">{{client.civilite}} {{client.nom}}</span>
        <i class="fa fa-phone icone-tel"></i>
        <i class="fa fa-envelope-o icone-mail"></i>
        <i class="fa fa-comment-o icone-chat"></i>
        <i class="fa fa-pencil edit"></i>
        <span class="adresse">{{client.adresse.adresse}}{{client.adresse.codePostal}}{{client.adresse.commune}}</span>
        <span class="telephone">{{client.telephone}}</span><span class="portable">{{client.portable}}</span>
        <span class="mail">{{client.email}}</span>
        <span class="note"><i v-for="nb in client.note" class="fa fa-star"></i><i v-for="nb in (5 - client.note)" class="fa fa-star-o"></i></span>
        <span class="ajouter-rdv"><rdvForm :client="client"></rdvForm></span>
    </div>
</template>

<script>
  import RendezVousForm from './RendezVousForm.vue'

  export default {
    name: 'annuaire',
    props: {
      client: {
        type: Object,
        required: true
      },
      rang: {
        type: Number,
        required: true
      }
    },
    data () {
      return {}
    },
    components: {
      'rdvForm': RendezVousForm
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.client {
    display: grid;
    grid-template-rows: 1fr 1fr 1fr 1fr 1fr min-content;
    grid-template-columns: 2fr 2fr 1fr 1fr 1fr 5fr;
    grid-template-areas: "nomclient nomclient icone-tel icone-mail icone-chat edit"
                        "adresse adresse adresse adresse adresse adresse"
                        "telephone telephone portable portable . ."
                        "mail mail mail mail . ."
                        ". . . rdv rdv ajouter-rdv"
                        "etoiles etoiles . . . .";
    background-color: rgba(0, 96, 149, 0.11);
    box-shadow: 0px 0px 5px black;
    border-radius: 5px;
    padding: 10px;
    margin: 10px;

}

.client-nom{
    grid-area: nomclient;
}

.telephone{
    grid-area: telephone;
}

.telephone:before{
    font-family: "FontAwesome";
    content: "\f095";
}

.mail{
    grid-area: mail;
}

.mail:before{
    font-family: "FontAwesome";
    content: "\F003";
}

.note {
    grid-area: etoiles
}

.portable{
    grid-area: portable;
}

.portable:before{
    font-family: "FontAwesome";
    content: "\f10b";
}

.adresse{
    grid-area: adresse;
}

.icone-tel{
    grid-area: icone-tel;
}

.icone-mail{
    grid-area: icone-mail;
}

.icone-chat{
    grid-area: icone-chat;
}

.edit{
    grid-area: edit;
    text-align: right;
}
</style>
