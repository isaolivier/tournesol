insert into adresse(id, numero, voie, code_postal, commune) values (1000, '1', 'rue du Général Sarrail', '33400', 'Talence');
insert into adresse(id, numero, voie, code_postal, commune) values (1001, '58', 'route de Bordeaux', '33220', 'Bordeaux');
insert into adresse(id, numero, voie, code_postal, commune) values (1002, '35', 'lotissement Saussus', '33250', 'Bordeaux');
insert into adresse(id, numero, voie, code_postal, commune) values (1003, '6',  'rue de Lyon', '33700', 'Mérignac');

insert into client(id, civilite, nom, telephone, portable, email, adresse_id, note) values (1000, 'Mme', 'LAPELLEGERIE Christelle', '05 56 21 21 21', '06 12 12 12 12', 'christellelapellegerie@yahoo.fr',1000, 3);
insert into client(id, civilite, nom, telephone, portable, email, adresse_id, note) values (1001, 'Mr', 'FAUVET Jérome', '05 53 24 23 23', '', 'jerome.fauvet@cobatri.com',1001, 2);
insert into client(id, civilite, nom, telephone, portable, email, adresse_id, note) values (1002, 'MrMme', 'BISSERIEX', '', '', '',1002, 5);
insert into client(id, civilite, nom, telephone, portable, email, adresse_id, note) values (1003, 'MrMme', 'DESCORPS', '', '06 19 76 29 86', 'descorps@free.fr',1003, 1);


insert into appareil(id, denomination, marque, client_id) values (1000, 'Poêle à granulés', 'Jolly Mec', 1000);
insert into appareil(id, denomination, marque, client_id) values (1001, 'Chaudière Fuel', 'Palazetti', 1001);

insert into rendezvous(id, event_cal_id, client_id, appareil_id) values (1000, '1bikrj8dcpv29qse91jhghcirf@google.com', 1000, 1000);
insert into rendezvous(id, event_cal_id, client_id, appareil_id) values (1001, 'rdv2', 1001, 1001);
