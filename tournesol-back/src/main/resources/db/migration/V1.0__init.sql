create table adresse (
  id bigint not null,
  code_postal varchar(255),
  commune varchar(255),
  latitude double,
  longitude double,
  numero varchar(255),
  place_id varchar(255),
  voie varchar(255),
  primary key (id));

create table appareil (
  id bigint not null,
  denomination varchar(255),
  marque varchar(255),
  numero_serie varchar(255),
  client_id varchar(255),
  date_installation date,
  date_mise_en_service date,
  adresse_id bigint,
  primary key (id));

create table client (
  id bigint not null,
  societe varchar(255),
  civilite varchar(255),
  nom varchar(255),
  email varchar(255),
  portable varchar(255),
  telephone varchar(255),
  note integer,
  adresse_id bigint,
  primary key (id));

create table entreprise (
  id bigint not null,
  nom varchar(255),
  siret varchar(255),
  heure_ouverture time,
  heure_fermeture time,
  jours_ouverture tinyint,
  search_days integer,
  search_distance integer,
  temps_rdv integer,
  time_step integer,
  primary key (id));

create table rendezvous (
  id bigint not null,
  event_cal_id varchar(255),
  appareil_id bigint,
  client_id bigint,
  primary key (id));