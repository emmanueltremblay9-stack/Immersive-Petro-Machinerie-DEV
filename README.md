# Immersive Petro-Machinery (IPM)

**Add-on non officiel pour Minecraft Java 1.21.1 / NeoForge**, consacré à l'intégration d'Immersive Machinery, Immersive Engineering et Immersive Petroleum. Le mod ID est `immersive_petro_machinery`.

> **Actualisation :** le candidat local 0.1.23 a depuis été identifié et compilé dans la qualification P00/P01 du 15 novembre, référencée en fin de page. Le paragraphe suivant décrit la revue historique du 14 novembre.

> **Portée de ce dépôt :** les déclarations et fonctionnalités ci-dessous concernent le code **GitHub distant**. Un candidat local plus récent a été rapporté, mais sa révision, ses fichiers modifiés, son build et son JAR n'ont pas été vérifiés ici. La présence d'une conception ou d'un test ne démontre pas son exécution ou sa publication.

## État du code accessible

Sur le `main` relu le **14 novembre 2026**, `gradle.properties` déclare `mod_version=0.1.0`. Cette valeur ne doit pas être appliquée à un éventuel candidat local plus récent.

Le code présent comporte notamment :

- **Industrial Driller Garage :** validation de la vraie structure **11 × 13 × 10**, fondation et rampe incluses, baies dégagées et réservoirs arrière ;
- **Docking Controller :** détection/alignment du Tunnel Digger, soft-lock, inventaire d'améliorations et état persistant ;
- **Service fluides :** ports de remplissage carburant et lubrifiant, avec filtres et réservoirs propres au Garage ;
- **Maintenance et diagnostics :** menu/écran de maintenance, manuel et GameTests de sécurité présents dans les sources.

Le *formed shell* expérimental est désactivé par défaut et soumis à des protections d'état. La sortie finale de la Survey Console et l'activation finale du Core Sampling ne sont pas ouvertes par cette base.

La **flotte Industrial Digger / Cargo Cart / Tanker Cart / Habitat Cart** fait l'objet de contrats de conception et de reprise, et non d'une implémentation attestée dans le `main` inspecté. Les profils MK1/MK2/MK3 (5 × 5 / 7 × 7 / 9 × 9) ont une approbation de conception dans leur portée exacte ; cela n'autorise pas de nouvelles mécaniques de casse ou des modifications natives du Tunnel Digger.

## Environnement déclaré dans le `main` distant

| Composant | Déclaration distante (à revalider pour le candidat actif) |
| --- | --- |
| Minecraft | `1.21.1` |
| NeoForge | `21.1.164` |
| Java | `21` |
| Immersive Engineering | `1.21.1-12.4.2-194` |
| Immersive Petroleum | `4.4.1-37` |
| Immersive Machinery | `0.2.0+1.21.1+neoforge` |
| Immersive Aircraft | `1.2.2+1.21.1+neoforge` |

**Attention :** le TOML distant classe actuellement Immersive Aircraft en dépendance `required` ; son rôle conditionnel dans l'architecture du projet doit être réconcilié sur la base candidate, sans changement implicite.

## Compiler et tester

Prévoir un **JDK 21** installé. Dans un workspace candidat vérifié :

```sh
./gradlew build
./gradlew runGameTestServer --no-daemon --console=plain
```

Sous Windows, utiliser `gradlew.bat`. Consulter [docs/build.md](docs/build.md), [docs/dependency-resolution.md](docs/dependency-resolution.md) et [docs/dev-diagnostics.md](docs/dev-diagnostics.md) pour l'environnement et les limites des diagnostics. Ces commandes sont des instructions, **pas des résultats de test obtenus par ce README**.

## Documentation et contrats

- [Règles de contribution et restrictions](AGENTS.md) — certaines mentions historiques de Phase 8 exigent une réconciliation avec les décisions approuvées.
- [Structure réelle du Garage](docs/docking-platform-layout.md).
- [Préflight Habitat P00](docs/habitat/P00_DEV_BASELINE_AND_GATES_2026-10-31.md) — audit distant partiel, candidat local encore à vérifier.
- [Contrat candidat P03 identité/clé/ancre](docs/habitat/P03_IDENTITY_KEY_CART_CONTRACT_PROPOSED.md) — **proposé, code non commencé, tests non exécutés**.
- [Revue technique du dépôt — 14 novembre 2026](docs/technical-review-2026-11-14.md) — observations et critères de reprise, sans nouvelle file de tâches.
- [Dépôt de gouvernance IPM](https://github.com/emmanueltremblay9-stack/Immersive_Petro-Machinerie_GPT) — conception, décisions et limites d'autorité.

## Sécurité et droits

Aucune modification implicite de la GUI, du renderer ou du transfert fluide du Tunnel Digger natif ; aucun Mixin, nouvelle casse de blocs hors contrat, génération d'items par casse, activation finale Survey/Core Sampling, remplacement de la fondation Garage ou publication de mod sans leur autorisation précise.

Les dépendances à d'autres mods ne constituent pas une autorisation de copier leurs codes ou assets. Vérifier licence, provenance, attribution et redistribution avant tout réemploi. La métadonnée TOML du `main` indique actuellement `All Rights Reserved` ; aucun statut GPL ou permission générale de réutilisation n'est déduit de ce README.

**Prochaine étape technique admissible :** réconcilier le véritable workspace local (branche, HEAD/tree, état modifié, versions, artefacts et tests), préserver tout travail non poussé, puis qualifier la tranche de code concernée. Ni une PR documentaire ni ce README ne certifient un build, un GameTest, une installation ou une version publiable.

## Actualisation vérifiée — 15 novembre 2026

Les observations du 14 novembre ci-dessus restent un état historique du dépôt distant, pas la situation actuelle du candidat local. La [qualification P00/P01 fusionnée dans DEV #5](https://github.com/emmanueltremblay9-stack/Immersive-Petro-Machinerie-DEV/blob/7b6c19cb2a6a80fc5c92e9b5f2f35f8390826a57/docs/habitat/P00_P01_TRACTION_QUALIFICATION_2026-11-15.md) identifie désormais le candidat **0.1.23**, HEAD `b0861152e7acfb62e397ece51e90a3288b5909a5` avec modifications locales préservées. Son build de référence et ses 153 entrées JAR ont été vérifiés dans cette qualification ; `test NO-SOURCE` ne prouve aucun test automatisé.

SHA-256 du JAR reconstruit : `48137b0f667d0da1f0a5ad9e8b3922dcd258c5cd8d8820798ccd867b946f43dd`. Cette preuve ne transfère pas le source local dans le `main` distant 0.1.0 et ne valide ni GameTests, ni traction, ni HUD en jeu. Les écarts de l'audit distant doivent être revérifiés sur le candidat avant correction. Ne pas écraser le travail non commité ni assimiler le contrat de profils MK à une entité Digger jouable.

Cette actualisation est documentaire : aucun nouveau build, GameTest, runtime, changement de mécanique ou levée de restriction native. L'essai LAB prévu par P00/P01 reste distinct ; les contrats proposés ne deviennent pas approuvés par cette note.
