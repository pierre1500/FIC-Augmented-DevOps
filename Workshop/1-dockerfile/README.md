# Workshop 1 : Dockerfile et optimisation assistée par IA

## Introduction
Ce tutoriel porte sur un projet d'application web full-stack, composé d'un backend Java/Spring Boot et d'un frontend Angular. L'objectif est double :

(1) apprendre à explorer et auditer rapidement la base de code (structure, dépendances, documentation) et
(2) utiliser l'outil d'IA GitHub Copilot (dans VS Code) pour assister les processus DevOps. 

(Note : les instructions sont présentées en français, mais l'utilisation de Copilot peut nécessiter des requêtes en anglais pour de meilleurs résultats.)

## 1. Explorer et auditer l'application

### 🔹 Étape 1 : Identifier les deux modules
Dans votre éditeur, observez les dossiers `backend/` et `frontend/`.

---

## 2. Démarrer l'application en local

Avant de conteneuriser l'application, il est essentiel de la faire fonctionner en local pour bien comprendre son fonctionnement.

> 💡 **Astuce :** Utilisez le mode **Ask** de Copilot afin de trouver les commandes et informations nécessaires au lancement du projet.

---

### 🔹 Étape 1 : Lancer le Backend

---

### 🔹 Étape 2 : Lancer le Frontend

---

### 🔹 Étape 3 : Vérifier le bon fonctionnement

Une fois les deux services lancés :

| Service | URL par défaut |
|---------|----------------|
| Backend | `http://localhost:8080` |
| Frontend | `http://localhost:4200` |

👉 **Prompt Copilot suggéré :**

```text
Comment tester que le backend et le frontend communiquent correctement ?
```

---

## 3. Construction des images Docker

**Fichiers à compléter :** `Workshop/1-dockerfile/docker_exercice/`

---

### 🔹 Étape 1 : Collecter les informations (Mode Ask)

Avant de créer vos Dockerfiles, récupérez les informations essentielles sur le projet.

👉 **Prompts Copilot suggérés :**

```text
Quelles versions de Java et Node.js sont utilisées dans ce projet ?
Sur quels ports écoutent le backend et le frontend ?
```

```text
Quels sont les fichiers de build pour le backend et le frontend ?
```

---

### 🔹 Étape 2 : Créer les Dockerfiles

Utilisez Copilot pour générer les fichiers Docker nécessaires.

| Fichier | Prompt suggéré |
|---------|----------------|
| **Dockerfile Backend** | `Crée un Dockerfile pour le backend Spring Boot.` |
| **Dockerfile Frontend** | `Crée un Dockerfile pour Angular.` |
| **Docker Compose** | `Crée un docker-compose.yml orchestrant backend et frontend.` |

---

### 🔹 Étape 3 : Choisir votre parcours

Deux options s'offrent à vous :

| Mode | Description |
|------|-------------|
| **Mode Plan** | Génère un plan d'action détaillé avant de créer les fichiers. |
| **Mode Agent** | Crée directement les fichiers demandés. |

---

### 🔹 Étape 4 : Tester et déboguer (Mode Agent)

👉 **Build et lancement :**

```text
Exécute docker-compose up --build et affiche les erreurs éventuelles.
```

👉 **En cas d'erreur :**

```text
J'ai cette erreur Docker : [collez l'erreur]. Comment la corriger ?
```

---

### Validation

- [ ] `docker-compose up` démarre sans erreur
- [ ] Backend accessible sur son port
- [ ] Frontend accessible et communique avec le backend

---

## 4. Optimisation et retravail des Dockerfiles

Il est possible que Copilot vous ait généré des Dockerfiles fonctionnels. Cependant, dans la majorité des cas, ils ne seront pas optimaux (sauf si vous avez fourni un contexte approprié dans votre prompt initial).

L'objectif de cette section est d'améliorer ces Dockerfiles afin de :

- **Réduire la taille des images**
- **Appliquer les bonnes pratiques**
- **Améliorer les temps de build**
- **Renforcer la sécurité**

### 🔹 Étape 1 : Analyser les Dockerfiles existants (Mode Chat)
Utilisez le mode **Ask** de Copilot pour analyser les Dockerfiles générés.

👉 **Prompts Copilot suggérés :**

```text
Tu es un auditeur Software Engineer senior spécialisé en Docker. Analyse ce Dockerfile et liste ses points faibles ainsi que des suggestions d'amélioration.
```

```text
Quels sont les conseils pour optimiser ce Dockerfile en termes de taille, sécurité et performance ?
```

**Documentation utile :**
- [Best practices for writing Dockerfiles](https://docs.docker.com/build/building/best-practices/)
- [Dockerfile reference](https://docs.docker.com/engine/reference/builder/)
> 💡 **Astuce :** Vous pouvez donner l'URL de la documentation dans votre prompt à Copilot.

### 🔹 Étape 2 : Appliquer les optimisations (Mode Edit / Agent)
Utilisez les suggestions de Copilot pour modifier et améliorer vos Dockerfiles.

👉 **Prompts Copilot suggérés :**
```text
Tu es un Software Engineer senior spécialisé en Docker.
Applique les optimisations suivantes à ce Dockerfile : [liste des optimisations].
```

### 🔹 Étape 3 : Re-tester les images optimisées
Refaites un `docker-compose up --build` avec les Dockerfiles optimisés et vérifiez que tout fonctionne correctement.

### Validation finale
- [ ] Taille des images réduite
- [ ] Bonnes pratiques Docker appliquées
- [ ] Application fonctionne correctement après optimisation

---

**Félicitations !** Vous avez terminé le Workshop 1 sur les Dockerfiles et leur optimisation assistée par IA. Vous êtes maintenant prêt à passer au prochain atelier.

[Workshop 2-kubernetes](../2-kubernetes/README.md)