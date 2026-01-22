-- ==========================================================
-- SCRIPT DE CRÉATION ET DE PEUPLEMENT - VERSION OPTIMISÉE
-- Correction : Suppression des IDs manuels pour laisser
-- l'AUTO_INCREMENT gérer la numérotation naturellement.
-- ==========================================================

-- 1. CRÉATION DES TABLES (STRUCTURE)
-- ----------------------------------------------------------

DROP TABLE IF EXISTS Contrat;
DROP TABLE IF EXISTS Prospect;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Adresse;

CREATE TABLE Adresse
(
    Id_Adresse  INT AUTO_INCREMENT,
    numeroDeRue VARCHAR(10) NOT NULL,
    nomDeRue    VARCHAR(50) NOT NULL,
    codePostal  CHAR(5)     NOT NULL,
    ville       VARCHAR(50) NOT NULL,
    PRIMARY KEY (Id_Adresse)
) ENGINE=InnoDB;

CREATE TABLE Client
(
    Id_Client           INT AUTO_INCREMENT,
    raisonSocialeClient VARCHAR(50)  NOT NULL,
    telephoneClient     VARCHAR(10)  NOT NULL,
    adresseMailClient   VARCHAR(100) NOT NULL,
    commentaire         VARCHAR(255),
    chiffreAffaire      BIGINT,
    nombreEmployes      INT          NOT NULL,
    Id_Adresse          INT          NOT NULL,
    PRIMARY KEY (Id_Client),
    UNIQUE (raisonSocialeClient),
    FOREIGN KEY (Id_Adresse) REFERENCES Adresse (Id_Adresse)
) ENGINE=InnoDB;

CREATE TABLE Prospect
(
    Id_Prospect           INT AUTO_INCREMENT,
    raisonSocialeProspect VARCHAR(50)  NOT NULL,
    telephoneProspect     VARCHAR(10)  NOT NULL,
    adresseMailProspect   VARCHAR(100) NOT NULL,
    commentaire           VARCHAR(255),
    dateProspection       DATE         NOT NULL,
    interet               BOOLEAN,
    Id_Adresse            INT          NOT NULL,
    PRIMARY KEY (Id_Prospect),
    UNIQUE (raisonSocialeProspect),
    FOREIGN KEY (Id_Adresse) REFERENCES Adresse (Id_Adresse)
) ENGINE=InnoDB;

CREATE TABLE Contrat
(
    Id_Contrat     INT AUTO_INCREMENT,
    montantContrat DECIMAL(10, 2) NOT NULL,
    nomContrat     VARCHAR(50)    NOT NULL,
    Id_Client      INT            NOT NULL,
    PRIMARY KEY (Id_Contrat),
    FOREIGN KEY (Id_Client) REFERENCES Client (Id_Client)
) ENGINE=InnoDB;

-- 2. INSERTION DES DONNÉES D'ADRESSE
-- On retire la colonne Id_Adresse. L'ordre d'insertion définit l'ID (1, 2, 3...).
-- ----------------------------------------------------------

INSERT INTO Adresse (numeroDeRue, nomDeRue, codePostal, ville)
VALUES ('890', 'Fifth Avenue', '10001', 'New York (Terre)'),                -- ID 1 (Ex-101)
       ('Zone C', 'Cranial Cortex', '99999', 'Knowhere'),                   -- ID 2 (Ex-102)
       ('1', 'Rainbow Bridge Road', '00009', 'Asgard City'),                -- ID 3 (Ex-103)
       ('42', 'Panther Plaza', '54321', 'Birnin Zana (Wakanda)'),           -- ID 4 (Ex-104)
       ('12', 'Nova Prime Avenue', '10101', 'Xandar Prime'),                -- ID 5 (Ex-105)
       ('666', 'Contest Boulevard', '88888', 'Sakaar City'),                -- ID 6 (Ex-106)
       ('77', 'Lowtown Alley', '98000', 'Madripoor'),                       -- ID 7 (Ex-107)
       ('Sector 9', 'Thanos Origin Place', '99001', 'Titan (Ruines)'),      -- ID 8 (Ex-108)
       ('Alpha-1', 'Supreme Intelligence Way', '99002', 'Kree-Lar (Hala)'), -- ID 9 (Ex-109)
       ('Block A', 'Panopticon Circle', '99003', 'The Kyln'),               -- ID 10 (Ex-110)
       ('10', 'Treasure Island Road', '94130', 'San Francisco (Terre)'),    -- ID 11 (Ex-201)
       ('1', 'Gold Chamber', '77777', 'Sovereign'),                         -- ID 12 (Ex-202)
       ('0', 'Mountain Top', '66600', 'Vormir'),                            -- ID 13 (Ex-203)
       ('1', 'Brain Center', '44444', 'Ego (La Planète Vivante)'),          -- ID 14 (Ex-204)
       ('7', 'Temple Way', '11111', 'Katmandou (Terre)'),                   -- ID 15 (Ex-205)
       ('200', 'Avenue of the Americas', '10019', 'New York (Terre)'),      -- ID 16 (Ex-206)
       ('99', 'Frost Giant Valley', '22222', 'Jotunheim'),                  -- ID 17 (Ex-207)
       ('0.001', 'Axia Central', '00001', 'Axia (Royaume Quantique)'),      -- ID 18 (Ex-208)
       ('1', 'Doomstadt Square', '99900', 'Doomstadt (Latveria)'),          -- ID 19 (Ex-209)
       ('888', 'Hidden Mountain', '88800', 'Ta Lo')                         -- ID 20 (Ex-210)
    AS n
ON DUPLICATE KEY
UPDATE
    numeroDeRue=n.numeroDeRue, nomDeRue=n.nomDeRue,
    codePostal=n.codePostal, ville=n.ville;

-- 3. INSERTION DES CLIENTS
-- Id_Adresse mis à jour pour correspondre aux nouveaux IDs (1 à 10)
-- ----------------------------------------------------------

INSERT INTO Client (raisonSocialeClient, telephoneClient, adresseMailClient, commentaire,
                    chiffreAffaire, nombreEmployes, Id_Adresse)
VALUES ('Stark Biotech (Labo Expérimental)', '0491010101', 'research@stark-bio.tech',
        'Petit laboratoire de recherche.', 245000, 4, 1),
       ('Knowhere Mining Corp.', '0140020202', 'collector@knowhere.space',
        'Opération d''extraction de fluide spinal.', 205000, 3, 2),
       ('Asgardian Forges', '0607030303', 'smith@nidavellir.realms', 'Atelier de forge légendaire.',
        180000, 2, 3),
       ('Wakanda Logistics', '0388040404', 'shuri@wakanda.gov',
        'Transport rapide via Maglev Vibranium.', 230000, 5, 4),
       ('Xandar Garden Care', '0561050505', 'nova@xandar.corps', 'Entretien des espaces verts.',
        195000, 2, 5),
       ('Sakaar Scrappers', '0478060606', 'grandmaster@sakaar.arena',
        'Récupération de déchets spatiaux.', 215000, 3, 6),
       ('Madripoor Imports', '0383070707', 'broker@madripoor.low', 'Échange de marchandises rares.',
        250000, 5, 7),
       ('Titan Salvage', '0556080808', 'survivor@titan.ruins', 'Récupération de technologies.',
        175000, 2, 8),
       ('Hala Tech Research', '0240090909', 'science@kree.empire', 'Recherche Kree.', 220000, 3, 9),
       ('Kyln Security Solutions', '0320101010', 'warden@kyln.prison',
        'Systèmes de verrouillage de prison.', 190000, 5, 10) AS c
ON DUPLICATE KEY
UPDATE
    telephoneClient= c.telephoneClient, adresseMailClient= c.adresseMailClient,
    commentaire= c.commentaire, chiffreAffaire= c.chiffreAffaire,
    nombreEmployes= c.nombreEmployes, Id_Adresse= c.Id_Adresse;

-- 4. INSERTION DES PROSPECTS
-- Id_Adresse mis à jour pour correspondre aux nouveaux IDs (11 à 20)
-- Note: Les ID Prospects commenceront maintenant à 1, car c'est une nouvelle table.
-- ----------------------------------------------------------

INSERT INTO Prospect (raisonSocialeProspect, telephoneProspect, adresseMailProspect, commentaire,
                      dateProspection, interet, Id_Adresse)
VALUES ('Pym Technologies', '0491111111', 'hank@pym.tech', 'Start-up particules subatomiques.',
        '2025-11-15', 1, 11),
       ('Sovereign Batteries', '0140121212', 'ayesha@sovereign.gold',
        'Transporteur d''énergie Anulax.', '2025-10-01', 1, 12),
       ('Vormir Soul Seekers', '0607131313', 'keeper@vormir.stone', 'Groupe de pèlerins.',
        '2025-09-20', NULL, 13),
       ('Ego Terraforming', '0388141414', 'peter@ego.dad', 'Opérateur d''expansion planétaire.',
        '2025-11-10', 0, 14),
       ('Kamar-Taj Wifi', '0561151515', 'wong@sanctum.magic', 'Réseau pour sorciers.', '2025-11-18',
        1, 15),
       ('Oscorp Genetics', '0478161616', 'norman@oscorp.evil', 'Labo de génétique croisée.',
        '2025-10-25', NULL, 16),
       ('Jotunheim Ice Storage', '0383171717', 'laufey@jotun.ice', 'Entrepôt frigorifique.',
        '2025-08-01', 0, 17),
       ('Quantum Realm Explorers', '0556181818', 'scott@quantum.micro', 'Voyage microscopique.',
        '2025-11-05', 1, 18),
       ('Latveria School of Bots', '0240191919', 'doom@latveria.gov', 'Formation robotique.',
        '2025-09-10', NULL, 19),
       ('Ten Rings Dojo', '0320202020', 'wenwu@tenrings.org', 'Organisation arts martiaux.',
        '2025-11-20', 1, 20) AS p
ON DUPLICATE KEY
UPDATE
    telephoneProspect=p.telephoneProspect, adresseMailProspect=p.adresseMailProspect,
    commentaire=p.commentaire, dateProspection=p.dateProspection,
    interet=p.interet, Id_Adresse=p.Id_Adresse;

-- 5. INSERTION DES CONTRATS
-- Id_Client correspond aux IDs générés en étape 3 (1 à 10)
-- ----------------------------------------------------------

INSERT INTO Contrat (montantContrat, nomContrat, Id_Client)
VALUES (1250.00, 'Licence IA J.A.R.V.I.S T1', 1),
       (950.00, 'Maintenance Bras Robotique', 1),
       (1500.00, 'Support Scaphandres Cosmo', 2),
       (800.00, 'Audit Sécurité Musée', 2),
       (500.00, 'Formation Soins Alien', 2),
       (1100.00, 'Licence Gestion Chaleur Étoile', 3),
       (580.00, 'Maintenance Navette Talon', 4),
       (250.00, 'Formation Pilotes Kimoyo', 4),
       (1400.00, 'Gestion Flore Exotique', 5),
       (300.00, 'Optimisation Photosynthèse', 5),
       (500.00, 'Support Worldmind', 5),
       (1350.00, 'Sécurité Portail Dimensionnel', 6),
       (900.00, 'Conseil Conformité Douanière', 7),
       (750.00, 'Logiciel Cryptage Flux', 7),
       (1200.00, 'Analyse Structurelle Ruines', 8),
       (280.00, 'Support Drone Exploration', 8),
       (1150.00, 'Logiciel Analyse Génome', 9),
       (1100.00, 'Système Verrouillage V2', 10),
       (850.00, 'Formation Gardiens T2', 10) AS ct
ON DUPLICATE KEY
UPDATE
    montantContrat=ct.montantContrat, nomContrat=ct.nomContrat,
    Id_Client=ct.Id_Client;