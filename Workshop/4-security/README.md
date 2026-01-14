# Workshop 4 - Sécurité DevSecOps assistée par IA

## Introduction

Ce tutoriel se concentre sur l'intégration de la sécurité dans le cycle DevOps (DevSecOps) pour une application web full-stack composée d'un backend Java/Spring Boot et d'un frontend Angular. L'objectif est d'apprendre à identifier les vulnérabilités, scanner les dépendances et images Docker, sécuriser les configurations Kubernetes, et gérer les secrets de manière appropriée, en utilisant GitHub Copilot pour assister dans ce processus.

Les fichiers d'exercice contiennent volontairement des configurations vulnérables. Vous utiliserez les différents modes de Copilot pour les identifier et les corriger.

(Note : les instructions sont présentées en français, mais l'utilisation de Copilot peut nécessiter des requêtes en anglais pour de meilleurs résultats.)

---

## Prérequis

- Workshop 1, 2 et 3 terminés
- Docker installé et fonctionnel
- Trivy installé (scanner de vulnérabilités)
- Accès au cluster Kubernetes (Minikube)

> 💡 **Astuce :** Utilisez le mode **Agent** de Copilot pour installer les outils nécessaires :
> ```text
> Installe Trivy sur mon système pour scanner les vulnérabilités Docker.
> ```

---

## 1. Comprendre les enjeux de sécurité DevSecOps

### 🔹 Étape 1 : Les piliers de la sécurité DevSecOps

| Pilier | Description | Outils |
|--------|-------------|--------|
| **SAST** | Analyse statique du code source | SonarQube, Semgrep, CodeQL |
| **SCA** | Analyse des dépendances tierces | OWASP Dependency Check, Snyk |
| **Container Security** | Scan des images Docker | Trivy, Clair, Anchore |
| **DAST** | Tests dynamiques en runtime | OWASP ZAP, Burp Suite |
| **Secret Management** | Gestion sécurisée des secrets | Vault, Sealed Secrets, SOPS |
| **Infrastructure Security** | Sécurisation K8s/Cloud | Falco, Kube-bench, OPA |

---

### 🔹 Étape 2 : Comprendre les concepts (Mode Ask)

Avant de commencer les exercices, familiarisez-vous avec les concepts clés.

👉 **Prompts Copilot suggérés :**

```text
Explique-moi la différence entre SAST, DAST et SCA dans le contexte DevSecOps.
```

```text
Quelles sont les vulnérabilités les plus courantes dans les applications Java/Spring Boot ?
```

```text
Comment fonctionne le scoring CVSS pour les vulnérabilités de sécurité ?
```

---

## 2. Scanner les dépendances applicatives

### 🔹 Étape 1 : Analyser les dépendances Backend (OWASP Dependency Check)

**Fichier d'exercice :** `Workshop/4-security/security_exercice/`

Le backend Spring Boot utilise Maven pour gérer ses dépendances. Certaines peuvent contenir des vulnérabilités connues (CVE).

👉 **Prompt Copilot suggéré (Mode Agent) :**

```text
Exécute OWASP Dependency Check sur le projet Maven backend et génère un rapport HTML des vulnérabilités.
```

---

### 🔹 Étape 2 : Analyser les dépendances Frontend (npm audit)

Le frontend Angular utilise npm. Les packages Node.js sont souvent source de vulnérabilités.

👉 **Prompt Copilot suggéré (Mode Agent) :**

```text
Exécute un audit de sécurité npm sur le frontend Angular et liste les vulnérabilités critiques et élevées.
```

---

### 🔹 Étape 3 : Corriger les vulnérabilités (Mode Ask/Edit)

Une fois les vulnérabilités identifiées, utilisez Copilot pour les corriger.

👉 **Prompts Copilot suggérés :**

```text
J'ai une vulnérabilité CVE-XXXX-XXXX dans la dépendance [nom]. Comment la corriger dans mon pom.xml ?
```

```text
npm audit a trouvé des vulnérabilités. Génère les commandes pour les corriger automatiquement.
```

| Niveau | Action recommandée |
|--------|-------------------|
| **Critical/High** | Correction immédiate obligatoire |
| **Medium** | Correction dans le sprint courant |
| **Low** | Évaluation et planification |

### Validation intermédiaire

- [ ] Rapport OWASP Dependency Check généré
- [ ] Audit npm exécuté
- [ ] Vulnérabilités critiques identifiées et documentées

---

## 3. Scanner les images Docker

### 🔹 Étape 1 : Scanner avec Trivy

Trivy est un scanner de vulnérabilités pour conteneurs, fichiers et configurations.

👉 **Prompt Copilot suggéré (Mode Agent) :**

```text
Scanne l'image Docker du backend avec Trivy et affiche les vulnérabilités de niveau HIGH et CRITICAL.
```

---

### 🔹 Étape 2 : Analyser un Dockerfile vulnérable

**Fichier d'exercice :** `Workshop/4-security/security_exercice/Dockerfile.vulnerable`

Ce Dockerfile contient plusieurs problèmes de sécurité courants.

👉 **Prompt Copilot suggéré (Mode Ask) :**

```text
Tu es un expert en sécurité des conteneurs. Analyse ce Dockerfile et identifie toutes les vulnérabilités et mauvaises pratiques de sécurité.
```

---

### 🔹 Étape 3 : Corriger le Dockerfile (Mode Edit)

Sélectionnez le Dockerfile vulnérable et corrigez-le.

👉 **Prompts Copilot suggérés (Mode Edit) :**

```text
Corrige ce Dockerfile pour qu'il s'exécute avec un utilisateur non-root.
```

```text
Applique les bonnes pratiques de sécurité Docker : épingle les versions, supprime les outils de debug, minimise les layers.
```

```text
Ajoute un HEALTHCHECK approprié pour une application Spring Boot.
```

---

### 🔹 Étape 4 : Scanner la configuration Dockerfile

Trivy peut également scanner les Dockerfiles pour détecter les misconfigurations, à vous de trouver comment faire !

### Validation intermédiaire

- [ ] Images Docker scannées avec Trivy
- [ ] Vulnérabilités HIGH/CRITICAL identifiées
- [ ] Dockerfile corrigé avec utilisateur non-root
- [ ] Bonnes pratiques de sécurité appliquées

---

## 4. Sécuriser les manifests Kubernetes

### 🔹 Étape 1 : Analyser les manifests vulnérables

**Fichier d'exercice :** `Workshop/4-security/security_exercice/insecure-deployment.yaml`

Ce manifest contient des configurations de sécurité problématiques.

👉 **Prompt Copilot suggéré (Mode Ask) :**

```text
Tu es un expert en sécurité Kubernetes. Analyse ce Deployment et identifie toutes les vulnérabilités et configurations non sécurisées selon les bonnes pratiques CIS Kubernetes Benchmark.
```

**Points de sécurité à vérifier :**

| Élément | Configuration sécurisée |
|---------|------------------------|
| `runAsNonRoot` | `true` |
| `readOnlyRootFilesystem` | `true` |
| `allowPrivilegeEscalation` | `false` |
| `capabilities` | `drop: ["ALL"]` |
| `seccompProfile` | `RuntimeDefault` |
| `resources` | Limites définies |
| `serviceAccountName` | Compte dédié (pas default) |

---

### 🔹 Étape 2 : Corriger le SecurityContext (Mode Edit)

👉 **Prompts Copilot suggérés (Mode Edit) :**

```text
Ajoute un securityContext sécurisé à ce Deployment Kubernetes suivant les recommandations CIS.
```

```text
Configure ce Pod pour s'exécuter en read-only avec les volumes temporaires nécessaires.
```

**Exemple de securityContext sécurisé :**

```yaml
securityContext:
  runAsNonRoot: true
  runAsUser: 1000
  runAsGroup: 1000
  fsGroup: 1000
  seccompProfile:
    type: RuntimeDefault
containers:
  - name: app
    securityContext:
      allowPrivilegeEscalation: false
      readOnlyRootFilesystem: true
      capabilities:
        drop:
          - ALL
```

---

### 🔹 Étape 3 : Créer une NetworkPolicy

Les NetworkPolicies contrôlent le trafic réseau entre les Pods.

👉 **Prompt Copilot suggéré (Mode Agent) :**

```text
Crée une NetworkPolicy Kubernetes qui :
- Permet au frontend de communiquer uniquement avec le backend sur le port 8080
- Bloque tout trafic entrant vers le backend sauf depuis le frontend
- Permet le trafic sortant du backend uniquement vers la base de données
```

---

### 🔹 Étape 4 : Scanner avec Kubesec

Kubesec analyse les manifests Kubernetes pour les problèmes de sécurité.

👉 **Prompt Copilot suggéré (Mode Agent) :**

```text
Utilise kubesec pour scanner le fichier de déploiement Kubernetes et affiche le score de sécurité.
```

### Validation intermédiaire

- [ ] Manifests Kubernetes analysés
- [ ] SecurityContext sécurisé appliqué
- [ ] NetworkPolicy créée
- [ ] Score Kubesec amélioré (objectif : > 7)

---

## 5. Gestion des Secrets

### 🔹 Étape 1 : Identifier les secrets exposés

**Fichier d'exercice :** `Workshop/4-security/security_exercice/secrets-exposed.yaml`

Ce fichier contient des secrets mal gérés (en clair, en base64 simple, etc.).

👉 **Prompt Copilot suggéré (Mode Ask) :**

```text
Analyse ce fichier Kubernetes et identifie tous les problèmes de gestion des secrets. Explique pourquoi le base64 n'est pas une méthode de chiffrement.
```

**Mauvaises pratiques courantes :**

| Problème | Risque |
|----------|--------|
| Secrets en clair dans les YAML | Exposition dans Git |
| Base64 seul | N'est PAS du chiffrement |
| Secrets dans les variables d'env des CI | Logs accessibles |
| Pas de rotation | Compromission prolongée |

---

### 🔹 Étape 2 : Utiliser les Secrets Kubernetes correctement

👉 **Prompt Copilot suggéré (Mode Edit/Agent) :**

```text
Convertis ces secrets en clair en un Secret Kubernetes correctement structuré, et modifie le Deployment pour les utiliser via secretKeyRef.
```

---

### 🔹 Étape 3 : Scanner les secrets dans le code

Utilisez des outils pour détecter les secrets accidentellement commités.

👉 **Prompt Copilot suggéré (Mode Agent) :**

```text
Utilise gitleaks ou trufflehog pour scanner le repository à la recherche de secrets exposés (mots de passe, clés API, tokens).
```

### Validation intermédiaire

- [ ] Secrets Kubernetes correctement structurés
- [ ] Deployments mis à jour pour utiliser secretKeyRef
- [ ] Scan de secrets exécuté sur le repository

---

## 6. Intégrer la sécurité dans la CI/CD

### 🔹 Étape 1 : Enrichir la pipeline GitLab CI

Ajoutez des étapes de sécurité à la pipeline créée dans le Workshop 3.

👉 **Prompt Copilot suggéré (Mode Edit) :**

```text
Ajoute à cette pipeline GitLab CI les jobs de sécurité suivants :
- Trivy scan pour les images Docker
- Gitleaks pour détecter les secrets
Avec des rapports artifacts et allow_failure pour les scans non bloquants.
```

**Structure des jobs de sécurité recommandée :**

```yaml
security:
  stage: security
  parallel:
    matrix:
      - SCAN_TYPE: [dependency-check, npm-audit, trivy, gitleaks]

dependency-check:
  stage: security
  image: owasp/dependency-check-action
  script:
    - /usr/share/dependency-check/bin/dependency-check.sh --scan ./backend --format HTML --out ./reports
  artifacts:
    paths:
      - reports/
    expire_in: 1 week
  allow_failure: true
  rules:
    - if: $CI_COMMIT_BRANCH == "main"
```

---

### 🔹 Étape 2 : Configurer les seuils de blocage

Définissez des seuils pour bloquer la pipeline en cas de vulnérabilités critiques.

👉 **Prompt Copilot suggéré (Mode Ask) :**

```text
Comment configurer Trivy dans GitLab CI pour bloquer la pipeline si des vulnérabilités CRITICAL sont détectées, mais permettre le passage avec des vulnérabilités HIGH ?
```

---

### 🔹 Étape 3 : Dashboard de sécurité (Optionnel)

Pour une vue consolidée des vulnérabilités :

👉 **Prompt Copilot suggéré (Mode Ask) :**

```text
Comment configurer GitLab pour afficher un dashboard de sécurité consolidant les résultats de tous les scans (SAST, Dependency Scanning, Container Scanning) ?
```

---

## 7. Exercice récapitulatif : Audit de sécurité complet

### 🔹 Mission

Réalisez un audit de sécurité complet de l'application shopping en utilisant Copilot.

**Checklist d'audit :**

| Catégorie | Vérification | Outil |
|-----------|--------------|-------|
| **Dépendances** | CVE dans les libs Java/npm | OWASP DC, npm audit |
| **Conteneurs** | Vulnérabilités dans les images | Trivy |
| **Dockerfile** | Bonnes pratiques | Trivy config, Hadolint |
| **Kubernetes** | SecurityContext, RBAC | Kubesec, kube-bench |
| **Secrets** | Exposition de credentials | Gitleaks, Trufflehog |
| **Réseau** | Isolation des services | NetworkPolicies |

---

### 🔹 Prompt Copilot suggéré (Mode Agent)

```text
Tu es un auditeur en sécurité DevSecOps. Réalise un audit de sécurité complet de cette application :

1. Scanne les dépendances Maven et npm pour les CVE
2. Scanne les images Docker avec Trivy
3. Analyse les Dockerfiles pour les misconfigurations
4. Vérifie les manifests Kubernetes pour les problèmes de sécurité
5. Recherche les secrets exposés dans le code

Génère un rapport consolidé avec :
- Les vulnérabilités critiques à corriger immédiatement
- Les recommandations prioritaires
- Un score de sécurité global
```

---

## Validation finale

- [ ] Dépendances scannées (OWASP, npm audit)
- [ ] Images Docker scannées (Trivy)
- [ ] Dockerfiles sécurisés (non-root, healthcheck)
- [ ] Manifests Kubernetes sécurisés (securityContext)
- [ ] NetworkPolicies en place
- [ ] Secrets correctement gérés
- [ ] Pipeline CI/CD avec étapes de sécurité
- [ ] Aucune vulnérabilité CRITICAL non traitée

---

## Ressources utiles

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [CIS Kubernetes Benchmark](https://www.cisecurity.org/benchmark/kubernetes)
- [Docker Security Best Practices](https://docs.docker.com/develop/security-best-practices/)
- [Trivy Documentation](https://aquasecurity.github.io/trivy/)
- [Kubernetes Security Checklist](https://kubernetes.io/docs/concepts/security/security-checklist/)

> 💡 **Astuce :** Vous pouvez fournir ces URLs dans vos prompts Copilot pour obtenir des réponses alignées avec la documentation officielle.

---

**Félicitations !** Vous avez terminé le Workshop 4 sur la sécurité DevSecOps assistée par IA. Vous maîtrisez maintenant l'identification et la correction des vulnérabilités dans une chaîne DevOps complète.

[Retour au Workshop 3](../3-ci-pipeline/README.md) | [Workshop 5 - Debug Logs](../5-debug-logs/README.md)
