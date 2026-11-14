# IPM DEV — Reprise de la flotte, contrat GOV ↔ développement

**État :** `DOCUMENTARY_GATE / P00_PARTIAL / CODE_NOT_STARTED / BUILD_NOT_RUN`  
**Observation distante :** 2026-11-14 ; **produit :** Minecraft 1.21.1 / NeoForge / Java 21, mod ID `immersive_petro_machinery`  
**Portée de cette branche :** ce seul document. Aucune source Java, ressource de mod, dépendance, configuration build ou fichier AGENTS modifié.

## 1. Baseline observable et sources propriétaires

| Objet | Observation vérifiée / règle |
|---|---|
| DEV `main` | `933552647a9f67fa83e20884828873d6bfaa1c45` au contrôle distant de cette tranche |
| DEV PR #1 | Fusionnée le 2026-11-01 ; apporte P00 et P03 documentaires ; **aucune implémentation Habitat prouvée** |
| DEV `gradle.properties` distant | Mod `0.1.0`, NeoForge `21.1.164`, IE `1.21.1-12.4.2-194`, IP `4.4.1-37`, IM `0.2.0+1.21.1+neoforge`, IA `1.2.2+1.21.1+neoforge` ; ne pas propager ces versions au candidat local sans contrôle |
| DEV `AGENTS.md` | Snapshot de règles du dépôt distant encore ancien : mention de Phase 8 future. Réconcilier avec l'approbation **déjà constatée** des seuls profils 5×5/7×7/9×9 ; cela n'autorise pas de casse de blocs |
| GOV `main` | `0ef5e0e68afb2973b5c58b4990c34659d3f4e51c` au contrôle de cette tranche |
| GOV PR #1 | Fusionnée ; deux handoffs détaillés et Habitat consolidé v0.3 ; pas un ordre d'implémentation automatique |
| DEV local actuel | **UNKNOWN** : racine, branche, HEAD, dirty/untracked, tracking, JAR, version et éventuel travail plus récent non relus |
| PR ouvertes GOV/DEV | Aucune à l'ouverture de cette synchronisation, puis branches documentaires dédiées ; état à relire avant autre écriture |

**Sources de GOV à utiliser comme référence :**
- [Volet 1 BBModel](https://github.com/emmanueltremblay9-stack/Immersive_Petro-Machinerie_GPT/blob/0ef5e0e68afb2973b5c58b4990c34659d3f4e51c/proposals/IPM-FLEET-20261031/01_BBMODEL_HANDOFF.md) — Digger + trois carts, pièces et 29 modules de catalogue.
- [Volet 2 technique](https://github.com/emmanueltremblay9-stack/Immersive_Petro-Machinerie_GPT/blob/0ef5e0e68afb2973b5c58b4990c34659d3f4e51c/proposals/IPM-FLEET-20261031/02_GOV_IMPLEMENTATION_HANDOFF.md) — contrats P00..P15 et QA ; leur statut est un plan, pas des PR DEV existantes.
- [Habitat v0.3](https://github.com/emmanueltremblay9-stack/Immersive_Petro-Machinerie_GPT/blob/0ef5e0e68afb2973b5c58b4990c34659d3f4e51c/proposals/IPM-FLEET-20261031/03_HABITAT_CART_SPEC_CONSOLIDEE_v0.3.md) — supprime les anciennes limites contradictoires et prévaut sur les variantes historiques dans son périmètre.
- [Guide d'améliorations visuelles](https://github.com/emmanueltremblay9-stack/Immersive_Petro-Machinerie_GPT/blob/0ef5e0e68afb2973b5c58b4990c34659d3f4e51c/guides/IPM_BBMODEL_VISUAL_UPGRADE_GUIDE_v1.0.0.md) — IPM-DEC-019, guide adopté pour documentation uniquement.
- [Amendement de synchronisation GOV, proposé](https://github.com/emmanueltremblay9-stack/Immersive_Petro-Machinerie_GPT/blob/docs/ipm-fleet-gov-dev-routing-20261114/proposals/IPM-FLEET-20261031/05_GOV_DEV_SYNC_2026-11-14.md) — cette étape documentaire, non un nouveau canon.

**Sources DEV locales existantes :** [P00](../habitat/P00_DEV_BASELINE_AND_GATES_2026-10-31.md), [P03 clé/ancre](../habitat/P03_IDENTITY_KEY_CART_CONTRACT_PROPOSED.md). Préserver ces fichiers sans réécrire leurs observations historiques. Ne pas créer une seconde file de tâches.

## 2. Registre d'architecture à rapprocher avant code

| Produit | État attendu / owner | Première vérification DEV |
|---|---|---|
| Industrial Digger | Forage et jobs Digger-side, ses fluides, cargo et modules ; 5×5 MK1 est contrat de profil | Inspecter état Digger réellement présent, ne pas fabriquer nouvelle entité/registry en se fondant sur les seuls documents |
| Cargo Cart | Inventaire indépendant, filtres, hayon, chargement visible non autoritatif | Examiner options de traction, identité, capacité, safe detach/reload et lien de transfert |
| Tanker Cart | Cuve principale et réservoir auxiliaire distincts, types compatibles, quantités conservées | Examiner `IFluidHandler`/capacités publiques et refus de mélange sans transférer vers IM natif |
| Habitat Cart | Une dimension privée persistante par ancre canonique, clé personnelle + clic sur cart réel | Qualifier backend dimensionnel, ACL, world/anchor/credential identity, sessions et recovery ; pas de téléport distant |
| Garage IPM | Docking/service/configuration et modules Garage, vraie structure **11×13×10** | Mesurer baie libre, port actif, interlocks et version de service, sans refaire la fondation |
| Rendu/BBModel | Assets propres et signaux non autoritatifs | Mapper vrais pivots/meshes avant binder ; pas de modification du renderer natif Tunnel Digger |

Service Cart/Machine Cart sont des concepts historiques non sélectionnés. Les 29 fiches d'amélioration `DIG=11/CAR=4/TAN=4/HAB=5/GAR=5` sont un inventaire de design, **pas** des items ou capacités actuellement présents. Les budgets de slots Cargo/Tanker et les formes visuelles restent des propositions. Le Cargo Expansion **embarqué** du Digger et un **chariot remorqué** ne sont pas le même inventaire.

## 3. Habitat v0.3 — obligations et décisions non tranchées

**Directions enregistrées, mais non livrées dans DEV :**
- Entrée normale : interaction sur la porte du cart réellement présent, credential valide lié au joueur et au bon habitat, ACL et génération d'ancre ; l'item clé seul ne téléporte personne.
- Une dimension propre par cart. Monde intérieur persistant avec blocs, machines et stockage compatibles ; aucune copie d'inventaire dans l'entité.
- **« 25 blocs cubes au total »** : exigence formulée par le propriétaire, **interprétation volume total vs positions aménagées toujours ouverte**. Ne pas réutiliser `7×7×4` comme plan approuvé, ni inventer limite 25 par axe.
- Sommeil intérieur qui participe au vote de l'Overworld pour une ancre vérifiée dans cet Overworld ; hooks NeoForge ne prouvent pas à eux seuls un quorum interdimensionnel.
- Port mécanique propre compatible avec le Digger ; aucun accès depuis un Digger distant et aucun transfert automatique d'items, fluides, énergie ou signaux.
- Destruction effective du cart canonique : évacuation sûre, **dégâts** à résoudre une seule fois, habitat/biens conservés, `EJECT_ON_LOGIN` à étudier pour occupants hors ligne. **4 points** n'est qu'une fixture d'étude, pas une valeur adoptée.
- Clés Master/Crew et règles de révocation liées au registre ; quitter l'intérieur ne dépend pas d'une clé, d'une permission encore active ou d'une source d'énergie.

Restent **OPEN / PROPOSED** : métrique et gabarit définitifs des 25 blocs, capacité et coût des modules, limites dimensionnelles, backend Hyperbox/Infiniverse, compatibilité exact AstikorCarts, nombre de slots Habitat, politiques de hors-ligne, coût énergétique, comportement hors Overworld, règles de décès/respawn, politique de sécurité d'entrée pendant le mouvement et de retour. Le code ne doit pas remplacer `UNKNOWN` par une valeur implicite.

## 4. Séquence de travail strictement bornée

**P00 (prérequis) — audit de la vraie cible :** obtenir un accès autorisé au workspace qui contient les dernières sources. Lire `git rev-parse --show-toplevel`, `git rev-parse HEAD`, `git status --porcelain=v1`, `git branch -vv`, `git remote -v`, les refs locales et `origin/main`, l'ascendance et les conflits, puis `AGENTS.md`, les skills accessibles, build/Gradle, le TOML, l'arbre source et les tests. Ne pas reset/clean ni pousser à partir du remote ancien. Rendre un tableau DIRECT/REPORTED/HISTORICAL/UNKNOWN lié au hash du candidat.

**P01 — traction, séparément :** comparer adapter AstikorCarts vs cart IPM propre et tester réellement attach/detach, marche arrière, virage, pente, transfert de chunk/reload avant décision technique. Les dépendances et licences tierces sont à examiner, pas copiées.

**P02 — backend dimensionnel, séparément :** tester **deux** habitats indépendants, sauvegarde/restart, duplication d'ancre et protocole de retour sûr. Ne pas choisir une bibliothèque seulement sur sa page de distribution.

**P03 — première tranche de code candidate après P00 :** module serveur pur **identité + ACL + key + ancre** suivant le contrat déjà écrit `docs/habitat/P03_IDENTITY_KEY_CART_CONTRACT_PROPOSED.md`. Sa fin est roundtrip, rejet clé volée/révoquée, génération périmée, tentative double, requête ancienne ; aucun monde/dimension/porte/téléportation dans cette tranche. Une nouvelle PR de code requiert une base inspectée et une portée explicite ; ne pas réutiliser la PR documentaire.

**P04..P15 — lots fonctionnels :** découper selon le Volet 2 GOV, chaque PR ayant un résultat borné, dépendances, classes réellement inspectées, tests, checks serveur/client, migrateurs et limites. Pas d'ordre global de créer 12 PR sans le candidat.

## 5. Contrats de sécurité minimaux avant implémentation

1. **Persistances** : l'inventaire du cart, ses fluides et l'intérieur Habitat ont une source de vérité chacun ; ni copie de coffre ni second propriétaire possible après duplication d'ancre.
2. **Transferts** : serveur arbitre, opération bornée, composants d'ItemStack et FluidStack conservés ; simulation ≠ réservation ; interruption/dételage/chunk/unload ne détruisent ni ne dupliquent ressources.
3. **Garage** : cible précise, revision attendue, coûts uniques, retrait de capacité rempli refusé ou migration sûre ; aucun détachement dangereux pendant opération ; port/canal explicitement autorisé.
4. **Habitat** : aucun paquet C2S de téléportation libre ; interactions sur ancre réelle seulement ; sortie de secours sans clé ; destruction et offline/reload idempotents.
5. **Rendu** : visibilité installed/active/fault/unknown distincte ; roulement et animation dépendants des vrais états, pas des GUI ; renderer du Tunnel Digger natif exclu.
6. **Gates** : pas de nouvelle casse ou production d'items, mixins, final Core Sampling, Survey Console final, injection fluide native, GUI native, renderer natif, renderer Garage contournant fondation, copie d'assets/code tiers sans licence et attribution exactes, publication.

## 6. Tests attendus et preuves (pas exécutés ici)

- **P03 :** reprendre `P03-T01..T12` du contrat DEV existant. Valeurs des UUID et clés en fixtures de test uniquement ; aucun ID de registre inventé.
- **Garage/Cargo/Tanker :** deux demandes concurrentes même revision ; contenu plein et retrait d'amélioration ; transfert partiel et cible perdue ; sauvegarde après arrêt ; refus de fluide incompatible ; champ visualSnapshot obsolète.
- **Habitat :** clic sans clé / clé volée ; deux ancrages concurrents ; destruction + reconnexion occupant ; sommeil Overworld avec joueur éveillé + dormeurs ; redémarrage ; changement de cellule occupée ; sortie obstruée ; chaîne de dételage.
- **QA :** Java 21, build `./gradlew build` après changement de code/ressources, diagnostics/GameTests et serveur dédié selon critère, avec sortie, code de retour, HEAD/tree, dirty status, rapports et artefacts par hash. Un test statique documentaire n'est pas PASS runtime.

## 7. Périmètre d'écriture de cette PR DEV

La présente révision n'ajoute que ce document, dans `docs/fleet/`, sur une branche de revue dédiée. Aucun `AGENTS.md`, Java, Gradle, JSON de jeu, modèle, renderer, texture, monde ou JAR n'est touché. Ne pas lancer un build factice pour prouver le comportement : aucune source exécutable n'a changé. Pas de Notion, Vault, ChatGPT Project installation, tag, Release, upload ou fusion.

**Verdict limité :** `DEV_DOCS_READY / P00_STILL_PARTIAL / RUNTIME_NOT_RUN`. L'écriture/readback et la PR Draft sont des preuves de dépôt documentaire uniquement.
