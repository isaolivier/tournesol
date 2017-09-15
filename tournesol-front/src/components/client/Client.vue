<template>
    <div class="client">
        <span class="client-nom">{{client.civilite}} <strong>{{client.nom}}</strong></span>
        <div class="edit">
            <span class="fa-stack fa-lg">
                <i class="fa fa-square fa-stack-2x"></i>
                <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
            </span>
        </div>
        <span class="adresse">{{client.adresse.adresse}}{{client.adresse.codePostal}}{{client.adresse.commune}}</span>
        <span v-if="client.telephone" class="telephone"><span class="clickable"><i class="fa fa-phone"></i> {{client.telephone}}</span></span>
        <span v-if="client.portable" class="portable">
            <span class="clickable"><i class="fa fa-mobile"></i> {{client.portable}}</span>
            <span class="fa-stack icone-chat">
                <i class="fa fa-square fa-stack-2x"></i>
                <i class="fa fa-comment-o fa-stack-1x fa-inverse"></i>
            </span>
        </span>
        <span v-if="client.email" class="mail"><span class="clickable"><i class="fa fa-envelope-o"></i> {{client.email}}</span></span>
        <span class="note">
            <i v-for="nb in client.note" class="fa fa-star"></i>
            <i v-for="nb in (5 - client.note)" class="fa fa-star-o"></i>
        </span>
        <rdvForm class="ajouter-rdv" :client="client"></rdvForm>
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
        grid-template-rows: repeat(5, auto-fit);
        grid-template-columns: 2fr 2fr 3em 3em 3em 5fr 50px;
        grid-template-areas: "nomclient nomclient icone-chat . . . edit"
            "adresse adresse adresse adresse adresse adresse edit"
            "telephone telephone portable portable portable portable edit"
            "mail mail mail mail . . ajouter-rdv"
            "etoiles etoiles . . . . ajouter-rdv";
        background-color: #d8cfaf;
        padding: 10px;
        width: 70%;
        margin: 10px auto;
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
        grid-area: etoiles
    }

    .portable {
        grid-area: portable;
    }


    .adresse {
        grid-area: adresse;
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
        background-color: #2C3E50;
        color: white;
        border-radius: 5px;
        padding: 4px 6px;
        margin: 0 0;
        line-height: 2em;
    }
</style>
