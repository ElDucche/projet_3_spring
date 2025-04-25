# Projet API Location Immobilière (Spring Boot)

Ce projet est une API RESTful développée avec Spring Boot pour gérer une application de location immobilière. Elle permet aux utilisateurs de s'inscrire, de se connecter, de lister des biens à louer, de les mettre à jour, de les supprimer et d'envoyer des messages concernant ces biens.

## Prérequis

*   Java 21 ou supérieur ([JDK 21](https://www.oracle.com/java/technologies/downloads/#java21))
*   Maven 3.x ou supérieur
*   MySQL 8.x (ou une base de données compatible)
*   Docker et Docker Compose (si vous utilisez la configuration fournie pour la base de données)

## Installation et Configuration

1.  **Cloner le dépôt :**
    ```bash
    git clone <url-du-repo>
    cd projet_3_spring
    ```

2.  **Configuration de la base de données :**
    *   Assurez-vous qu'une instance MySQL est en cours d'exécution.
    *   Modifiez le fichier `src/main/resources/application.properties` pour configurer l'URL de la base de données, le nom d'utilisateur et le mot de passe :
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/mydb
        spring.datasource.username=user
        spring.datasource.password=password
        ```
    *   La base de données (`mydb` dans cet exemple) doit exister. Hibernate (`spring.jpa.hibernate.ddl-auto=update`) créera ou mettra à jour les tables au démarrage.

3.  **Configuration JWT :**
    *   Une clé secrète JWT est définie dans `application.properties` (`app.jwt.secret`). Pour la production, utilisez une clé plus sécurisée et gérez-la via des variables d'environnement ou un système de gestion des secrets.

4.  **Configuration du stockage des images :**
    *   Par défaut, les images uploadées sont stockées dans un dossier `uploads` à la racine du projet. Ce chemin est défini dans `application.properties` (`app.upload.dir`) et utilisé par `ImageHelper.java`.
    *   L'URL de base pour accéder aux images est configurée via `app.base.url`.

## Lancement de l'application

Vous pouvez lancer l'application en utilisant le wrapper Maven fourni :

```bash
./mvnw spring-boot:run
```


L'application démarrera par défaut sur le port 9000 (configurable via `server.port` dans `application.properties`).

## API Endpoints

L'API expose les endpoints suivants (préfixés par `/api`) :

### Authentification (`/auth`)
- **POST** `/register` : Inscription d'un nouvel utilisateur.
- **POST** `/login` : Connexion d'un utilisateur et récupération d'un token JWT.
- **GET** `/me` : Récupération des informations de l'utilisateur connecté (nécessite un token JWT valide).

### Locations (`/rentals`)
- **GET** `/` : Récupérer la liste de toutes les locations.
- **GET** `/{id}` : Récupérer les détails d'une location spécifique.
- **POST** `/` : Créer une nouvelle location (nécessite authentification et données multipart/form-data).
- **PUT** `/{id}` : Mettre à jour une location existante (nécessite authentification et données multipart/form-data).
- **DELETE** `/{id}` : Supprimer une location (nécessite authentification).

### Messages (`/messages`)
- **POST** `/` : Envoyer un message lié à une location (nécessite authentification).

### Images (`/uploads`)
- **GET** `/{nom-fichier}` : Accéder aux images uploadées.

## Documentation API (Swagger/OpenAPI)

Une fois l'application lancée, la documentation interactive de l'API est disponible via Swagger UI à l'adresse suivante :

http://localhost:9000/swagger-ui.html

## Technologies utilisées

- **Framework** : Spring Boot 3.x
- **Sécurité** : Spring Security (avec authentification JWT)
- **Accès aux données** : Spring Data JPA, Hibernate
- **Base de données** : MySQL
- **Validation** : Jakarta Bean Validation
- **Build** : Maven
- **Utilitaires** : Lombok
- **API Documentation** : SpringDoc OpenAPI (Swagger UI)
- **Gestion des images** : Stockage local

## Structure du projet
```
projet_3_spring/
├── .mvn/                     # Configuration du wrapper Maven
├── src/
│   ├── main/
│   │   ├── java/             # Code source Java
│   │   │   └── elducche/projet_3_spring/
│   │   │       ├── config/       # Configurations (Sécurité, Web, JPA)
│   │   │       ├── controller/   # Contrôleurs REST
│   │   │       ├── dto/          # Data Transfer Objects
│   │   │       ├── exception/    # Exceptions personnalisées
│   │   │       ├── model/        # Entités JPA
│   │   │       ├── repository/   # Interfaces Spring Data JPA
│   │   │       ├── security/     # Classes liées à la sécurité (JWT, UserDetails)
│   │   │       ├── services/     # Logique métier
│   │   │       └── utils/        # Classes utilitaires (ex: ImageHelper)
│   │   └── resources/        # Fichiers de ressources
│   │       ├── application.properties # Fichier de configuration principal
│   │       └── META-INF/
│   └── test/                 # Code source des tests
├── target/                   # Fichiers générés par Maven (compilation, packaging)
├── uploads/                  # Dossier pour les images uploadées (créé au premier upload)
├── mvnw                      # Script wrapper Maven (Linux/macOS)
├── mvnw.cmd                  # Script wrapper Maven (Windows)
└── pom.xml                   # Fichier de configuration Maven
```
