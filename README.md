Projet : Gestion Commerciale – Java Swing

Application de gestion des clients, prospects et contrats développée en Java 17/25 avec Swing dans le cadre de l’ECF.

🚀 Fonctionnalités

- Gestion des Clients  
  * Lister
  * Afficher
  * Créer / Modifier
  * Supprimer

- Gestion des Prospects
  * Lister
  * Afficher
  * Créer / Modifier
  * Supprimer

- Gestion des Contrats
  * Lister
  * Afficher
  * Créer / Modifier
  * Supprimer

🧱 Architecture / Contraintes techniques
  * Java 17+
  * Interface graphique Swing
  * Stockage Json
  * Collections (ArrayList, HashMap)
  * Logs applicatifs
  * Javadoc générée dans /javadoc
  * Diagrammes UML dans /docs


🗂 Structure du projet

 📁/src
 📁/docs
 📁/logs
 📁/javadoc
 📁/data

/!\ Attention, le projet nécessite la librairie json-20250517.jar /!\


▶️ Lancement du projet
  * Ouvrir le projet dans IntelliJ
  * Lancer la classe Accueil (la classe main sert de zone de testing temporaires pour les dev et le vocabulaire)
  * L’interface Swing s’ouvre automatiquement

📚 Documentation fournie
  * Spécifications techniques : /docs/specifications.md
  * Maquettes : /docs/maquettes/
  * Diagramme de classes : /docs/diagrammes/
  * Javadoc : /javadoc

🧪 Règles fonctionnelles clés
  * ID auto-incrémenté
  * Validations avec REGEX (mail, téléphone, CP)
  * Champs obligatoires lors de la création / modif
  * Enum pour “intéressé”
  * Tri automatique par raison sociale
  * Navigation fluide entre les écrans

🔒 Auteur

Projet réalisé par Nordine Sefroun dans le cadre de l’ECF Java.
