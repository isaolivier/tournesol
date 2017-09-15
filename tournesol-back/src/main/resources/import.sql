insert into adresse(id, adresse, code_postal, commune) values (1, '1 rue du Général Sarrail', '33400', 'Talence');
insert into adresse(id, adresse, code_postal, commune) values (2, '58 route de Bordeaux', '33220', 'Bordeaux');
insert into adresse(id, adresse, code_postal, commune) values (3, '35 lotissement Saussus', '33250', 'Bordeaux');
insert into adresse(id, adresse, code_postal, commune) values (4, '6 rue de Lyon', '33700', 'Mérignac');

insert into client(id, civilite, nom, telephone, portable, email, adresse_id, note) values (1, 'Mme', 'LAPELLEGERIE Christelle', '05 56 21 21 21', '06 12 12 12 12', 'christellelapellegerie@yahoo.fr',1, 3);
insert into client(id, civilite, nom, telephone, portable, email, adresse_id, note) values (2, 'Mr', 'FAUVET Jérome', '05 53 24 23 23', '', 'jerome.fauvet@cobatri.com',2, 2);
insert into client(id, civilite, nom, telephone, portable, email, adresse_id, note) values (3, 'MrMme', 'BISSERIEX', '', '', '',3, 5);
insert into client(id, civilite, nom, telephone, portable, email, adresse_id, note) values (4, 'MrMme', 'DESCORPS', '', '06 19 76 29 86', 'descorps@free.fr',4, 1);


insert into appareil(id, denomination, marque, client_id) values (1, 'Poêle à granulés', 'Jolly Mec', 1);
insert into appareil(id, denomination, marque, client_id) values (2, 'Chaudière Fuel', 'Palazetti', 2);

insert into rendezvous(id, event_cal_id, client_id, appareil_id) values (1000, '1bikrj8dcpv29qse91jhghcirf@google.com', 1, 1);
insert into rendezvous(id, event_cal_id, client_id, appareil_id) values (2000, 'rdv2', 2, 2);
