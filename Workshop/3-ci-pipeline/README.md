# Workshop 3 - Pipeline CI/CD assistée par IA

## Introduction

Ce tutoriel se concentre sur la création d'une pipeline CI/CD pour une application web full-stack composée d'un backend Java/Spring Boot et d'un frontend Angular. L'objectif est d'apprendre à automatiser le build, les tests, l'analyse de sécurité, la création des images Docker et le déploiement sur Kubernetes, en utilisant GitHub Copilot pour assister dans ce processus.

Les fichiers d'exercice contiennent volontairement des configurations incomplètes. Vous utiliserez les différents modes de Copilot pour les compléter et les améliorer.

(Note : les instructions sont présentées en français, mais l'utilisation de Copilot peut nécessiter des requêtes en anglais pour de meilleurs résultats.)

---

## Prérequis

- Workshop 1 terminé (Dockerfiles optimisés)
- Workshop 2 terminé (manifests Kubernetes corrigés)
- Compte GitLab avec le repo partagé cloné et une branche dédiée

> 💡 **Astuce :** Utilisez le mode **Ask** de Copilot pour vérifier votre configuration GitLab :
> ```text
> Comment vérifier que mon compte GitLab est bien configuré pour utiliser les pipelines CI/CD ?
> ```

---

## 1. Explorer la pipeline existante

### 🔹 Étape 1 : Identifier le fichier à compléter

**Fichier d'exercice :** `Workshop/3-ci-pipeline/ci-pipeline_exercice/`

Observez le fichier `.gitlab-ci.exercise.yml` présent dans le dossier d'exercice. Ce fichier contient un squelette de pipeline incomplet.

| Élément | Statut | Description |
|---------|--------|-------------|
| Cache Maven | ❌ Absent | Accélère les builds |
| Cache npm | ❌ Absent | Accélère les builds frontend |
| Stage test | ❌ Absent | Tests unitaires et intégration |
| Stage security | ❌ Absent | Scan de dépendances |
| Stage docker | ❌ Absent | Build et push des images |
| Stage deploy | ❌ Absent | Déploiement K8s |
| Artifacts | ❌ Absent | Partage entre jobs |
| Rules | ❌ Absent | Conditions d'exécution |

---

### 🔹 Étape 2 : Comprendre les concepts CI/CD (Mode Ask)

Avant de compléter la pipeline, assurez-vous de comprendre les concepts clés.

👉 **Prompts Copilot suggérés :**

```text
Explique-moi la différence entre un stage, un job et un artifact dans GitLab CI.
```

```text
Quels sont les stages recommandés pour une pipeline CI/CD complète ?
```

```text
Comment fonctionne le cache dans GitLab CI et pourquoi est-il important ?
```

---

## 2. Spécifications de la pipeline CI/CD attendue

Avant de construire votre prompt, vous devez comprendre ce qui est attendu dans la version finale de la pipeline. Voici les spécifications complètes :

---

### Configuration globale

| Élément | Spécification |
|---------|---------------|
| **Cache Maven** | Chemin `.m2/repository`, clé basée sur la branche |
| **Cache npm** | Chemin `frontend/node_modules`, clé basée sur la branche |
| **Variables** | Registry Docker, chemins des Dockerfiles, namespace K8s |

---

### Stage `test`

| Job | Spécification |
|-----|---------------|
| **test-backend** | Exécute `mvn test`, génère un rapport JUnit, artifact des rapports |
| **test-frontend** | Exécute `npm test` avec couverture, artifact des rapports |

---

### Stage `security`

| Job | Spécification |
|-----|---------------|
| **dependency-check** | Utilise OWASP Dependency Check pour Maven |
| **Conditions** | Exécution uniquement sur `main`, `allow_failure: true` |

---

### Stage `docker`

| Job | Spécification |
|-----|---------------|
| **build-backend** | Build et push de l'image backend |
| **build-frontend** | Build et push de l'image frontend |
| **Configuration** | Docker-in-Docker, tags `$CI_COMMIT_SHA` et `latest` |
| **Conditions** | Exécution sur `main` uniquement, après les tests |

---

### Stage `deploy`

| Job | Spécification |
|-----|---------------|
| **deploy-staging** | Déploiement manuel sur environnement staging |
| **deploy-production** | Nécessite `deploy-staging`, déploiement production |
| **Configuration** | kubectl via variables CI/CD, mise à jour des images avec SHA |
| **Vérification** | `kubectl rollout status` pour valider le déploiement |

---

## 3. Bonnes pratiques de Prompt Engineering

Ici nous allons utiliser le mode "Plan" de Copilot pour générer une pipeline CI/CD complète.
Nous avons donc besoin de rédiger un prompt efficace pour guider Copilot dans cette tâche complexe.

Avant de rédiger votre prompt, familiarisez-vous avec les techniques de prompt engineering efficaces.

### 🔹 Structure d'un prompt efficace

Un bon prompt pour générer du code suit cette structure :

```
[RÔLE] + [CONTEXTE] + [TÂCHE] + [CONTRAINTES] + [FORMAT DE SORTIE]
```

| Élément | Description | Exemple |
|---------|-------------|---------|
| **Rôle** | Définir l'expertise attendue | "Tu es un ingénieur DevOps senior spécialisé en GitLab CI" |
| **Contexte** | Décrire l'environnement et le projet | "Pour une application Java 17/Spring Boot 3.4 et Angular 14..." |
| **Tâche** | Expliquer clairement ce qui est demandé | "Génère une pipeline CI/CD complète..." |
| **Contraintes** | Lister les règles et bonnes pratiques | "Respecte les bonnes pratiques : cache, artifacts, rules..." |
| **Format** | Préciser le format de sortie attendu | "Génère un fichier .gitlab-ci.yml valide et commenté" |

---

### 🔹 Techniques avancées

| Technique | Description | Exemple |
|-----------|-------------|---------|
| **Être spécifique** | Détailler les versions, outils, chemins | "Maven 3.9, Node.js 21, images basées sur Alpine" |
| **Donner des exemples** | Montrer le format attendu | "Les tags Docker doivent être : `registry/app:sha` et `registry/app:latest`" |
| **Décomposer** | Lister les éléments un par un | "Stage 1: test, Stage 2: security, Stage 3: docker..." |
| **Contraintes négatives** | Préciser ce qu'il ne faut PAS faire | "Ne pas utiliser de secrets en dur, ne pas exécuter en root" |
| **Référencer la doc** | Mentionner les sources officielles | "En suivant les recommandations de docs.gitlab.com" |

---

### 🔹 Importance du contexte

> 💡 **Astuce cruciale :** Le contexte est la clé d'un prompt réussi !

Utilisez le bouton **"Add Context"** (ou `#`) dans Copilot pour attacher les fichiers pertinents avant de soumettre votre prompt. 

---

### 🔹 Exemple de prompt complet (Mode Plan)

Voici un exemple de prompt bien structuré :

```text
Tu es un ingénieur SRE / Automation senior spécialisé en Ansible.

CONTEXTE :
- Objectif : industrialiser le provisionning + la configuration d’une infra “classique” sur des VM Linux
- Cibles : Debian 12 (staging + production)
- Topologie :
  - 2 serveurs web (Nginx) exposés en 80/443
  - 2 serveurs applicatifs (Python FastAPI) exposés en 8000, derrière Nginx en reverse-proxy
  - 1 serveur PostgreSQL 16 exposé uniquement sur le réseau privé
- Exigences :
  - TLS géré côté Nginx (certificats fournis via variables, pas générés)
  - Hardening de base (SSH, firewall, mises à jour, users)
  - Observabilité : node_exporter + journald persistent + rotation de logs

TÂCHE :
Génère un plan détaillé puis un projet Ansible complet (fichiers + contenu) avec :

1. Configuration globale :
   - ansible.cfg (bonnes pratiques : forks, pipelining, timeout, fact caching si pertinent)
   - requirements.yml (collections/roles externes si nécessaires)
   - conventions de variables (group_vars/host_vars), et structure claire des inventaires

2. Provisionning & configuration (playbooks) :
   - playbook site.yml “end-to-end” (common + hardening + apps)
   - playbook web.yml (Nginx + TLS + reverse-proxy)
   - playbook app.yml (déploiement FastAPI via systemd, venv, user dédié)
   - playbook db.yml (PostgreSQL 16 : users/db, conf réseau privé, sauvegarde basique)
   - handlers propres (reload/restart), templates Jinja2, idempotence stricte

3. Sécurité :
   - role hardening :
     - SSH : désactivation root login, auth par clé, options raisonnables
     - firewall (nftables ou ufw) : règles minimales par groupe (web/app/db)
     - fail2ban : profil sshd
   - Aucun secret en dur :
     - utilisation d’Ansible Vault (fichiers vault séparés par environnement)
     - exemples de variables attendues (placeholders), mais jamais de valeurs réelles

4. Qualité & tests :
   - ansible-lint + yamllint (config + commandes)
   - Molecule pour tester au moins un rôle critique avec scénario simple
   - tags Ansible (ex : setup, hardening, deploy, rollback), et support du --check (dry-run)

5. Déploiement & opérations :
   - stratégie de déploiement applicatif “safe” :
     - restart contrôlé, healthcheck local avant de valider
     - rollback simple (version N-1) si échec
   - commandes d’exécution documentées (staging puis production)

CONTRAINTES :
- Respecter les bonnes pratiques Ansible (idempotence, structure roles, handlers, templates, variables)
- Ne pas mettre de secrets en dur (Vault + variables)
- Ajouter des commentaires explicatifs dans les fichiers clés
- Tout doit être cohérent, exécutable et “propre” (pas de pseudo-code flou)

FORMAT :
Génère d’abord un plan d’action, puis :
- l’arborescence complète (tree)
- Le contenu des fichiers nécessaires
```

---

## 4. Exercice : Construire votre prompt et générer la pipeline (Mode Plan)

### 🔹 Étape 1 : Préparer le contexte

Avant d'écrire votre prompt, rassemblez le contexte nécessaire :

1. Ouvrez le chat Copilot (`Ctrl+Alt+I`)
2. Sélectionnez le mode **Plan** dans le menu déroulant en bas à gauche
3. Cliquez sur **"Add Context"** ou utilisez `#`
4. Ajoutez les fichiers que vous estimez pertinents.

---

### 🔹 Étape 2 : Rédiger votre prompt

En vous inspirant des bonnes pratiques ci-dessus et des spécifications de la section 2, rédigez votre propre prompt pour générer la pipeline complète.

👉 **Checklist pour votre prompt :**

- [ ] Avez-vous défini un rôle d'expert ?
- [ ] Avez-vous décrit le contexte du projet (technologies, versions) ?
- [ ] Avez-vous listé tous les stages et jobs attendus ?
- [ ] Avez-vous précisé les contraintes (bonnes pratiques, sécurité) ?
- [ ] Avez-vous demandé un format de sortie spécifique ?
- [ ] Avez-vous attaché les fichiers de contexte pertinents ?

---

### 🔹 Étape 3 : Utiliser le Mode Plan

1. Dans le chat Copilot, tapez votre prompt complet
2. Copilot va d'abord générer un **plan d'action** détaillé
3. Validez le plan ou demandez des ajustements
4. Copilot génère ensuite le fichier `.gitlab-ci.yml` complet

> 💡 **Astuce :** Le Mode Plan permet de voir la stratégie avant l'exécution. C'est idéal pour des tâches complexes comme la création d'une pipeline CI/CD complète.

---

### 🔹 Étape 4 : Valider et itérer

Une fois la pipeline générée :

1. Vérifiez que tous les éléments des spécifications sont présents
2. Si des éléments manquent, affinez votre prompt et régénérez

👉 **Prompts d'itération suggérés :**

```text
Il manque la configuration du cache npm. Ajoute-la en suivant le même pattern que le cache Maven.
```

```text
Le stage deploy n'a pas de vérification rollout status. Peux-tu l'ajouter ?
```

---

### Validation intermédiaire

- [ ] Prompt rédigé avec rôle, contexte, tâche, contraintes et format
- [ ] Plan généré et validé
- [ ] Pipeline complète générée
- [ ] Tous les stages présents : test, security, docker, deploy
- [ ] Cache configuré pour Maven et npm
- [ ] Bonnes pratiques respectées

---

## 5. Valider la syntaxe de la pipeline

### 🔹 Étape 1 : Vérification locale

Utilisez le mode **Agent** de Copilot pour valider la syntaxe de votre pipeline avant de la pousser sur GitLab.

👉 **Prompt Copilot suggéré :**

```text
Utilise l'API de validation GitLab CI dans mon wsl pour vérifier la syntaxe de ce fichier .gitlab-ci.yml. Indique-moi les erreurs éventuelles et comment les corriger.
```

---

### 🔹 Étape 2 : Pousser et tester sur GitLab

Continuez en mode **Agent** pour pousser votre fichier sur GitLab et déclencher la pipeline.
👉 **Prompt Copilot suggéré :**

```text
Pousse ce fichier .gitlab-ci.yml sur ma branche [nom]-[prenom]-ci-cd dans le repository GitLab distant et déclenche la pipeline.
```

---

## 6. Optimiser la pipeline (Optionnel)

Maintenant que la pipeline fonctionne, optimisons-la pour suivre les bonnes pratiques.

### 🔹 Étape 1 : Analyser la pipeline (Mode Ask)

👉 **Prompts Copilot suggérés :**

```text
Tu es un ingénieur DevOps senior spécialisé en GitLab CI. Analyse cette pipeline et liste les améliorations possibles en termes de performance, sécurité et bonnes pratiques.
```

```text
Quelles sont les bonnes pratiques pour optimiser le temps d'exécution d'une pipeline CI/CD ?
```

---

### 🔹 Étape 2 : Appliquer les optimisations (Mode Edit)

Sélectionnez votre fichier `.gitlab-ci.yml` et utilisez le mode Edit pour appliquer les améliorations.

👉 **Prompts Copilot suggérés (Mode Edit) :**

| Optimisation | Prompt suggéré |
|--------------|----------------|
| **Matrice de tests** | `Ajoute une matrice de tests pour Java 17 et Java 21 avec une limite de parallélisation à 2 jobs.` |
| **Cache Docker** | `Optimise le job de build Docker pour utiliser le cache des layers avec buildx.` |

---

### 🔹 Étape 3 : Optimisations avancées (Optionnel)

Pour aller plus loin, explorez ces améliorations :

| Optimisation | Prompt suggéré |
|--------------|----------------|
| **Rollback automatique** | `Ajoute un mécanisme de rollback automatique qui surveille le déploiement pendant 5 minutes et rollback si le taux d'erreur dépasse 5%.` |
| **Helm** | `Modifie la pipeline pour utiliser Helm au lieu de kubectl apply.` |
| **Review Apps** | `Ajoute la création d'environnements de review automatiques sur chaque merge request.` |

---

### 🔹 Étape 4 : Re-valider la pipeline

```bash
git add .
git commit -m "feat: optimize CI/CD pipeline"
git push origin nom-prenom-ci-cd
```

---

## 7. Déboguer une pipeline en échec

### 🔹 Exemple de problème courant

Analysez ce log de pipeline et identifiez le problème :

```
$ docker build -t $CI_REGISTRY_IMAGE/backend:$CI_COMMIT_SHA .
error: failed to solve: failed to read dockerfile: 
open /var/lib/docker/tmp/buildkit-mount123/Dockerfile: no such file or directory
```

👉 **Prompt Copilot suggéré :**

```text
Cette erreur GitLab CI indique un problème avec le Dockerfile. Explique la cause probable et comment corriger le job.
```

---

### 🔹 Autres erreurs courantes

| Erreur | Prompt suggéré |
|--------|----------------|
| **Permission denied** | `J'ai une erreur "permission denied" dans mon job Docker. Comment la résoudre ?` |
| **Cache not found** | `Mon cache GitLab CI n'est pas trouvé entre les jobs. Pourquoi ?` |
| **Timeout** | `Mon job dépasse le timeout. Comment optimiser ou augmenter le temps ?` |

---

## Validation finale

- [ ] Pipeline syntaxiquement valide
- [ ] Tous les stages s'exécutent correctement
- [ ] Cache configuré et fonctionnel
- [ ] Tests avec rapports JUnit générés
- [ ] Scan de sécurité présent
- [ ] Build Docker avec tags appropriés
- [ ] Déploiement staging et production séparés
- [ ] Optimisations appliquées

---

## Ressources utiles

- [GitLab CI/CD Documentation](https://docs.gitlab.com/ee/ci/)
- [GitLab CI/CD Best Practices](https://docs.gitlab.com/ee/ci/pipelines/pipeline_efficiency.html)
- [Docker Build Best Practices](https://docs.docker.com/build/ci/)

> 💡 **Astuce :** Vous pouvez fournir ces URLs dans vos prompts Copilot pour obtenir des réponses alignées avec la documentation officielle.

---

**Félicitations !** Vous avez terminé le Workshop 3 sur les pipelines CI/CD assistées par IA. Vous maîtrisez maintenant la création et l'optimisation de pipelines GitLab CI avec Copilot.

[Retour au Workshop 2](../2-kubernetes/README.md) | [Workshop 4 - Security](../4-security/README.md)
