package toolbox.servlet;

/**
 * La classe {@link SimpleParameter} implémente la description d'un paramètre de servlet.<br>
 * Le nom du paramètre doit être constitué uniquement des caractères: {@value #NAME_PATTERN}.
 * @author Ludovic WALLE
 */
public abstract class SimpleParameter extends Parameter {



	/**
	 * @param name Nom du paramètre.
	 * @param description Description du paramètre.
	 */
	public SimpleParameter(String name, String description) {
		super(name, description);
	}



	/**
	 * Ajoute les informations sur le paramètre à la page d'aide indiquée.
	 * @param page Page d'aide à compléter.
	 */
	@Override protected void appendHelp(Page page) {
		page.appendItem(getName(), getDescription());
	}



	/**
	 * Ajoute la description de la valeur invalide indiquée à la page indiquée.
	 * @param page Page d'aide à compléter.
	 * @param value Valeur du paramètre.
	 */
	protected void appendInvalidValue(Page page, String value) {
		page.appendSection("Erreur", "La valeur \"" + CustomizedServlet.encodeForHtml(value) + "\" du paramètre \"" + getName() + "\" est invalide.");
	}



	/**
	 * Ajoute la description de la valeur invalide indiquée à la page indiquée.
	 * @param page Page d'aide à compléter.
	 */
	protected void appendIsAFile(Page page) {
		page.appendSection("Erreur", "La valeur du paramètre \"" + getName() + "\" est un fichier.");
	}



	/**
	 * Teste si la valeur indiquée (éventuellement <code>null</code>) est valide.<br>
	 * Cette méthode est destinée à être surchargée dans les classes dérivées. Par défaut, elle retourne toujours <code>true</code>.
	 * @param parameterValue Valeur du paramètre à tester.
	 * @return <code>true</code> si la valeur est valide, <code>false</code> sinon.
	 */
	@SuppressWarnings("static-method") public boolean check(@SuppressWarnings("unused") String parameterValue) {
		return true;
	}



	/**
	 * Teste si le paramètre existe dans les données d'entrée de la servlet, même sans valeur.
	 * @param request Données d'entrée de la servlet.
	 * @return <code>true</code> si le paramètre existe, <code>false</code> sinon.
	 */
	@Override public final boolean exists(CustomizedRequest request) {
		return request.getParameterValues(getName()) != null;
	}



	/**
	 * Teste si le paramètre existe dans les données d'entrée de la servlet, avec au moins une valeur non vide.<br>
	 * @param request Données d'entrée de la servlet.
	 * @return <code>true</code> si le paramètre existe, <code>false</code> sinon.
	 */
	public final boolean existsWithValue(CustomizedRequest request) {
		if (request.getParameterValues(getName()) == null) {
			return false;
		}
		for (String value : request.getParameterValues(getName())) {
			if ((value != null) && (value.length() > 0)) {
				return true;
			}
		}
		return false;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public int getCount(CustomizedRequest request) {
		return request.getParameterMap().size();
	}



	/**
	 * Retourne la valeur du paramètre si elle existe dans les données d'entrée de la servlet et est valide, <code>null</code> sinon, sauf pour les paramètres {@link BooleanParameter}, où la valeur
	 * retournée est {@link Boolean#FALSE}.
	 * @param request Données d'entrée de la servlet.
	 * @return La valeur du paramètre si elle existe dans les données d'entrée de la servlet et est valide, <code>null</code> sinon, sauf pour les paramètres {@link BooleanParameter}, où la valeur
	 *         retournée est {@link Boolean#FALSE}.
	 * @throws RuntimeException Si le paramètre apparaît plusieurs fois.
	 */
	public Object getValue(CustomizedRequest request) throws RuntimeException {
		return getValue(request, null, null);
	}



	/**
	 * Retourne la valeur du paramètre si elle existe dans les données d'entrée de la servlet et est valide, la valeur indiquée par défaut sinon.
	 * @param request Données d'entrée de la servlet.
	 * @param valueWhenNoParameter Valeur à retourner si le paramètre n'existe pas dans les données d'entrée de la servlet.
	 * @param valueWhenParameterWithoutValue Valeur à retourner si le paramètre existe dans les donnés d'entrée de la servlet mais n'a pas de valeur.
	 * @return La valeur du paramètre si elle existe dans les données d'entrée de la servlet et est valide, la valeur indiquée par défaut sinon.
	 * @throws RuntimeException Si le paramètre apparaît plusieurs fois.
	 */
	public Object getValue(CustomizedRequest request, String valueWhenNoParameter, String valueWhenParameterWithoutValue) throws RuntimeException {
		String[] values;

		values = request.getParameterValues(getName());
		if (values == null) {
			return valueWhenNoParameter;
		} else if (values.length == 1) {
			if ((values[0] == null) || (values[0].length() <= 0)) {
				return valueWhenParameterWithoutValue;
			} else {
				return getValue(values[0]);
			}
		} else {
			throw new RuntimeException("Le paramètre est multi-occurrent: " + getName());
		}
	}



	/**
	 * Retourne un objet du type correspondant à celui du type de paramètre.<br>
	 * Cette méthode est destinée à être surchargée pour les paramètres dont la valeur n'est pas une chaine.
	 * @param parameterValue Valeur chaine du paramètre.
	 * @return Un objet du type correspondant à celui du type de paramètre.
	 */
	@SuppressWarnings("static-method") protected Object getValue(String parameterValue) {
		return parameterValue;
	}



	/**
	 * Retourne un tableau contenant les valeurs du paramètre existantes et valides dans les données d'entrée de la servlet. Si une valeur est invalide, l'entrée correspondante dans le tableau sera
	 * <code>null</code>.<br>
	 * Le tableau est vide si le paramètre n'apparait pas, jamais <code>null</code>.
	 * @param request Données d'entrée de la servlet.
	 * @return Un tableau contenant les valeurs du paramètre existantes et valides dans les données d'entrée de la servlet.
	 */
	public abstract Object[] getValues(CustomizedRequest request);



}
