# PerfView_Android

Mise en place :

Ce projet a était développé à l'aide de l'environnement de travail Android Studio 2.2.3.
Pour ce faire si vous n'avez pas cette IDE il va falloir l'installer : https://developer.android.com/studio/install.html

Une fois Android Studio installé, il vous reste à configurer son SDK Manager, 
qui est le "centre de contrôle" des outils d’Android (librairies, Software Development Kits - SDK, etc.).
C’est à partir du SDK Manager que vous allez pouvoir télécharger et mettre à jour ces outils.

Pour cela, lancez Android Studio, cliquez sur "Tools" puis sur "Android" et finalement "SDK Manager".

- SDK Plateforme : Android 7.0 (Nougat)

Par défaut, tout est déjà installé pour que vous puissiez développer avec la dernière version des outils et du SDK Manager.
Si ce n'est pas le cas pour une raison ou une autre installez les packages "Recommended packages"
profité également de mettre à jours votre IDE et outils SDK : https://developer.android.com/studio/intro/update.html

Mais avant de passer à l'importation du projet,
je vous invite à ajouter ou créer un émulateur depuis AVD Manager mis a disposition par Android Studio.

Pour cela, lancez Android Studio, cliquez sur "Tools" puis sur "Android" et finalement "AVD Manager".
Les différentes étapes sont mentionnées sous ce lien : https://developer.android.com/studio/run/managing-avds.html

Cependant, je vous conseille de lancer l'application sur un smartphone, car les émulateurs sont très très longs.
Généralement, il suffit d'activer sur votre l'appareil les options développeur (la manière d'activé est différentes pour chaque appareil),
puis activez le débogage USB.
pour plus de détails, voici un lien avec les différentes étapes : https://developer.android.com/studio/run/device.html

Pour déployer le projet :
- Télécharger le zip du projet depuis github
- Dézipper le projet (évitez de le placer dans un emplacement où le chemin d'accès est long)
- Ouvrir Android Studio et importer le projet dézippé. 
     Cliquez sur "File" puis sur "New" et finalement "Import project".
     
 Si lorsque vous importez le projet vous avez une erreur "Failed to crunch file ...... "
 déplacez simplement le projet sur un chemin plus court
