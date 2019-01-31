# ChatApplication 2.0

C'est une application qui sert a faire le clavardage entre des users en temps reel, ca pourra fonctionner par groupe ou juste un Chat 1 on 1. C'est la version avec base de donnees locales, mais on a pas implemente un vrai serveur de presence.
Fonctionne sur locale ou plusieurs differentes machines sur le meme reseau local.

## Getting Started

Sur Eclipse, lancer Graphique.java (ChatApplication/Interface/Graphique.java)

### Prerequisites

Il n'y a rien de special a installer


## Running the tests

Quand on lance le programme, juste entrer 

	votre pseudo, 
	votre mot de passe, 
	votre port qui va etre utilise (On recommande Le num de port>1024),  
Et puis presse le bouton de CREER UN COMPTE pour tester si vous avez bien entrer votre info, et tous ces information va enregistrer dans notre BDD locale.

Sur la page de clavadage

	Il y aura une liste des users connecte sur notre reseau, vous pourriez cliquer l'un des user et entrer le message que vous voulez envoyer, et puis presse le bouton ENVOYER.
	Groupe Chat: il faut selectionner au moins deux users en ligne (Press Ctrl+ the users choisi), apres c'est le meme morphologie que Chat 1 on 1

Changer le pseudo

Vous avez le droit de changer le pseudo quand vous etes en ligne.
	
Deconnecter et reconnecter:

Vous pouvez tres bien deconnecter notre application, et reconnecter juste apres disons que vous avez bien entre votre information de connection comme mot de passe..

### Break down into end to end tests

Il y en a quelques bugs durant le clavardage et un user reconnecter, et le reste fonctionne normallement mais avec quelques Warning.


## Built With

Java.net
Java.Swing

## Versioning

2.0 Beta

1.0 Sur le repertoire : https://github.com/YuhengJin/ChatOnline

## Authors

* **Yuheng JIN** - *Initial work* - [YuhengJIN](https://github.com/YuhengJin)
* **Mohammad Amine** - *Initial work* - [aminea7](https://github.com/aminea7)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

