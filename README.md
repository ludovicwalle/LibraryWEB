# LibraryWEB

Cette librairie est destinée à simplifier et fiabiliser l'écriture et l'utilisation de servlets. Elle ne prétend pas couvrir tous les cas, mais s'appliquer à la grande majorité des cas courants.

Le point d'entrée pour l'utiliser est d'étendre la classe `CustomisedServlet` au lieu de `HttpServlet`.

Le constructeur de `CustomisedServlet` permet de déclarer les méthodes HTTP supportées, avec pour chacune les paramètres et les combinaisons autorisées, et pour chaque paramètre les valeurs autorisées (typage, modèle de syntaxe, énumération de valeur, ...). Chaque paramètre est spécifié par une variable `*Parameter`. Un paramètre implicite `Help` permet d'afficher une aide sur l'utilisation de la servlet. Un controle de conformité des paramètres par rapport à cette description est automatiquement réalisé, et une page d'erreur est générée en cas de non conformité, avant même d'arriver dans la classe implémentant la servlet.

Acessoirement, elle permet de récupérer facilement les fichiers des requètes multipart, si ils ne sont pas imbriqués.

D'autres méthodes d'un intéret plus modeste sont aussi disponibles.

	public MaServlet(Family family, String url) {
		super("Description de la servlet", //
		    new MethodDescription(new Method[]{Method.GET}, "Consulte la DataSource Kendo.", xor(is(OPTION_PARAMETER, OPTION_PARAMETER$KENDO), and(is(OPTION_PARAMETER, OPTION_PARAMETER$IDS), FIELD, zeroOrOne(FILTER)), zeroOrOne(and(is(OPTION_PARAMETER, OPTION_PARAMETER$IDENTITY, OPTION_PARAMETER$FIELDS, OPTION_PARAMETER$TABLE, OPTION_PARAMETER$ALL), zeroOrOne(FILTER), zeroOrOne(SORT), xor(and(zeroOrOne(TAKE), zeroOrOne(SKIP)), ID), zeroOrOne(PREVIOUS), zeroOrOne(NEXTS), zeroOrOne(PARENTS), zeroOrOne(CHILDREN), zeroOrOne(HIERARCHY)))), OPTION_PARAMETER, FIELD, FILTER, SORT, TAKE, SKIP, ID, PREVIOUS, NEXTS, PARENTS, CHILDREN, HIERARCHY), //
		    new MethodDescription(new Method[]{Method.POST}, "Modifie la DataSource Kendo.", xor(xor(is(OPTION_PARAMETER, OPTION_PARAMETER$KENDO), and(is(OPTION_PARAMETER, OPTION_PARAMETER$IDS), FIELD, zeroOrOne(FILTER)), zeroOrOne(and(is(OPTION_PARAMETER, OPTION_PARAMETER$IDENTITY, OPTION_PARAMETER$FIELDS, OPTION_PARAMETER$TABLE, OPTION_PARAMETER$ALL), zeroOrOne(FILTER), zeroOrOne(SORT), xor(and(zeroOrOne(TAKE), zeroOrOne(SKIP)), ID), zeroOrOne(PREVIOUS), zeroOrOne(NEXTS), zeroOrOne(PARENTS), zeroOrOne(CHILDREN), zeroOrOne(HIERARCHY)))), and(is(ACTION, ACTION$DELETE), ID), and(is(ACTION, ACTION$INSERT, ACTION$UPDATE), DATA)), ACTION, ID, DATA, OPTION_PARAMETER, FIELD, FILTER, SORT, TAKE, SKIP, PREVIOUS, NEXTS, PARENTS, CHILDREN, HIERARCHY));
	}
	/**
	 * Paramètre pour indiquer l'action à effectuer.
	 */
	public static final ListParameter ACTION = new ListParameter("Action", "Action à réaliser (fait la même chose qu'un GET mais en s'affranchissant de la limite de taille d'URL si aucune action n'est précisée). ", //
	    ACTION$DELETE = new ValueAndLabel("Delete", "Supprime une ligne."), //
	    ACTION$INSERT = new ValueAndLabel("Insert", "Insère une ligne."), //
	    ACTION$UPDATE = new ValueAndLabel("Update", "Modifie une ligne."));



	/**
	 * Valeur du paramètre {@link #ACTION}: Supprime la ligne dont l'identifiant est indiqué.
	 */
	public static final ValueAndLabel ACTION$DELETE;



	/**
	 * Valeur du paramètre {@link #ACTION}: Insère une nouvelle ligne contenant les informations indiquées.
	 */
	public static final ValueAndLabel ACTION$INSERT;



	/**
	 * Valeur du paramètre {@link #ACTION}: Modifie la ligne dont l'identifiant est indiqué en les remplaçant ses informations par celles indiquées.
	 */
	public static final ValueAndLabel ACTION$UPDATE;



	/**
	 * Paramètre pour le nombre de niveaux hiérarchiques ascendants à sélectionner.
	 */
	public static final IntegerParameter CHILDREN = new IntegerParameter("Children", "Nombre de niveaux hiérarchiques descendants à sélectionner (aucun si le paramètre est omis, tous si il est spécifié mais sans valeur).", true, 0, Hardcoded.MAX_ID);



	/**
	 * Paramètre pour les données de la table, en format Json.
	 */
	public static final TextParameter DATA = new TextParameter("Data", "Données de la table, en format Json.");



	/**
	 * Paramètre pour le nom du champ contenant le lien.
	 */
	public static final TextParameter FIELD = new TextParameter("Field", "Nom du champ contenant le lien.");



	/**
	 * Paramètre pour le nombre de lignes à retourner.
	 */
	public static final TextParameter FILTER = new TextParameter("Filter", "Informations de filtrage, en format Json.");



	/**
	 * Paramètre pour le nom de la hiérarchie à retourner.
	 */
	public static final TextParameter HIERARCHY = new TextParameter("Hierarchy", "Nom de hiérarchie.");



	/**
	 * Paramètre pour l'identifiant.
	 */
	public static final IntegerParameter ID = new IntegerParameter("Id", "Identifiant.", false, 0, Hardcoded.MAX_ID);



	/**
	 * Paramètre pour le nombre d'identifiants suivants à sélectionner.
	 */
	public static final IntegerParameter NEXTS = new IntegerParameter("Nexts", "Nombre d'identifiants suivants à sélectionner, dans l'ordre, en partant de l'élément courant (aucun si le paramètre est omis).", false, 0, Hardcoded.MAX_ID);



	/**
	 * Paramètre pour le nombre de niveaux hiérarchiques ascendants à sélectionner.
	 */
	public static final IntegerParameter PARENTS = new IntegerParameter("Parents", "Nombre de niveaux hiérarchiques ascendants à sélectionner (aucun si le paramètre est omis, tous si il est spécifié mais sans valeur).", true, 0, Hardcoded.MAX_ID);



	/**
	 * Paramètre pour le nombre d'identifiants précédents à sélectionner.
	 */
	public static final IntegerParameter PREVIOUS = new IntegerParameter("Previous", "Nombre d'identifiants précédents à sélectionner, dans l'ordre, en partant de l'élément courant (aucun si le paramètre est omis).", false, 0, Hardcoded.MAX_ID);



	/**
	 * Paramètre pour le nombre de lignes à passer.
	 */
	public static final IntegerParameter SKIP = new IntegerParameter("Skip", "Pagination: nombre de lignes à ignorer (aucune si le paramètre est omis).", false, 0, Hardcoded.MAX_ID);



	/**
	 * Paramètre pour les informations de tri.
	 */
	public static final TextParameter SORT = new TextParameter("Sort", "Informations de tri, en format Json.");



	/**
	 * Paramètre pour le nombre de lignes à retourner.
	 */
	public static final IntegerParameter TAKE = new IntegerParameter("Take", "Pagination: nombre de lignes à retourner (toutes si le paramètre est omis).", false, 0, Hardcoded.MAX_ID);



	/**
	 * Paramètre pour indiquer quelles informations retourner.
	 */
	public static final ListParameter OPTION_PARAMETER = new ListParameter("OPTION", "Informations à retourner.", //
	    OPTION_PARAMETER$O1 = new ValueAndLabel("O1", "Option 1."), //
	    OPTION_PARAMETER$O2 = new ValueAndLabel("O2", "Option 2."), //
	    OPTION_PARAMETER$O3 = new ValueAndLabel("O3", "Option 3."));



	/**
	 * Valeur du paramètre {@link #OPTION_PARAMETER}: Bundle en base et tous les liens.
	 */
	public static final ValueAndLabel OPTION_PARAMETER$ALL;



	/**
	 * Valeur du paramètre {@link #OPTION_PARAMETER}: Bundle en base uniquement.
	 */
	public static final ValueAndLabel OPTION_PARAMETER$FIELDS;



	/**
	 * Valeur du paramètre {@link #OPTION_PARAMETER}: Informations d'identité (identifiant et label).
	 */
	public static final ValueAndLabel OPTION_PARAMETER$IDENTITY;



	/**
	 * Valeur du paramètre {@link #OPTION_PARAMETER}: Identifiants d'objets liés dédoublonnés.
	 */
	public static final ValueAndLabel OPTION_PARAMETER$IDS;



	/**
	 * Valeur du paramètre {@link #OPTION_PARAMETER}: Fragment JavaScript pour créer les objets Kendo correspondants à la famille (DataSource, Model, ...).
	 */
	public static final ValueAndLabel OPTION_PARAMETER$KENDO;



	/**
	 * Valeur du paramètre {@link #OPTION_PARAMETER}: Bundle en base et liens affichables en mode table.
	 */
	public static final ValueAndLabel OPTION_PARAMETER$TABLE;
	