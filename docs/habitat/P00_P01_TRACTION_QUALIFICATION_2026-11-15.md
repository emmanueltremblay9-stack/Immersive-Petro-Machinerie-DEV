# Qualification de l’attelage Industrial Digger et Habitat Cart

**15 novembre 2026 · P00 candidat local et P01 qualification statique**

L’étude porte sur l’Industrial Digger P14 et le Habitat Cart v4.1 pour Minecraft 1.21.1, NeoForge et Java 21. Les contrôles établissent une base de compilation et identifient les points d’attache à tester. La qualification en jeu reste à faire avant P04.

## P00 — Base DEV vérifiée

- Le candidat local `0.1.23` a été relevé sur `b0861152e7acfb62e397ece51e90a3288b5909a5` avec des modifications locales. Les 134 entrées de compilation ont été copiées et contrôlées par SHA-256. La configuration du wrapper dans la copie a pointé vers l’archive Gradle 8.8 locale vérifiée.
- La branche `main` distante à `933552647a9f67fa83e20884828873d6bfaa1c45` déclare `0.1.0`. Les trois commits distants depuis `fee532df16975a1ce36d75c5315eff659f597bba` ajoutent deux documents sans chemin commun avec les modifications du candidat. [Comparaison GitHub](https://github.com/emmanueltremblay9-stack/Immersive-Petro-Machinerie-DEV/compare/fee532df16975a1ce36d75c5315eff659f597bba...933552647a9f67fa83e20884828873d6bfaa1c45)
- L’[audit P00 initial](P00_DEV_BASELINE_AND_GATES_2026-10-31.md) décrivait l’état distant. Le [contrat d’identité P03](P03_IDENTITY_KEY_CART_CONTRACT_PROPOSED.md) conserve son statut de proposition.

| Contrôle effectué dans la copie du candidat `0.1.23` | Résultat |
| --- | --- |
| `.\gradlew.bat --offline --console=plain compileJava processResources --stacktrace` | Code 1. Le cache ne contenait pas le plugin ModDevGradle `2.0.30-beta`. |
| `.\gradlew.bat --console=plain -Dorg.gradle.internal.http.connectionTimeout=15000 -Dorg.gradle.internal.http.socketTimeout=20000 compileJava processResources --stacktrace` | Code 0. Java et les ressources ont été traités. |
| `.\gradlew.bat --offline --console=plain clean build --stacktrace` | Code 0. Six tâches exécutées. `test` a indiqué `NO-SOURCE`. |

Le JAR reconstruit de 182 083 octets identifie `immersive_petro_machinery` version `0.1.23`. Il contient les fichiers de licence et la classe du profil minier. Son SHA-256 est `48137b0f667d0da1f0a5ad9e8b3922dcd258c5cd8d8820798ccd867b946f43dd`. Les noms et les octets des 153 entrées de fichier concordent avec ceux du JAR présent dans le LAB au moment de la vérification. Le SHA-256 de l’archive du LAB est `1faf807f6c77332ae2b57341830802c4e2ae90dc59e3617785ce49e44f7a8f75`.

| Composant | Déclaré pour le build | Relevé dans le LAB |
| --- | --- | --- |
| Minecraft | 1.21.1 | 1.21.1 |
| NeoForge | 21.1.164 | 21.1.233 |
| Immersive Machinery | 0.2.0+1.21.1+neoforge | 0.2.0+1.21.1+neoforge |
| Immersive Engineering | 1.21.1-12.4.2-194 | 1.21.1-12.4.2-194 |
| Immersive Petroleum | 4.4.1-37 | 4.5.0-39 |
| Immersive Aircraft | 1.2.2+1.21.1+neoforge | 1.4.6+1.21.1+neoforge |
| AstikorCarts Redux | Absent des dépendances | Absent des 51 JAR actifs inspectés |

## P01 — Modèles et dépendance de traction

Le ZIP de référence présente 17 entrées au CRC valide et 16 empreintes conformes au manifeste. Le Cart d’origine, retrouvé séparément, concorde aussi avec l’empreinte déclarée. La boule du Digger, le coupleur avant et le pivot de lacet coïncident au point `(0, 6.65, 35.2)` dans le repère Blockbench de l’assemblage. La rotation de 180° autour de Y, la réduction à 31,07 % et la translation documentée reproduisent les quatre positions des pivots de roues. Le contrôle par texture a confirmé zéro UV hors limites. Les trois modèles portent au total 26 images PNG embarquées dont les dimensions et les CRC ont été contrôlés.

Le clip `preview_tow_yaw` présente des poses à 0° et ±60°. Le rapport de l’assemblage situe un premier contact autour de ±72,45° sur terrain plat. L’échelle Minecraft, le dégagement en mouvement et la limite de braquage en jeu restent à mesurer.

### AstikorCarts Redux

Le JAR officiel `astikorcartsredux-1.2.4.jar` déclare `astikorcartsredux`, la licence MIT, Minecraft `[1.21]` et NeoForge `[21.0.167,)`. Les distributions officielles vérifiées concordent, avec 576 717 octets et le SHA-256 `eb03ea0c246df3d465e7a7634e283c80489df91399075191f815f1b954a5e3fe`. [Modrinth](https://modrinth.com/mod/astikorcarts-redux/version/JtPCk233) · [CurseForge](https://www.curseforge.com/minecraft/mc-mods/astikor-carts-redux/files/8590778)

FML 4.0.38 (build) et 4.0.42 (LAB) appliquent une exception qui accepte la contrainte Minecraft `[1.21]` sous `1.21.1`. La qualification porte sur le contrôle de versions du chargeur. Le démarrage avec AstikorCarts reste à tester. [Sources FML 4.0.38](https://maven.neoforged.net/releases/net/neoforged/fancymodloader/loader/4.0.38/loader-4.0.38-sources.jar) · [Sources FML 4.0.42](https://maven.neoforged.net/releases/net/neoforged/fancymodloader/loader/4.0.42/loader-4.0.42-sources.jar)

L’inspection du JAR confirme que `setPulling(Entity)` peut accepter un véhicule non animal lorsque son identifiant est autorisé dans `pull_animals`. Le raccourci prend le véhicule du joueur monté comme tracteur possible. Le suivi calcule sa cible à partir des positions, de l’orientation et de la largeur de collision. AstikorCarts enregistre `PullingUUID` et tente une reprise dans le même niveau. Le comportement du Tunnel Digger et la persistance réciproque du couple restent à vérifier. [Attache et suivi](https://github.com/jusipat/astikor-carts-redux/blob/8de3b451ecb98d4ce7517ff1ff4556161987f1ba/src/main/java/com/jusipat/astikorcartsredux/entity/AbstractDrawnEntity.java#L131-L448) · [Sauvegarde](https://github.com/jusipat/astikor-carts-redux/blob/8de3b451ecb98d4ce7517ff1ff4556161987f1ba/src/main/java/com/jusipat/astikorcartsredux/entity/AbstractDrawnEntity.java#L696-L712)

## Passage à P04

AstikorCarts peut servir à un essai de faisabilité avec le Tunnel Digger `immersive_machinery:tunnel_digger`. Le futur attelage Habitat devra calculer les deux points d’attache en coordonnées monde et faire valider la liaison et son état persistant par le serveur.

L’étape suivante consiste à tester dans un LAB isolé avec le JAR AstikorCarts vérifié. L’essai doit couvrir l’attache et le détachement, la marche arrière et les obstacles, l’écart entre les deux points pendant les virages, puis le déchargement, la sauvegarde et la reprise avec deux clients. Il doit établir les limites de la traction native avant la mise en œuvre P04. Les GameTests, le rendu natif, le démarrage avec AstikorCarts et la validation en jeu n’ont pas été exécutés dans cette qualification.
