-- ==========================================================
-- SCRIPT DE CRÉATION ET DE PEUPLEMENT DE LA BASE DE DONNÉES
-- ==========================================================

-- 1. CRÉATION DES TABLES (STRUCTURE)
-- ----------------------------------------------------------

CREATE TABLE IF NOT EXISTS Adresse(
   Id_Adresse INT AUTO_INCREMENT,
   numeroDeRue VARCHAR(10) NOT NULL, -- Augmenté à 10 pour supporter "Zone C" ou "Sector 9"
   nomDeRue VARCHAR(50) NOT NULL,
   codePostal CHAR(5) NOT NULL,
   ville VARCHAR(50) NOT NULL,
   PRIMARY KEY(Id_Adresse)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Client(
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
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Prospect(
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
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Contrat(
   Id_Contrat INT AUTO_INCREMENT,
   montantContrat DECIMAL(10,2) NOT NULL, -- Augmenté la précision pour plus de sécurité
   nomContrat VARCHAR(50) NOT NULL,
   Id_Client INT NOT NULL,
   PRIMARY KEY(Id_Contrat),
   FOREIGN KEY(Id_Client) REFERENCES Client(Id_Client)
) ENGINE=InnoDB;

-- 2. INSERTION DES DONNÉES D'ADRESSE
-- ----------------------------------------------------------

INSERT INTO Adresse (Id_Adresse, numeroDeRue, nomDeRue, codePostal, ville) VALUES
(101, '890', 'Fifth Avenue', '10001', 'New York (Terre)'),
(102, 'Zone C', 'Cranial Cortex', '99999', 'Knowhere'),
(103, '1', 'Rainbow Bridge Road', '00009', 'Asgard City'),
(104, '42', 'Panther Plaza', '54321', 'Birnin Zana (Wakanda)'),
(105, '12', 'Nova Prime Avenue', '10101', 'Xandar Prime'),
(106, '666', 'Contest Boulevard', '88888', 'Sakaar City'),
(107, '77', 'Lowtown Alley', '98000', 'Madripoor'),
(108, 'Sector 9', 'Thanos Origin Place', '99001', 'Titan (Ruines)'),
(109, 'Alpha-1', 'Supreme Intelligence Way', '99002', 'Kree-Lar (Hala)'),
(110, 'Block A', 'Panopticon Circle', '99003', 'The Kyln'),
(201, '10', 'Treasure Island Road', '94130', 'San Francisco (Terre)'),
(202, '1', 'Gold Chamber', '77777', 'Sovereign'),
(203, '0', 'Mountain Top', '66600', 'Vormir'),
(204, '1', 'Brain Center', '44444', 'Ego (La Planète Vivante)'),
(205, '7', 'Temple Way', '11111', 'Katmandou (Terre)'),
(206, '200', 'Avenue of the Americas', '10019', 'New York (Terre)'),
(207, '99', 'Frost Giant Valley', '22222', 'Jotunheim'),
(208, '0.001', 'Axia Central', '00001', 'Axia (Royaume Quantique)'),
(209, '1', 'Doomstadt Square', '99900', 'Doomstadt (Latveria)'),
(210, '888', 'Hidden Mountain', '88800', 'Ta Lo')
ON DUPLICATE KEY UPDATE 
    numeroDeRue=VALUES(numeroDeRue), nomDeRue=VALUES(nomDeRue), 
    codePostal=VALUES(codePostal), ville=VALUES(ville);

-- 3. INSERTION DES CLIENTS
-- ----------------------------------------------------------

INSERT INTO Client (Id_Client, raisonSocialeClient, telephoneClient, adresseMailClient, commentaire, chiffreAffaire, nombreEmployes, Id_Adresse) VALUES
(1, 'Stark Biotech (Labo Expérimental)', '0491010101', 'research@stark-bio.tech', 'Petit laboratoire de recherche sur le sérum expérimental.', 245000, 4, 101),
(2, 'Knowhere Mining Corp.', '0140020202', 'collector@knowhere.space', 'Opération d''extraction de fluide spinal Céleste.', 205000, 3, 102),
(3, 'Asgardian Forges', '0607030303', 'smith@nidavellir.realms', 'Atelier de forge pour armes légendaires.', 180000, 2, 103),
(4, 'Wakanda Logistics', '0388040404', 'shuri@wakanda.gov', 'Transport rapide via Maglev Vibranium.', 230000, 5, 104),
(5, 'Xandar Garden Care', '0561050505', 'nova@xandar.corps', 'Entretien des espaces verts de la capitale.', 195000, 2, 105),
(6, 'Sakaar Scrappers', '0478060606', 'grandmaster@sakaar.arena', 'Récupération de déchets spatiaux.', 215000, 3, 106),
(7, 'Madripoor Imports', '0383070707', 'broker@madripoor.low', 'Micro-échange de marchandises rares.', 250000, 5, 107),
(8, 'Titan Salvage', '0556080808', 'survivor@titan.ruins', 'Récupération de technologies sur les ruines.', 175000, 2, 108),
(9, 'Hala Tech Research', '0240090909', 'science@kree.empire', 'Petite équipe de recherche Kree.', 220000, 3, 109),
(10, 'Kyln Security Solutions', '0320101010', 'warden@kyln.prison', 'Fournisseur de systèmes de verrouillage pour la prison.', 190000, 5, 110)
ON DUPLICATE KEY UPDATE 
    telephoneClient=VALUES(telephoneClient), adresseMailClient=VALUES(adresseMailClient), 
    commentaire=VALUES(commentaire), chiffreAffaire=VALUES(chiffreAffaire), 
    nombreEmployes=VALUES(nombreEmployes), Id_Adresse=VALUES(Id_Adresse);

-- 4. INSERTION DES PROSPECTS
-- ----------------------------------------------------------

INSERT INTO Prospect (Id_Prospect, raisonSocialeProspect, telephoneProspect, adresseMailProspect, commentaire, dateProspection, interet, Id_Adresse) VALUES
(11, 'Pym Technologies', '0491111111', 'hank@pym.tech', 'Start-up spécialisée en particules subatomiques.', '2025-11-15', 1, 201),
(12, 'Sovereign Batteries', '0140121212', 'ayesha@sovereign.gold', 'Transporteur d''énergie Anulax.', '2025-10-01', 1, 202),
(13, 'Vormir Soul Seekers', '0607131313', 'keeper@vormir.stone', 'Petit groupe de pèlerins.', '2025-09-20', NULL, 203),
(14, 'Ego Terraforming', '0388141414', 'peter@ego.dad', 'Opérateur d''expansion planétaire.', '2025-11-10', 0, 204),
(15, 'Kamar-Taj Wifi', '0561151515', 'wong@sanctum.magic', 'Gestion du réseau pour les sorciers.', '2025-11-18', 1, 205),
(16, 'Oscorp Genetics', '0478161616', 'norman@oscorp.evil', 'Labo de génétique croisée.', '2025-10-25', NULL, 206),
(17, 'Jotunheim Ice Storage', '0383171717', 'laufey@jotun.ice', 'Entrepôt frigorifique.', '2025-08-01', 0, 207),
(18, 'Quantum Realm Explorers', '0556181818', 'scott@quantum.micro', 'Compagnie de voyage microscopique.', '2025-11-05', 1, 208),
(19, 'Latveria School of Bots', '0240191919', 'doom@latveria.gov', 'Centre de formation robotique.', '2025-09-10', NULL, 209),
(20, 'Ten Rings Dojo', '0320202020', 'wenwu@tenrings.org', 'Organisation locale d''arts martiaux.', '2025-11-20', 1, 210)
ON DUPLICATE KEY UPDATE 
    telephoneProspect=VALUES(telephoneProspect), adresseMailProspect=VALUES(adresseMailProspect), 
    commentaire=VALUES(commentaire), dateProspection=VALUES(dateProspection), 
    interet=VALUES(interet), Id_Adresse=VALUES(Id_Adresse);

-- 5. INSERTION DES CONTRATS
-- ----------------------------------------------------------

INSERT INTO Contrat (Id_Contrat, montantContrat, nomContrat, Id_Client) VALUES
(1, 1250.00, 'Licence IA J.A.R.V.I.S T1', 1),
(2, 950.00, 'Maintenance Bras Robotique', 1),
(3, 1500.00, 'Support Scaphandres Cosmo', 2),
(4, 800.00, 'Audit Sécurité Musée', 2),
(5, 500.00, 'Formation Soins Alien', 2),
(6, 1100.00, 'Licence Gestion Chaleur Étoile', 3),
(7, 580.00, 'Maintenance Navette Talon', 4),
(8, 250.00, 'Formation Pilotes Kimoyo', 4),
(9, 1400.00, 'Gestion Flore Exotique', 5),
(10, 300.00, 'Optimisation Photosynthèse', 5),
(11, 500.00, 'Support Worldmind', 5),
(12, 1350.00, 'Sécurité Portail Dimensionnel', 6),
(13, 900.00, 'Conseil Conformité Douanière', 7),
(14, 750.00, 'Logiciel Cryptage Flux', 7),
(15, 1200.00, 'Analyse Structurelle Ruines', 8),
(16, 280.00, 'Support Drone Exploration', 8),
(17, 1150.00, 'Logiciel Analyse Génome', 9),
(18, 1100.00, 'Système Verrouillage V2', 10),
(19, 850.00, 'Formation Gardiens T2', 10)
ON DUPLICATE KEY UPDATE 
    montantContrat=VALUES(montantContrat), nomContrat=VALUES(nomContrat), 
    Id_Client=VALUES(Id_Client);