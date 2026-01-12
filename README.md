# Workshop DevOps Augmenté – Docker, Kubernetes & CI/CD avec GitHub Copilot

## Objectif général

Ce workshop a pour but de mettre en pratique GitHub Copilot dans le cadre des pratiques DevOps modernes, en illustrant concrètement comment l'outil peut aider à :

- **Optimiser des Dockerfiles** en appliquant les bonnes pratiques de containerisation
- **Corriger et améliorer des manifests Kubernetes** (Deployments, Services, Ingress)
- **Créer et compléter des pipelines CI/CD** (GitLab CI / GitHub Actions)
- **Analyser des logs et créer des runbooks** de résolution d'incidents

---

## Prérequis

### Outils obligatoires

| Outil | Version | Vérification |
|-------|---------|--------------|
| VS Code ou IntelliJ | Dernière version | - |
| GitHub Copilot | Extension activée | Licence requise |

### Connaissances préalables

- Bases de Docker (build, run, images)
- Notions de Kubernetes (pods, deployments, services)
- Compréhension des pipelines CI/CD

---

## Parcours proposé (2h)

| Activité | Durée |
|----------|:-----:|
| **Présentation et tour de table** | 15 min |
| **Mini-atelier : Setup de l'environnement avec Copilot Agent** | 10 min |
| **Workshop 0 : Setup de l'environnement** | 15 min |
| **Workshop 1 : Optimisation des Dockerfiles** | 45 min |
| Présentation + exercice pratique | |
| Débrief : bonnes pratiques Docker | 15 min |
| **Workshop 2 : Manifests Kubernetes** | 45 min |
| Présentation + exercice pratique | |
| Débrief : sécurité et ressources K8s | 15 min |
| **Workshop 3 : Pipeline CI/CD** | 45 min |
| Présentation + exercice pratique | |
| Débrief : optimisations et déploiement | 15 min |
| **Workshop 4 : Analyse de logs (optionnel)** | 30 min |

---

## Objectifs pédagogiques

### Maîtriser les 4 modes de GitHub Copilot

- **Chat** : Analyse de configurations, explications, questions/réponses
- **Edit** : Modification de fichiers YAML, Dockerfiles, scripts
- **Agent** : Automatisation de tâches complexes multi-fichiers
- **Plan** : Génération de plans d'action pour des problématiques DevOps

---

## Outils et technologies

| Catégorie | Technologies |
|-----------|--------------|
| **Application** | Java 17, Spring Boot 3.4.2, Angular 14 |
| **Containerisation** | Docker, Docker Compose |
| **Orchestration** | Kubernetes, Helm |
| **CI/CD** | GitLab CI, GitHub Actions |
| **Tests** | JUnit 5, Karma |
| **Assistance IA** | GitHub Copilot (Chat, Edit, Agent, Plan) |

---

## Démarrage rapide

### 1. Cloner le repository
```bash
git clone <repository-url>
cd FIC-Augmented-DevOps
```

### 2. Mini-atelier : Setup avec GitHub Copilot Agent

> **Objectif** : Découvrir le mode **Agent** de Copilot en lui faisant installer les outils nécessaires.

1. Ouvrir **GitHub Copilot Chat** (`Ctrl+Shift+I`)
2. Sélectionner le mode **Agent** dans le menu déroulant en bas à gauche
3. Copier-coller ce prompt :

```
Vérifie et installe si nécessaire les outils suivants sur mon système 
(utilise WSL) :

Outils obligatoires :
- Java JDK 17
- Maven 3.x
- Node.js 21.x (avec npm)
- Docker 24.x

Outils Kubernetes :
- kubectl (dernière version stable)
- minikube (dernière version stable)
- Helm 3.x

Pour chaque outil :
1. Vérifie d'abord s'il est déjà installé et sa version
2. Installe-le uniquement si absent ou version incorrecte
3. Confirme l'installation avec la commande de vérification

À la fin, affiche un récapitulatif de tous les outils avec leurs versions.
```

3. Laisser Copilot exécuter les commandes (accepter les confirmations)

---

### 3. Commencer le Workshop

**Bon workshop !**

[Workshop 1-dockerfile](./Workshop/1-dockerfile/README.md)

---

