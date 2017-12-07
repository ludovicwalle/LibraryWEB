# LibraryWEB

Cette librairie propose un ensemble de classes destinées à fiabiliser et  simplifier l'écriture de servlets. Elles ne prétendent pas couvrir tous les cas, mais s'appliquer aux plus courants, qui sont aussi les plus fréquents. 

Pour les utiliser, la servlet doit étendre la classe *CustomisedServlet* au lieu de HttpServlet.

Principalement, elles permettent de déclarer les méthodes HTTP supportées, avec pour chacune les paramètres et les combinaisons autorisées, et pour chaque paramètre les valeurs autorisées (typage, modèle de syntaxe, énumération de valeur, ...). Un paramètre implicite *Help* permet d'afficher une aide sur l'utilisation de la servlet. Un controle de conformité des paramètres par rapport à cette description est automatiquement réalisé, et une page d'erreur est générée en cas de non conformité, avant même d'arriver dans la classe implémentant la servlet.

Acessoirement, elle permet de récupérer facilement les fichiers des requètes multipart, si ils ne sont pas imbriqués.

D'autres méthodes d'un intéret plus modeste sont aussi disponibles.
