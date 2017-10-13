<template>
    <div :class="{'client greybox': true,'collapsed':isCollapsed}" >
        <span class="client-nom" @click="collapse">
            <span v-if="client.societe">Sté <strong>{{client.societe}}</strong> - </span>{{civilite()}} <strong>{{client.nom}}</strong>
            <span v-if="!client.adresse || !client.adresse.placeId" class="client-warning"><i class="fa fa-exclamation-triangle"></i></span>
        </span>

        <span class="adresse" v-if="client.adresse">{{client.adresse.numero}} {{client.adresse.voie}}, {{client.adresse.codePostal}} {{client.adresse.commune}}</span>
        <span v-if="client.telephone" class="telephone"><span class="clickable"><i class="fa fa-phone"></i> {{client.telephone}}</span>
        <span v-if="client.portable" class="portable">
            <span class="clickable"><i class="fa fa-mobile"></i> {{client.portable}}</span>
            <i class="fa fa-comment-o clickable"></i>
        </span>
        </span>
        <span v-if="client.email" class="mail"><span class="clickable"><i class="fa fa-envelope-o"></i> {{client.email}}</span></span>
        <el-rate class="note" v-model="client.note" disabled disabled-void-color="#CCCCCC"></el-rate>
        <rdv-form class="ajouter-rdv" :client="client"></rdv-form>
        <client-form :client="client" @change="emitClientEvent"></client-form>
    </div>
</template>

<script>
  import ClientForm from './form/ClientForm.vue'
  import RendezVousForm from './form/RendezVousForm.vue'

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
      return {
        isCollapsed: true
      }
    },
    components: {
      'client-form': ClientForm,
      'rdv-form': RendezVousForm
    },
    methods: {
      collapse () {
        this.isCollapsed = !this.isCollapsed
      },
      civilite () {
        if (this.client.civilite === 'MrMme') {
          return 'Mr & Mme'
        }
        return this.client.civilite
      },
      emitClientEvent (value) {
        this.$emit(value.operation)
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .client {
        display: grid;
        grid-template-rows: repeat(5, minmax(0px, max-content));
        grid-template-columns: 2fr 2fr 3em 3em 3em 5fr 50px;
        grid-template-areas: "nomclient nomclient nomclient nomclient nomclient nomclient edit"
        "adresse adresse adresse adresse adresse adresse edit"
        "telephone telephone telephone telephone telephone telephone ."
        "mail mail mail mail . . ajouter-rdv"
        "etoiles etoiles . . . . ajouter-rdv";
        padding: 10px;
        width: 70%;
        margin: 10px auto;
        align-items: center;
        line-height: 1.5em;
        transition: all 0.15s ease-out;
    }

    .fa-comment-o{
        line-height: 1.5em;
    }

    .client.collapsed {
        grid-template-rows: repeat(1, minmax(0px, max-content));
        grid-template-areas: "nomclient nomclient nomclient nomclient nomclient edit ajouter-rdv";
        padding: 0px 10px;
        transition: all 0.15s cubic-bezier(.08,.82,.17,1);
    }

    .client.collapsed .client-nom, .client .client-nom {
        padding-left: 0em;
    }

    .client .client-nom:before {
        font-family: 'FontAwesome';
        content: '\f054';
        justify-self: flex-start;
        padding: 0 1em;
    }

    .client.collapsed .client-nom:before {
        font-family: 'FontAwesome';
        content: '\f078';
        justify-self: flex-start;
        padding: 0 1em;
    }



    .client.collapsed .adresse,
    .client.collapsed .mail,
    .client.collapsed .telephone,
    .client.collapsed .portable,
    .client.collapsed .note {
        display: none;
    }

    .fa {
        color: grey
    }

    .client-nom {
        grid-area: nomclient;
    }

    .client-warning .fa {
        color: #FFD82F;
        margin-left: 5px;
    }

    .telephone {
        grid-area: telephone;
    }

    .portable {
        margin-left: 1em;
    }

    .mail {
        grid-area: mail;
    }

    .note {
        grid-area: etoiles;
        padding: 4px 6px 0px 6px;
    }

    .adresse {
        grid-area: adresse;
        padding: 2px 6px;
    }

    .ajouter-rdv {
        grid-area: ajouter-rdv;
    }


</style>
