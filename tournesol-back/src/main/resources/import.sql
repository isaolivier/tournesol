insert into adresse(id, numero, voie, code_postal, commune) values (1000, '1', 'rue du Général Sarrail', '33400', 'Talence');
insert into adresse(id, numero, voie, code_postal, commune) values (1001, '58', 'route de Bordeaux', '33220', 'Bordeaux');
insert into adresse(id, numero, voie, code_postal, commune) values (1002, '35', 'lotissement Saussus', '33250', 'Bordeaux');
insert into adresse(id, numero, voie, code_postal, commune) values (1003, '6',  'rue de Lyon', '33700', 'Mérignac');

insert into client(id, civilite, nom, telephone, portable, email, adresse_id, note) values (1000, 'Mme', 'LAPELLEGERIE Christelle', '0556212121', '0612121212', 'christellelapellegerie@yahoo.fr',1000, 3);
insert into client(id, civilite, nom, telephone, portable, email, adresse_id, note) values (1001, 'Mr', 'FAUVET Jérome', '0553242323', '', 'jerome.fauvet@cobatri.com',1001, 2);
insert into client(id, civilite, nom, telephone, portable, email, adresse_id, note) values (1002, 'MrMme', 'BISSERIEX', '', '', '',1002, 5);
insert into client(id, civilite, nom, telephone, portable, email, adresse_id, note) values (1003, 'MrMme', 'DESCORPS', '', '0619762986', 'descorps@free.fr',1003, 1);


insert into appareil(id, denomination, marque, client_id) values (1000, 'Poêle à granulés', 'Jolly Mec', 1000);
insert into appareil(id, denomination, marque, client_id) values (1001, 'Chaudière Fuel', 'Palazetti', 1001);

insert into rendezvous(id, event_cal_id, client_id, appareil_id) values (1000, '1bikrj8dcpv29qse91jhghcirf@google.com', 1000, 1000);
insert into rendezvous(id, event_cal_id, client_id, appareil_id) values (1001, '07uivgj6fsimjr813hulhubv6c@google.com', 1002, null);
insert into rendezvous(id, event_cal_id, client_id, appareil_id) values (1002, 'deui79ic3lgc577p5gl6q05mgs@google.com', 1003, null);
insert into rendezvous(id, event_cal_id, client_id, appareil_id) values (1003, 'rdv2', 1001, 1001);
