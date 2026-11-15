# Industrial Digger MK1 V12 P8 — référence dimensionnelle Blockbench

**Type :** documentation technique de modèle, **pas** configuration de gameplay  
**Version géométrique :** `MK1 V12 P8`, attelage inclus  
**Référence GOV :** [IPM-MK1-V12-P8-DIM-20261114](https://github.com/emmanueltremblay9-stack/Immersive_Petro-Machinerie_GPT/blob/821db542b7c14d857f9e76d31432c46e55a151cc/guides/IPM_MK1_V12_P8_DIMENSIONS_2026-11-14.md)  
**Origine des valeurs :** déclaration du propriétaire du projet, 2026-11-14 (America/Toronto)  
**Précision déclarée :** 0,001 unité Blockbench ; **relecture du .bbmodel V12 P8 : NOT_RUN**  
**Candidat local plus récent :** `UNKNOWN` ; ce fichier ne prétend pas le remplacer ni décrire ses ressources actuelles.

## Enveloppe du modèle

| Axe | Dimension | Au repos | Encombrement maximal animé |
|---|---|---:|---:|
| Z | Longueur | **83,780** | **85,530** |
| X | Largeur | **36,700** | **37,460** |
| Y | Hauteur | **28,214** | **29,600** |

La virgule sépare les décimales : `83,780` signifie **83.780 unités Blockbench**, et non 83 780 unités.

- La longueur inclut toute la distance **pointe du foret → arrière de la boule d'attelage**.
- L'enveloppe maximale animée comprend l'**avance du foret**, la **rotation des alésoirs** et l'**abaissement de la lame**.
- Les maxima sont fournis **par axe** ; aucun calcul d'échelle Minecraft, d'AABB runtime ou de simultanéité de toutes les poses extrêmes n'a été réalisé.
- C'est le **MK1 V12 P8**, non le rig historique `Industrial_Digger_MK1_ANIMATION_RIG_v0.8.bbmodel`. Les dimensions des autres variantes ne sont pas déduites.

## Limites de mise en œuvre

Le profil de forage approuvé reste **MK1 5×5 / MK2 7×7 / MK3 9×9**, distinct de l'encombrement physique du véhicule. Le Garage demeure une vraie structure **11×13×10** (fondation, rampe, réservoirs). Aucune preuve d'ajustement ou de dégagement des animations dans la structure ne découle de cette fiche.

Avant de dériver une boîte de collision, un export de modèle, une animation ou une contrainte de docking, contrôler le fichier source V12 P8 (identité/hash, axes, origine, transformations, poses extrêmes, échelle d'export), puis les volumes effectifs de la version intégrée. Si le .bbmodel ou le candidat local est inaccessible, déclarer cette validation `NOT_RUN` / `UNKNOWN`, et ne pas reconstruire des assets à partir de l'ancien `main`.

**Hors portée de cette mise à jour :** Java, Gradle, JSON runtime, .bbmodel, animations, renderer natif Tunnel Digger, fluides, GUI, mixins, nouveau minage, production de loot, Core Sampling final, Survey Console final, refonte Garage, versions, release, publication et fusion de PR.

**Validation de cette tranche :** uniquement écriture/relecture documentaire GitHub. Aucun build, GameTest, test Blockbench ou runtime n'est revendiqué.
