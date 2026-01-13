# Workshop 2 - Cluster Kubernetes et Manifests assistés par IA

## Introduction
Ce tutoriel se concentre sur la création et la gestion de manifests Kubernetes pour déployer une application web full-stack composée d'un backend Java/Spring Boot et d'un frontend Angular. L'objectif est d'apprendre à définir des ressources Kubernetes (Pods, Deployments, Services) et à utiliser GitHub Copilot pour assister dans ce processus.

Les fichiers d'exercice contiennent volontairement des erreurs et des configurations sous-optimales. Vous utiliserez principalement le **mode Edit** de Copilot pour les corriger et les améliorer.

(Note : les instructions sont présentées en français, mais l'utilisation de Copilot peut nécessiter des requêtes en anglais pour de meilleurs résultats.)

---

## Prérequis

- Workshop 1 terminé (images Docker construites)
- Cluster Kubernetes local (Minikube)
- `kubectl` installé et configuré

> 💡 **Astuce :** Utilisez le mode **Ask** de Copilot pour vérifier votre configuration :
> ```text
> Comment vérifier que mon cluster Kubernetes local (Minikube) est bien configuré et accessible ?
> ```

---

## 1. Explorer les manifests existants

### 🔹 Étape 1 : Identifier les fichiers à corriger

**Fichiers d'exercice :** `Workshop/2-kubernetes/k8s_exercice/`

Observez les fichiers YAML présents dans le dossier d'exercice. Chaque fichier contient des erreurs ou des configurations incomplètes.

| Fichier | Description |
|---------|-------------|
| `backend-deployment.yaml` | Déploiement du backend Spring Boot |
| `backend-service.yaml` | Service exposant le backend |
| `frontend-deployment.yaml` | Déploiement du frontend Angular |
| `frontend-service.yaml` | Service exposant le frontend |
| `configmap.yaml` | Configuration partagée (optionnel) |

---

### 🔹 Étape 2 : Comprendre les ressources Kubernetes (Mode Ask)

Avant de corriger les manifests, assurez-vous de comprendre les concepts clés.

👉 **Prompts Copilot suggérés :**

```text
Explique-moi la différence entre un Pod, un Deployment et un Service dans Kubernetes.
```

```text
Quels sont les champs obligatoires dans un Deployment Kubernetes ?
```

```text
Comment un Service Kubernetes permet-il la communication entre le frontend et le backend ?
```

---

## 2. Corriger les manifests avec Copilot (Mode Edit)

### 🔹 Étape 1 : Analyser les erreurs

Ouvrez chaque fichier YAML et utilisez le **mode Edit** de Copilot pour identifier et corriger les erreurs.

👉 **Comment utiliser le mode Edit :**
1. Sélectionnez le code à corriger (ou tout le fichier)
2. Appuyez sur `Ctrl+I`
3. Entrez votre prompt de correction

> 💡 **Astuce importante :** Pensez à fournir le contexte nécessaire à Copilot ! Utilisez le bouton **"Add Context"** (ou `#`) pour attacher les fichiers ou dossiers pertinents à votre prompt. Par exemple :
> - Ajoutez le dossier `k8s_exercice/` pour que Copilot comprenne l'ensemble des manifests
>
> Plus le contexte est riche, plus les corrections seront précises et cohérentes !

---

### 🔹 Étape 2 : Corriger le Backend

Ouvrez `backend-deployment.yaml` et `backend-service.yaml`.

👉 **Prompts Copilot suggérés (Mode Edit) :**

```text
Corrige ce manifest Kubernetes. Vérifie les labels, selectors, ports et la syntaxe YAML.
```

```text
Ce Deployment Kubernetes contient des erreurs. Identifie-les et corrige-les.
```

```text
Assure-toi que le Service pointe correctement vers le Deployment avec les bons selectors.
```

---

### 🔹 Étape 3 : Corriger le Frontend

Ouvrez `frontend-deployment.yaml` et `frontend-service.yaml`.

👉 **Prompts Copilot suggérés (Mode Edit) :**

```text
Corrige ce manifest de Deployment Angular pour Kubernetes.
```

```text
Vérifie que le Service expose correctement le frontend et corrige les erreurs.
```

---

### 🔹 Étape 4 : Vérifier la cohérence globale (Mode Ask)

Une fois les fichiers corrigés individuellement, vérifiez leur cohérence.

👉 **Prompts Copilot suggérés :**

```text
Vérifie que les labels et selectors sont cohérents entre les Deployments et Services de ce projet.
```

```text
Le frontend doit communiquer avec le backend. Comment configurer l'URL du backend dans le frontend ?
```

---

## 3. Déployer et tester sur le cluster

### 🔹 Étape 1 : Appliquer les manifests

```bash
kubectl apply -f Workshop/2-kubernetes/k8s_exercice/
```

👉 **En cas d'erreur :**

```text
J'ai cette erreur kubectl : [collez l'erreur]. Comment la corriger ?
```

---

### 🔹 Étape 2 : Vérifier le déploiement

```bash
kubectl get pods
kubectl get services
kubectl get deployments
```

👉 **Prompts Copilot suggérés :**

```text
Comment déboguer un Pod Kubernetes qui reste en état "CrashLoopBackOff" ?
```

```text
Comment consulter les logs d'un Pod Kubernetes ?
```

---

### 🔹 Étape 3 : Accéder à l'application

| Méthode | Commande / Description |
|---------|------------------------|
| **Port-forward** | `kubectl port-forward svc/frontend-service 4200:80` |
| **NodePort** | Accéder via `http://localhost:<nodePort>` |
| **Minikube** | `minikube service frontend-service` |

👉 **Prompt Copilot suggéré :**

```text
Comment exposer un Service Kubernetes pour y accéder depuis mon navigateur local ?
```

### Validation intermédiaire

- [ ] `kubectl apply` s'exécute sans erreur
- [ ] Tous les Pods sont en état `Running`
- [ ] Les Services sont correctement créés
- [ ] L'application est accessible via le navigateur

---

## 4. Optimiser les manifests Kubernetes

Maintenant que l'application fonctionne, optimisons les manifests pour suivre les bonnes pratiques.

### 🔹 Étape 1 : Analyser les manifests (Mode Ask)

👉 **Prompts Copilot suggérés :**

```text
Tu es un ingénieur DevOps senior spécialisé en Kubernetes. Analyse ce Deployment et liste les améliorations possibles en termes de sécurité, performance et bonnes pratiques.
```

```text
Quelles sont les bonnes pratiques pour configurer les resources requests et limits dans Kubernetes ?
```

---

### 🔹 Étape 2 : Appliquer les optimisations (Mode Edit)

Sélectionnez vos manifests et utilisez le mode Edit pour appliquer les améliorations.

👉 **Prompts Copilot suggérés (Mode Edit) :**

```text
Ajoute des resources requests et limits appropriés pour une application Spring Boot.
```

```text
Ajoute des probes de liveness et readiness pour ce Deployment.
```

```text
Configure ce Deployment pour suivre les bonnes pratiques de sécurité Kubernetes (securityContext, non-root user).
```

```text
Ajoute des labels standards Kubernetes (app.kubernetes.io/*) à ce manifest.
```

---

### 🔹 Étape 3 : Optimisations avancées (Optionnel)

Pour aller plus loin, explorez ces améliorations :

| Optimisation | Prompt suggéré |
|--------------|----------------|
| **HPA (Autoscaling)** | `Crée un HorizontalPodAutoscaler pour ce Deployment.` |
| **NetworkPolicy** | `Crée une NetworkPolicy pour isoler le backend du trafic externe.` |
| **Ingress** | `Crée un Ingress pour exposer le frontend avec un nom de domaine.` |
| **ConfigMap/Secrets** | `Externalise les variables d'environnement dans un ConfigMap.` |

---

### 🔹 Étape 4 : Re-déployer et valider

```bash
kubectl apply -f Workshop/2-kubernetes/k8s_exercice/
kubectl rollout status deployment/backend-deployment
kubectl rollout status deployment/frontend-deployment
```

---

## Validation finale

- [ ] Tous les manifests sont valides et sans erreur
- [ ] Les Pods démarrent correctement avec les probes configurées
- [ ] Les resources requests/limits sont définis
- [ ] Les bonnes pratiques de sécurité sont appliquées
- [ ] L'application fonctionne correctement après optimisation

---

## Ressources utiles

- [Kubernetes Documentation](https://kubernetes.io/docs/home/)
- [Kubectl Cheat Sheet](https://kubernetes.io/docs/reference/kubectl/cheatsheet/)
- [Kubernetes Best Practices](https://kubernetes.io/docs/concepts/configuration/overview/)

> 💡 **Astuce :** Vous pouvez fournir ces URLs dans vos prompts Copilot pour obtenir des réponses alignées avec la documentation officielle.

---

**Félicitations !** Vous avez terminé le Workshop 2 sur Kubernetes et les manifests assistés par IA. Vous maîtrisez maintenant la correction et l'optimisation de ressources Kubernetes avec Copilot.

[Retour au Workshop 1](../1-dockerfile/README.md) | [Workshop 3 - CI/CD](../3-cicd/README.md)

