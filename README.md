*Composition de l’équipe*
- Benazet Laurent
- Frère Baptiste
- Marilier Cyril (défaillant)
- Tetevi Fiacre Togni

*Etat du projet*

- La pull queue pour l'administration a été remplacée par deux push queues (une pour la liste des logs, une pour les logs concernant une ptit-u en particulier) car Cyril travaillait sur les pull queues et est malheureusement défaillant. Ainsi, nous n'avons pas eu le temps de finir leur mise en place et avons préféré livré une application fonctionnelle.
- Le système de log est fonctionnel.
- Il est possible d'ajouter une image et une long-u. Les images ne sont pas supprimées au bout de 5 minutes pour le moment.
- Une requête est présente pour nettoyer tous les stockages.
- La redirection vers un contenu fonctionne.
- L'envoi des mails fonctionne.
- La création de compte fonctionne.


Pour la liste des logs, le contenu est renvoyé sous la forme {contenu1 : nbAcces, contenu2 : nbAcces}.

*Afin de télécharger les logs, il faut cliquer sur le lien envoyé par mail, puis copier-coller le lien présent dans le champ "mediaLink". Nous n'avons pas réussi à utiliser la méthode getMediaLink() pour effectuer ceci, car le lien renvoyé par cette méthode ne fonctionne pas.*
