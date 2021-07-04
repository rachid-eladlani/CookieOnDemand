# cookiefactory-20-21-team-k


# CookieFactory

### Création du compte nom du fichier: CreateAccount.feature
**En tant que** client
**Je veux** pouvoir créer mon compte **afin de** être enregistré sur l'application et pouvoir accèder au programme Loyalty.
>*Must*: Medium
#### Acceptance Criteria
1. Les informations des clients doivent être complète.Scenario: Account creation
2. Avoir un account pour s'abonner à Loyalty Program. Scenario: Join Loyalty Program
  

### Commande des cookies nom du fichier: OrderCookie.feature 
**En tant que** client
**Je veux** pouvoir commander des cookies **afin de** les voir dans mon shopping cart.
>*Must*: Large
#### Acceptance Criteria
1. commander des cookies les ajoute dans le shopping cart   Scenario: add cookies to the shopping cart
2. supprimer des cookies les retire du shopping cart Scenario: remove cookies from the shopping cart


### Récupérer la commande nom du fichier: OrderRetrieval.feature
**En tant que** client
**Je veux** choisir une heure précise de récupération **afin de** retirer ma commande sans attendre.
>*Must*: Medium
#### Acceptance Criteria
1. quand la commande est prête à être retirée le client la retire du magasin Scenario Outline: set the order ready, in order to notify the customer to retrieve his order


### Payer la commande nom du fichier: PayOrder.feature
**En tant que** client
**Je veux** procéder au paiement de ma commande **afin de** avoir une confirmation de prise en compte de ma commande.
>*Must*: small
#### Acceptance Criteria
1. shopping cart complet et carte bancaire valide Scenario Outline: a User pay an order


### Profiter des réduction du loyalty nom du fichier: loyaltyProgram.feature
**En tant que** client
**Je veux** adhérer au programme loyalty **afin de** profiter des réductions.
>*Should*: small
#### Acceptance Criteria
1. le prix d'une commande d'un client adhérent au programme loyalty est bien réduit  Scenario Outline: a User make and pay an order and has 10% off thanks to his loyalty program
2. ne pas avoir de réduction mais l'activé pour la prochaine fois. Scenario Outline: a User doesn't have the discount but order more than 30 cookies


### Changer les horaires d'un magasin nom du fichier: ChangeStoreHours.feature
**En tant que** responsable de magasin
**Je veux** pouvoir définir l’heure d’ouverture de mon magasin **afin de** que les clients puissent commander avec les nouveaux horaires.
>*Must*: Medium
#### Acceptance Criteria
1. ajouter l'heure d'ouverture.Scenario: adding opening hours of a store 
2. ajouter l'heure de fermetur.Scenario: adding closing hours of a store
3. changer l'heure d'ouverture si déja existante.Scenario: changing opening hours of a store
4. changer l'heure d'ouverture si déja existante.Scenario: changing closing hours of a store


### Donner la commande au client nom du fichier: EndOrder.feature
**En tant que** employé
**Je veux** pouvoir indiquer qu’un client est venu chercher sa commande **afin de** vérifier sa commande et la valider.
>*Should*: Medium
#### Acceptance Criteria
1. la commande doit etre préte. Scenario: order already prepared and waiting for customer


### Voir les statistiques nom du fichier: CheckStats.feature 
**En tant que** manageur
**Je veux** pouvoir voir les statistiques des commandes par recettes **afin de** estimer quelle recette est susceptible d'être supprimée
>*Should*: Medium
#### Acceptance Criteria
1. avoir une des commandes pour une période d'une journée Scenario: check the statistics for a period of a day
2. avoir des commandes sur une journée Scenario: check the statistics for a day
3. demander des statistiques aprés la fermeture du store.Scenario: check the statistics for a day (borderline case) 


### Ajouter des recettes aux magasins nom du fichier: CreateRecipe.feature
**En tant que** Manageur de la maison mère
**Je veux** pouvoir ajouter des recettes **afin de** que tous les franchises ajoutent la recette à leur catalogue de vente
>*Must*: Small
#### Acceptance Criteria
1. avoir accés au COD   Scenario Outline: a manager who has access to the COD add a cookie recipe into the COD


### Modifier les recettes nom du fichier: EditRecipe.feature
**En tant que** Manageur de la maison mère
**Je veux** pouvoir modifier des recettes **afin de** que celles-ci ne soient plus disponibles sur le système
>*should*: Small
#### Acceptance Criteria
1. la recette éxistante dans le catalogue. Scenario Outline: a manager who has access to the COD change a cookie recipe into the COD


### Mémoriser la carte bancaire nom du fichier: SaveCC.feature
**En tant que** Client enregistré
**Je veux** enregistrer ma carte bancaire **afin de** ne pas avoir à la rentrée la prochaine fois
>*could*: Medium
#### Acceptance Criteria
1. la carte bancaire est valide  Scenario Outline: A user would save his credit card to not have to do it the next time


### Commander des cookies personnalisés nom du fichier: CookieOnDemande.feature
**En tant que** Client
**Je veux** ajouter un élément à une recette existante **afin de** commander une recette personnalisée
>*Must*: Medium
#### Acceptance Criteria
1. ajouter topping pour cookie existant dans catalogue Scenario: Add topping
2. ajouter plus de 3 topping pour cookie n Scenario: Add more than 3 toppings
3. supprimer topping pour cookie existant dans catalogue Scenario: Delete topping
4. supprimer topping qui n'est pas dans le cookie.Scenario: Delete non existing topping
5. changer quantité d'un topping pour cookie.Scenario: Change quantity of topping
6. changer quantité d'un qui n'est pas dans le cookie.Scenario: Change quantity of non existing topping


### Mise à jour du BestOfCookie local
**En tant que** Horloge du système
**Je veux** signaler au système qu’il doit mettre à jour la recette la plus populaire durant ces 30 derniers jours 
**afin de** que le système mette à jour le cookie BestOf dans le magasin concerné
>*Must*: Medium
#### Acceptance Criteria
1. si d'un mois à l'autre si le cookie le plus commandé d'un magasin n'est plus le même, le BestOfCookie est mis à jour


### Mise à jour du BestOfCookie national
**En tant que** Horloge du système
**Je veux** signaler au système qu’il doit mettre à jour la recette la plus populaire durant ces 30 derniers jours 
**afin de** que le système mette à jour le cookie BestOf au niveau national
>*Could*: Medium
#### Acceptance Criteria
1. si d'un mois à l'autre si le cookie national le plus commandé n'est plus le même, le BestOfCookieNational est mis à jour


### Mise à jour du BestOfCookie local
**En tant que** Horloge du système
**Je veux** signaler au système qu’il doit mettre à jour la recette la plus populaire durant ces 30 derniers jours 
**afin de** que le système mette à jour le cookie BestOf dans le magasin concerné
>*Must*: Medium
#### Acceptance Criteria
1. si d'un mois à l'autre si le cookie le plus commandé d'un magasin n'est plus le même, le BestOfCookie est mis à jour


### Mise à jour du BestOfCookie national nom du fichier: DiscountWithBestOfCookie.feature
**En tant que** Horloge du système
**Je veux** signaler au système qu’il doit mettre à jour la recette la plus populaire durant ces 30 derniers jours 
**afin de** que le système mette à jour le cookie BestOf au niveau national
>*Must*: Medium
#### Acceptance Criteria
1. si d'un mois à l'autre si le cookie national le plus commandé n'est plus le même, le BestOfCookieNational est mis à jour


### Commander un BestOfCookie nom du fichier: DiscountWithBestOfCookie.feature
**En tant que** Client
**Je veux** pouvoir commander le BestOfCookie que la boutique propose
**afin de** bénéficier des 10% de réduction
>*Must*: Small
#### Acceptance Criteria
1. Un client qui achète un BestOfCookie d'un magasin profite de la réduction


### Indiquer qu'il y a eu une panne nom du fichier: Kitchen Report.feature
**En tant que** employé dans la cuisine
**Je veux** indiquer qu’il y a eu une panne
**afin de** rediriger les commandes du magasin vers un autre magasin à proximité pouvant assurer la commande 
>*Should*: Medium
#### Acceptance Criteria
1. les nouvelles commandes ne peuvent pas être faites dans ce magasin         Scenario Outline: an employee report a dysfunction of the kitchen
2. les commandes actuelles du magasin sont redirigées vers d'autres magasins  Scenario Outline: an employee report a dysfunction of the kitchen


### Livraison de la commande nom du fichier: DeliveryWithMarcelEat.feature
**En tant que** client
**Je veux** me faire livrer ma commande
**afin de** recevoir un mail de confirmation de MarcelEat
>*Must*: Small
#### Acceptance Criteria
1. Un mail de confirmation est envoyé au client   Scenario: Customer get delivered By MarcelEat with simple delivery option


### Commander malgré des horaires saturées nom du fichier: CheckStat.feature
**En tant que** client
**Je veux** avoir plusieurs choix de magasins
**afin de** récupérer ma commande malgré des horaires saturées (ex: 10 order par h, alors ca grise l’horaire)
>*Won't*: Medium
#### Acceptance Criteria
1. un client ne peux pas commander dans un magasin sur des horaire saturées 


### Livraison de la commande au dernier moment nom du fichier: DeliveryWithMarcelEat.feature
**En tant que** client
**Je veux** faire appelle au service de livraison au dernier moment
**afin de** recevoir une confirmation de MarcelEat pour ma commande à l’heure souhaitée 
>*Must*: Small
#### Acceptance Criteria
1. Recevoir un mail de confirmation avec l'heure prévue Scenario: Customer get delivered By MarcelEat with last delivery option


### Réapprovisionner les stocks du magasin nom du fichier: StockSupply.feature
**En tant que** employé ayant accçès au stock
**Je veux** réapprovisionner les stocks du magasins
**afin de** mettre à jour le niveau du stock selon les arrivages
>*Must*: Small
#### Acceptance Criteria
1. Le niveau de stock correspond à bien à ce qui a été rajouté Scenario: an employee receives a new delivery of ingredient



## Sonar
Lancer SonarQube

## on linux
```
bin/linux-x86-64/sonar.sh start 
```
## or on MacOS
```
bin/macosx-universal-64/sonar.sh start
```
## or on Windows
```
bin\windows-x86-64\StartSonar.bat 
```

> Dans notre répertoire :
```
mvn clean package -Psonar sonar:sonar
```

## Team
- [Rachid EL ADLANI](https://github.com/rachid-eladlani)
- [Julien KAPLAN](https://github.com/JulienK-hub)
- [Abdel BELKHIRI](https://github.com/AbdelBelkhiri)
- [Armand FARGEON](https://github.com/armandfargeon)
- [Mohamed FERTALA](https://github.com/fertala2)



cookiefactory-20-21-team-k created by GitHub Classroom




