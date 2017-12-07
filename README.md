# LibraryWEB

Cette librairie propose un ensemble de classes destinées à fiabiliser et  simplifier l'écriture de servlets. Elles ne prétendent pas couvrir tous les cas, mais s'appliquer aux plus courants, qui sont aussi les plus fréquents. 

En particulier, elles permettent de déclarer les méthodes HTTP supportées, avec pour chacune les paramètres et les combinaisons autorisées, et pour chaque paramètre les valeurs autorisées (typage, modèle de syntaxe, énumération de valeur, ...). Un paramètre implicite *Help* permet d'afficher une aide sur l'utilisation de la servlet. Un controle de conformité des paramètres par rapport à cette description est automatiquement réalisé, et une page d'erreur est générée en cas de non conformité, avant même d'arriver dans la servlet.

Acessoirement, elle permet de récupérer facilement les fichiers des requètes multipart, si ils ne sont pas imbriqués.

D'autres petites méthodes sont aussi disponibles.
