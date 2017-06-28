insert into adresse(id, numero_voie, code_postal) values (1, '1 rue du Géneral Sarrail', '33400');
insert into adresse(id, numero_voie, code_postal) values (2, '58 route de Bordeaux', '33220');
insert into adresse(id, numero_voie, code_postal) values (3, '35 lotissement Saussus', '33250');
insert into adresse(id, numero_voie, code_postal) values (4, '6 rue de Lyon', '33700');

insert into client(id, civilite, nom, telephone, portable, email, adresse_id) values (1, 'Mme', 'LAPELLEGERIE Christelle', '', '', 'christellelapellegerie@yahoo.fr',1);
insert into client(id, civilite, nom, telephone, portable, email, adresse_id) values (2, 'Mr', 'FAUVET Jérome', '05 53 24 23 23', '', 'jerome.fauvet@cobatri.com',2);
insert into client(id, civilite, nom, telephone, portable, email, adresse_id) values (3, 'MrMme', 'BISSERIEX', '', '', '',3);
insert into client(id, civilite, nom, telephone, portable, email, adresse_id) values (4, 'MrMme', 'DESCORPS', '', '06 19 76 29 86', 'descorps@free.fr',4);