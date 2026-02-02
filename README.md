🧱 Architecture & Design Patterns
Le projet a évolué d'un stockage JSON vers une architecture robuste organisée en couches:
DAO (Data Access Object) : Abstraction de la persistance pour les tables Client, Prospect, Adresse et Contrat.
Singleton : Instance unique de connexion à la base de données MySQL.
Factory & Abstract Factory : Gestion multi-sources (MySQL par défaut, MongoDB/Fichiers en option).
Généricité & Polymorphisme : Optimisation des composants d'accès aux données.

🚀 Fonctionnalités
Gestion des Clients & Prospects : CRUD complet avec suppression des collections au profit de la base de données.
Gestion des Contrats : Recherche spécifique par identifiant client (findByIdClient).
Sécurité : Utilisation systématique des PreparedStatement contre les injections SQL.
Intégrité : Gestion des transactions SQL, notamment pour les opérations de suppression.

🗂 Structure du Projet
📁 /src/fr/cda/java/AccesDonnees : Cœur de la logique DAO (Singleton, Interfaces, Factory).
📁 /ressources/data/scriptsMySql : Scripts SQL d'initialisation de la base.
📁 /ressources/Javadoc : Documentation technique complète du projet.
📁 /Logs : Trçabilité des erreurs d'accès aux données.

▶️ Lancement & Configuration
Base de Données : Importer le script SQL dans une instance MySQL.
Identifiants : Les ID métiers sont désormais de type Integer.
Exécution : Lancer la classe Accueil depuis IntelliJ.

🧪 Règles & Contraintes Techniques
Git : Utilisation de branches dédiées et normalisation des messages de commit.
Validation : Contrôles par REGEX et gestion stricte des champs obligatoires.
Logs : Journalisation des exceptions liées à la persistance.

🛠️ Note technique
Ce projet ECF utilise actuellement Swing pour l’interface, avec la logique de contrôle intégrée à la vue, conformément aux exigences de l’ECF.
L’architecture est solide : Abstract Factory, DAO interfaces, gestion centralisée des erreurs.
Certaines fonctionnalités (mode dégradé, internationalisation complète, tests d’intégration) ont été préparées architecturally et sont maquetées dans le code, mais leur implémentation détaillée reste à finaliser.
Une migration vers React est prévue pour améliorer la séparation de la logique métier, la modularité et l’expérience utilisateur.
