# IPM DEV — Revue technique du dépôt distant (14 novembre 2026)

**Type :** audit statique/documentaire en lecture seule du code, consigné dans une branche dédiée.  
**Propriétaire du code :** dépôt DEV IPM ; **conception et décisions :** dépôt GOV IPM.  
**Dépôt :** `emmanueltremblay9-stack/Immersive-Petro-Machinerie-Mod-Add-On`.  
**Objet revu :** `main@933552647a9f67fa83e20884828873d6bfaa1c45` (PR #1 Habitat P00/P03 intégrée).  
**PR concurrente :** [DEV #2](https://github.com/emmanueltremblay9-stack/Immersive-Petro-Machinerie-Mod-Add-On/pull/2), `docs/ipm-fleet-p00-gates-20261114@55c0f66e45e41fb4e6775cdcf654499196022d59`, **ouverte / Draft**, un seul fichier documentaire.  
**Résultat de cette revue :** `STATIC_REVIEW_ONLY / P00_PARTIAL / BUILD_NOT_RUN / GAMETEST_NOT_RUN / RUNTIME_NOT_RUN`.

## 1. Preuves directement consultées

- Arbre Git du `main` à la révision précisée : sources Java, ressources, Gradle, docs et deux skills locaux `.agents/skills` ; aucun `README.md` racine ni dossier `.github/workflows` dans cet arbre.
- `build.gradle` : ModDevGradle, toolchain Java 21, `--release 21`, configurations client, serveur et GameTest.
- `gradle.properties` : `mod_version=0.1.0`, Minecraft `1.21.1`, NeoForge `21.1.164`, versions historiques déclarées des dépendances.
- `src/main/resources/META-INF/neoforge.mods.toml` : `version = "0.1.0"`, Immersive Aircraft `type = "required"`, `issueTrackerURL = "https://github.com/"`, `license = "All Rights Reserved"`.
- `DockingPlatformValidator.java` : coordonnées `x=-5..5`, `y=-2..7`, `z=-12..0`, soit la structure réelle **11 × 13 × 10**.
- `IPMManualMultiblocks.java` : formation de coque désactivée par défaut ; contrôle des états protégés avant formation expérimentale.
- `IPMGarageSafetyGameTests.java` : cinq méthodes GameTest présentes, dont un test de persistance des fluides à résultat potentiellement non qualifiant.
- API GitHub Actions : **zéro exécution renvoyée** à la date du contrôle. Une absence d'exécutions n'est ni une tentative de build échouée ni une validation de build.
- PR #1 fusionnée, PR #2 encore Draft. Aucun code local plus récent ni artefact de build n'a été inspecté.

Les versions locales `0.1.19` et `0.1.23` apparaissent dans des rapports/historiques distincts. Ce contrôle ne certifie ni l'existence ni l'identité actuelle du candidat local. Ne pas remplacer ses fichiers par ceux du `main` distant pour résoudre cette incertitude.

## 2. Écarts techniques à traiter sur le candidat réellement sélectionné

| ID | Observation directe | Risque / qualification nécessaire |
| --- | --- | --- |
| IPM-REV-01 | `IPMGarageSafetyGameTests.fluid_tank_persists_basic_nbt_if_safe` exécute `helper.succeed()` si le diesel ou le lubrifiant attendu est introuvable. | Un résultat vert peut être émis sans tester la persistance de fluides. Séparer explicitement indisponibilité/skip de PASS ; valider comportement et rapport d'exécution, sans inventer une API de skip. |
| IPM-REV-02 | Aucun workflow GitHub Actions ni exécution trouvés sur le `main` examiné. | Aucun build/test CI public de ce snapshot n'est établi. Préparer un workflow Java 21/Gradle **après** sélection de la base et vérification de la résolution des dépendances ; rattacher les résultats à un SHA. |
| IPM-REV-03 | `mod_version=0.1.0` côté Gradle et `version="0.1.0"` en dur côté TOML (cohérents aujourd'hui). | Risque de dérive lors d'un futur changement de version. Évaluer un seul mécanisme de version/expansion des ressources, sans modifier la version active sur la foi de l'historique. |
| IPM-REV-04 | `immersive_aircraft` est `required` dans le TOML et une dépendance `implementation` dans Gradle, alors que le contrat IPM décrit son intégration comme conditionnelle. | Choisir explicitement le comportement souhaité, auditer les appels réels et vérifier la matrice avec et sans Aircraft ; ne pas rendre la dépendance optionnelle par supposition. |
| IPM-REV-05 | `issueTrackerURL` dans le TOML pointe seulement sur la racine GitHub et `license` porte `All Rights Reserved`. Aucun fichier LICENSE/CREDITS racine relevé dans cet arbre. | Corriger l'URL de support seulement avec la destination approuvée ; examiner licence, attribution et droits **spécifiques à chaque mod tiers** avant réemploi/publication. |
| IPM-REV-06 | Code distant centré sur Garage, docking, ports, écran et diagnostics ; la flotte est présente comme contrats P00/P03 et PR #2, non comme implémentation vérifiée dans ce `main`. | Ne pas présenter un module Cargo/Tanker/Habitat ou des profils miniers comme fonctionnalités exécutées avant sources, tests et runtime du candidat exact. |

Ces IDs sont **des repères d'audit**, pas une seconde file de tâches, des décisions approuvées ou de nouvelles autorisations de code. Ils ne changent pas les gates protégés.

## 3. Ordre minimal de reprise et critères

**P00 :** sur l'espace réellement retenu, relever `git rev-parse --show-toplevel`, `git rev-parse HEAD`, `git status --porcelain=v1`, `git branch -vv`, suivi/ancêtres, fichiers non suivis, `gradle.properties`, `build.gradle`, TOML, AGENTS/skills accessibles, artefact et droits. Comparer au `main` sans `reset` ni `clean` destructif. Vérifier aussi les chevauchements avec la PR #2.

**Qualification de référence :** Java 21 ; exécuter le build et les GameTests adéquats **sur la révision candidate**, relever commandes, codes de retour, nombres de tests, logs, JAR et empreinte. Un ancien PASS ne se transfère pas automatiquement.

**Corrections indépendantes, à décomposer :** démarrer par un lot borné GameTest/CI ou métadonnées selon impact ; appliquer revue et critères du lot, ne pas mélanger à l'implémentation P03. Les correctifs Java/config/ressources exigent les vérifications correspondantes.

**P03 Habitat :** seulement une fois P00 validé et son contrat confirmé, implémenter l'identité/ACL/clé/ancre côté serveur et tester les cas `P03-T01..T12` du contrat existant. Ne pas anticiper dimension, portail, véhicules, transferts ou rendu dans cette tranche.

**Publication :** aucun upload, tag, Release ou fusion ne découle de cet audit.

## 4. Frontières conservées

- Garage réel **11 × 13 × 10** avec fondation, rampe et cuves ; le renderer ne remplace pas la structure.
- Profils MK1/MK2/MK3 historiquement approuvés en conception (5 × 5 / 7 × 7 / 9 × 9) ; aucune nouvelle casse ni production d'items autorisée par ce document.
- Ne pas modifier la GUI, les fluides ou le renderer **natifs** du Tunnel Digger ; aucun Mixin, Survey Console finale, Core Sampling final ou copie d'assets/code tiers sans analyse de droits.
- GOV/Notion assurent les décisions de conception ; le workspace sélectionné prouve le code. Ce document n'actualise ni GOV, ni Notion, ni le poste local.

**Limite de validation du présent changement documentaire :** lecture, écriture et comparaison de fichiers Git peuvent démontrer uniquement la persistance du texte. Aucun Java, Gradle, JSON de jeu, modèle, texture, NBT ou gameplay n'est modifié et aucun test runtime n'est déclaré réussi.
