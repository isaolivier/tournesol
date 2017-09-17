<template>
    <div class="client">
        <span class="client-nom">{{client.civilite}} <strong>{{client.nom}}</strong></span>
        <span class="adresse">{{client.adresse.adresse}}{{client.adresse.codePostal}}{{client.adresse.commune}}</span>
        <span v-if="client.telephone" class="telephone"><span class="clickable"><i class="fa fa-phone"></i> {{client.telephone}}</span></span>
        <span v-if="client.portable" class="portable">
            <span class="clickable"><i class="fa fa-mobile"></i> {{client.portable}}</span>
                <i class="fa fa-comment-o clickable"></i>
        </span>
        <span v-if="client.email" class="mail"><span class="clickable"><i class="fa fa-envelope-o"></i> {{client.email}}</span></span>
        <span class="note">
            <i v-for="nb in client.note" class="fa fa-star"></i>
            <i v-for="nb in (5 - client.note)" class="fa fa-star-o"></i>
        </span>
        <rdv-form class="ajouter-rdv" :client="client"></rdv-form>
        <client-form class="edit clickable" :client="client"></client-form>
    </div>
</template>

<script>
  import ClientForm from './ClientForm.vue'
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
      'client-form': ClientForm,
      'rdv-form': RendezVousForm
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .client {
        display: grid;
        grid-template-rows: repeat(5, minmax(0px, max-content));
        grid-template-columns: 2fr 2fr 3em 3em 3em 5fr 50px;
        grid-template-areas: "nomclient nomclient icone-chat . . . edit"
            "adresse adresse adresse adresse adresse adresse edit"
            "telephone telephone portable portable portable portable ."
            "mail mail mail mail . . ajouter-rdv"
            "etoiles etoiles . . . . ajouter-rdv";
        padding: 10px;
        width: 70%;
        margin: 10px auto;
        border: solid 1px #D1DBE5;
        border-radius: 0px;
        background-color: #EEF1F6;
        align-items: center
    }

    .fa {
        color: grey
    }

    .client-nom {
        grid-area: nomclient;
    }

    .telephone {
        grid-area: telephone;
    }

    .mail {
        grid-area: mail;
    }

    .note {
        grid-area: etoiles;
        padding: 4px 6px 0px 6px;
    }

    .portable {
        grid-area: portable;
    }


    .adresse {
        grid-area: adresse;
        padding: 2px 6px;
    }

    .icone-chat {
        grid-area: icone-chat;
    }

    .edit {
        grid-area: edit;
    }

    .ajouter-rdv {
        grid-area: ajouter-rdv;
    }

    .clickable {
        display: inline-block;
        border: solid 1px grey;
        border-radius: 5px;
        padding: 2px 6px;
        margin: 4px 0;
    }
</style>
