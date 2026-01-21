CREATE TABLE Adresse(
   Id_Adresse INT AUTO_INCREMENT,
   numeroDeRue VARCHAR(3) NOT NULL,
   nomDeRue VARCHAR(50) NOT NULL,
   codePostal CHAR(5) NOT NULL,
   ville VARCHAR(50) NOT NULL,
   PRIMARY KEY(Id_Adresse)
);

CREATE TABLE Client(
   Id_Client INT AUTO_INCREMENT,
   raisonSocialeClient VARCHAR(50) NOT NULL,
   telephoneClient VARCHAR(10) NOT NULL,
   adresseMailClient VARCHAR(100) NOT NULL,
   commentaire VARCHAR(255),
   chiffreAffaire BIGINT,
   nombreEmployes INT NOT NULL,
   Id_Adresse INT NOT NULL,
   PRIMARY KEY(Id_Client),
   UNIQUE(raisonSocialeClient),
   FOREIGN KEY(Id_Adresse) REFERENCES Adresse(Id_Adresse)
);

CREATE TABLE Prospect(
   Id_Prospect INT AUTO_INCREMENT,
   raisonSocialeProspect VARCHAR(50) NOT NULL,
   telephoneProspect VARCHAR(10) NOT NULL,
   adresseMailProspect VARCHAR(100) NOT NULL,
   commentaire VARCHAR(255),
   dateProspection DATE NOT NULL,
   interet BOOLEAN,
   Id_Adresse INT NOT NULL,
   PRIMARY KEY(Id_Prospect),
   UNIQUE(raisonSocialeProspect),
   FOREIGN KEY(Id_Adresse) REFERENCES Adresse(Id_Adresse)
);

CREATE TABLE Contrat(
   Id_Contrat INT AUTO_INCREMENT,
   montantContrat DECIMAL(6,2) NOT NULL,
   nomContrat VARCHAR(50) NOT NULL,
   Id_Client INT NOT NULL,
   PRIMARY KEY(Id_Contrat),
   FOREIGN KEY(Id_Client) REFERENCES Client(Id_Client)
);
