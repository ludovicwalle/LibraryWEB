# LibraryWEB

Cette librairie est destin�e � simplifier et fiabiliser l'�criture et l'utilisation de servlets. Elle ne pr�tend pas couvrir tous les cas, mais s'appliquer � la grande majorit� des cas courants.

Le point d'entr�e pour l'utiliser est d'�tendre la classe `CustomisedServlet` au lieu de `HttpServlet`.

Le constructeur de `CustomisedServlet` permet de d�clarer les m�thodes HTTP support�es, avec pour chacune les param�tres et les combinaisons autoris�es, et pour chaque param�tre les valeurs autoris�es (typage, mod�le de syntaxe, �num�ration de valeur, ...). Chaque param�tre est sp�cifi� par une variable `*Parameter`. Un param�tre implicite `Help` permet d'afficher une aide sur l'utilisation de la servlet. Un controle de conformit� des param�tres par rapport � cette description est automatiquement r�alis�, et une page d'erreur est g�n�r�e en cas de non conformit�, avant m�me d'arriver dans la classe impl�mentant la servlet.

Acessoirement, elle permet de r�cup�rer facilement les fichiers des requ�tes multipart, si ils ne sont pas imbriqu�s.

D'autres m�thodes d'un int�ret plus modeste sont aussi disponibles.

	public MaServlet(Family family, String url) {
		super("Description de la servlet", //
		    new MethodDescription(new Method[]{Method.GET}, "Consulte la DataSource Kendo.", xor(is(OPTION_PARAMETER, OPTION_PARAMETER$KENDO), and(is(OPTION_PARAMETER, OPTION_PARAMETER$IDS), FIELD, zeroOrOne(FILTER)), zeroOrOne(and(is(OPTION_PARAMETER, OPTION_PARAMETER$IDENTITY, OPTION_PARAMETER$FIELDS, OPTION_PARAMETER$TABLE, OPTION_PARAMETER$ALL), zeroOrOne(FILTER), zeroOrOne(SORT), xor(and(zeroOrOne(TAKE), zeroOrOne(SKIP)), ID), zeroOrOne(PREVIOUS), zeroOrOne(NEXTS), zeroOrOne(PARENTS), zeroOrOne(CHILDREN), zeroOrOne(HIERARCHY)))), OPTION_PARAMETER, FIELD, FILTER, SORT, TAKE, SKIP, ID, PREVIOUS, NEXTS, PARENTS, CHILDREN, HIERARCHY), //
		    new MethodDescription(new Method[]{Method.POST}, "Modifie la DataSource Kendo.", xor(xor(is(OPTION_PARAMETER, OPTION_PARAMETER$KENDO), and(is(OPTION_PARAMETER, OPTION_PARAMETER$IDS), FIELD, zeroOrOne(FILTER)), zeroOrOne(and(is(OPTION_PARAMETER, OPTION_PARAMETER$IDENTITY, OPTION_PARAMETER$FIELDS, OPTION_PARAMETER$TABLE, OPTION_PARAMETER$ALL), zeroOrOne(FILTER), zeroOrOne(SORT), xor(and(zeroOrOne(TAKE), zeroOrOne(SKIP)), ID), zeroOrOne(PREVIOUS), zeroOrOne(NEXTS), zeroOrOne(PARENTS), zeroOrOne(CHILDREN), zeroOrOne(HIERARCHY)))), and(is(ACTION, ACTION$DELETE), ID), and(is(ACTION, ACTION$INSERT, ACTION$UPDATE), DATA)), ACTION, ID, DATA, OPTION_PARAMETER, FIELD, FILTER, SORT, TAKE, SKIP, PREVIOUS, NEXTS, PARENTS, CHILDREN, HIERARCHY));
	}
	/**
	 * Param�tre pour indiquer l'action � effectuer.
	 */
	public static final ListParameter ACTION = new ListParameter("Action", "Action � r�aliser (fait la m�me chose qu'un GET mais en s'affranchissant de la limite de taille d'URL si aucune action n'est pr�cis�e). ", //
	    ACTION$DELETE = new ValueAndLabel("Delete", "Supprime une ligne."), //
	    ACTION$INSERT = new ValueAndLabel("Insert", "Ins�re une ligne."), //
	    ACTION$UPDATE = new ValueAndLabel("Update", "Modifie une ligne."));



	/**
	 * Valeur du param�tre {@link #ACTION}: Supprime la ligne dont l'identifiant est indiqu�.
	 */
	public static final ValueAndLabel ACTION$DELETE;



	/**
	 * Valeur du param�tre {@link #ACTION}: Ins�re une nouvelle ligne contenant les informations indiqu�es.
	 */
	public static final ValueAndLabel ACTION$INSERT;



	/**
	 * Valeur du param�tre {@link #ACTION}: Modifie la ligne dont l'identifiant est indiqu� en les rempla�ant ses informations par celles indiqu�es.
	 */
	public static final ValueAndLabel ACTION$UPDATE;



	/**
	 * Param�tre pour le nombre de niveaux hi�rarchiques ascendants � s�lectionner.
	 */
	public static final IntegerParameter CHILDREN = new IntegerParameter("Children", "Nombre de niveaux hi�rarchiques descendants � s�lectionner (aucun si le param�tre est omis, tous si il est sp�cifi� mais sans valeur).", true, 0, Hardcoded.MAX_ID);



	/**
	 * Param�tre pour les donn�es de la table, en format Json.
	 */
	public static final TextParameter DATA = new TextParameter("Data", "Donn�es de la table, en format Json.");



	/**
	 * Param�tre pour le nom du champ contenant le lien.
	 */
	public static final TextParameter FIELD = new TextParameter("Field", "Nom du champ contenant le lien.");



	/**
	 * Param�tre pour le nombre de lignes � retourner.
	 */
	public static final TextParameter FILTER = new TextParameter("Filter", "Informations de filtrage, en format Json.");



	/**
	 * Param�tre pour le nom de la hi�rarchie � retourner.
	 */
	public static final TextParameter HIERARCHY = new TextParameter("Hierarchy", "Nom de hi�rarchie.");



	/**
	 * Param�tre pour l'identifiant.
	 */
	public static final IntegerParameter ID = new IntegerParameter("Id", "Identifiant.", false, 0, Hardcoded.MAX_ID);



	/**
	 * Param�tre pour le nombre d'identifiants suivants � s�lectionner.
	 */
	public static final IntegerParameter NEXTS = new IntegerParameter("Nexts", "Nombre d'identifiants suivants � s�lectionner, dans l'ordre, en partant de l'�l�ment courant (aucun si le param�tre est omis).", false, 0, Hardcoded.MAX_ID);



	/**
	 * Param�tre pour le nombre de niveaux hi�rarchiques ascendants � s�lectionner.
	 */
	public static final IntegerParameter PARENTS = new IntegerParameter("Parents", "Nombre de niveaux hi�rarchiques ascendants � s�lectionner (aucun si le param�tre est omis, tous si il est sp�cifi� mais sans valeur).", true, 0, Hardcoded.MAX_ID);



	/**
	 * Param�tre pour le nombre d'identifiants pr�c�dents � s�lectionner.
	 */
	public static final IntegerParameter PREVIOUS = new IntegerParameter("Previous", "Nombre d'identifiants pr�c�dents � s�lectionner, dans l'ordre, en partant de l'�l�ment courant (aucun si le param�tre est omis).", false, 0, Hardcoded.MAX_ID);



	/**
	 * Param�tre pour le nombre de lignes � passer.
	 */
	public static final IntegerParameter SKIP = new IntegerParameter("Skip", "Pagination: nombre de lignes � ignorer (aucune si le param�tre est omis).", false, 0, Hardcoded.MAX_ID);



	/**
	 * Param�tre pour les informations de tri.
	 */
	public static final TextParameter SORT = new TextParameter("Sort", "Informations de tri, en format Json.");



	/**
	 * Param�tre pour le nombre de lignes � retourner.
	 */
	public static final IntegerParameter TAKE = new IntegerParameter("Take", "Pagination: nombre de lignes � retourner (toutes si le param�tre est omis).", false, 0, Hardcoded.MAX_ID);



	/**
	 * Param�tre pour indiquer quelles informations retourner.
	 */
	public static final ListParameter OPTION_PARAMETER = new ListParameter("OPTION", "Informations � retourner.", //
	    OPTION_PARAMETER$O1 = new ValueAndLabel("O1", "Option 1."), //
	    OPTION_PARAMETER$O2 = new ValueAndLabel("O2", "Option 2."), //
	    OPTION_PARAMETER$O3 = new ValueAndLabel("O3", "Option 3."));



	/**
	 * Valeur du param�tre {@link #OPTION_PARAMETER}: Bundle en base et tous les liens.
	 */
	public static final ValueAndLabel OPTION_PARAMETER$ALL;



	/**
	 * Valeur du param�tre {@link #OPTION_PARAMETER}: Bundle en base uniquement.
	 */
	public static final ValueAndLabel OPTION_PARAMETER$FIELDS;



	/**
	 * Valeur du param�tre {@link #OPTION_PARAMETER}: Informations d'identit� (identifiant et label).
	 */
	public static final ValueAndLabel OPTION_PARAMETER$IDENTITY;



	/**
	 * Valeur du param�tre {@link #OPTION_PARAMETER}: Identifiants d'objets li�s d�doublonn�s.
	 */
	public static final ValueAndLabel OPTION_PARAMETER$IDS;



	/**
	 * Valeur du param�tre {@link #OPTION_PARAMETER}: Fragment JavaScript pour cr�er les objets Kendo correspondants � la famille (DataSource, Model, ...).
	 */
	public static final ValueAndLabel OPTION_PARAMETER$KENDO;



	/**
	 * Valeur du param�tre {@link #OPTION_PARAMETER}: Bundle en base et liens affichables en mode table.
	 */
	public static final ValueAndLabel OPTION_PARAMETER$TABLE;
