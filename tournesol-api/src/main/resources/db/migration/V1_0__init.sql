/*CREATE TABLE ENTREPRISE (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY,
	nom varchar(255) not null,
	siret varchar(255) not null
);

CREATE TABLE CLIENT (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY,
	nom varchar(255) not null,
	telephone varchar(255) not null,
	portable varchar(255),
	email varchar(255),
	vote INT(5)
);

CREATE TABLE APPAREIL (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY,
	client_id BIGINT,
	adresse varchar(255) not null,
	marque varchar(255) not null,
	numeroSerie varchar(255) not null,
	date_installation DATE,
	date_mise_en_service DATE,

	FOREIGN KEY (client_id) REFERENCES CLIENT(id)
);*/