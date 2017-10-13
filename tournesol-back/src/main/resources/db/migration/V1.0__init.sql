create sequence adresse_sequence;
create table adresse (
  id bigint not null default adresse_sequence.nextval primary key,
  code_postal varchar(255),
  commune varchar(255),
  latitude double,
  longitude double,
  numero varchar(255),
  place_id varchar(255),
  voie varchar(255));


create sequence appareil_sequence;
create table appareil (
  id bigint not null default appareil_sequence.nextval primary key,
  denomination varchar(255),
  marque varchar(255),
  numero_serie varchar(255),
  client_id varchar(255),
  date_installation date,
  date_mise_en_service date,
  adresse_id bigint);


create sequence client_sequence;
create table client (
  id bigint not null default client_sequence.nextval primary key,
  societe varchar(255),
  civilite varchar(255),
  nom varchar(255),
  email varchar(255),
  portable varchar(255),
  telephone varchar(255),
  note integer,
  adresse_id bigint);


create sequence entreprise_sequence;
create table entreprise (
  id bigint not null default entreprise_sequence.nextval primary key,
  nom varchar(255),
  siret varchar(255),
  heure_ouverture time,
  heure_fermeture time,
  jours_ouverture tinyint,
  search_days integer,
  search_distance integer,
  temps_rdv integer,
  time_step integer);


create sequence rendezvous_sequence;
create table rendezvous (
  id bigint not null default rendezvous_sequence.nextval primary key,
  event_cal_id varchar(255),
  appareil_id bigint,
  client_id bigint);

