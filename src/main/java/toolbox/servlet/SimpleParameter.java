package toolbox.servlet;

/**
 * La classe {@link SimpleParameter} impl�mente la description d'un param�tre de servlet.<br>
 * Le nom du param�tre doit �tre constitu� uniquement des caract�res: {@value #NAME_PATTERN}.
 * @author Ludovic WALLE
 */
public abstract class SimpleParameter extends Parameter {



	/**
	 * @param name Nom du param�tre.
	 * @param description Description du param�tre.
	 */
	public SimpleParameter(String name, String description) {
		super(name, description);
	}



	/**
	 * Ajoute les informations sur le param�tre � la page d'aide indiqu�e.
	 * @param page Page d'aide � compl�ter.
	 */
	@Override protected void appendHelp(Page page) {
		page.appendItem(getName(), getDescription());
	}



	/**
	 * Ajoute la description de la valeur invalide indiqu�e � la page indiqu�e.
	 * @param page Page d'aide � compl�ter.
	 * @param value Valeur du param�tre.
	 */
	protected void appendInvalidValue(Page page, String value) {
		page.appendSection("Erreur", "La valeur \"" + CustomizedServlet.encodeForHtml(value) + "\" du param�tre \"" + getName() + "\" est invalide.");
	}



	/**
	 * Ajoute la description de la valeur invalide indiqu�e � la page indiqu�e.
	 * @param page Page d'aide � compl�ter.
	 */
	protected void appendIsAFile(Page page) {
		page.appendSection("Erreur", "La valeur du param�tre \"" + getName() + "\" est un fichier.");
	}



	/**
	 * Teste si la valeur indiqu�e (�ventuellement <code>null</code>) est valide.<br>
	 * Cette m�thode est destin�e � �tre surcharg�e dans les classes d�riv�es. Par d�faut, elle retourne toujours <code>true</code>.
	 * @param parameterValue Valeur du param�tre � tester.
	 * @return <code>true</code> si la valeur est valide, <code>false</code> sinon.
	 */
	@SuppressWarnings("static-method") public boolean check(@SuppressWarnings("unused") String parameterValue) {
		return true;
	}



	/**
	 * Teste si le param�tre existe dans les donn�es d'entr�e de la servlet, m�me sans valeur.
	 * @param request Donn�es d'entr�e de la servlet.
	 * @return <code>true</code> si le param�tre existe, <code>false</code> sinon.
	 */
	@Override public final boolean exists(CustomizedRequest request) {
		return request.getParameterValues(getName()) != null;
	}



	/**
	 * Teste si le param�tre existe dans les donn�es d'entr�e de la servlet, avec au moins une valeur non vide.<br>
	 * @param request Donn�es d'entr�e de la servlet.
	 * @return <code>true</code> si le param�tre existe, <code>false</code> sinon.
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
	 * Retourne la valeur du param�tre si elle existe dans les donn�es d'entr�e de la servlet et est valide, <code>null</code> sinon, sauf pour les param�tres {@link BooleanParameter}, o� la valeur
	 * retourn�e est {@link Boolean#FALSE}.
	 * @param request Donn�es d'entr�e de la servlet.
	 * @return La valeur du param�tre si elle existe dans les donn�es d'entr�e de la servlet et est valide, <code>null</code> sinon, sauf pour les param�tres {@link BooleanParameter}, o� la valeur
	 *         retourn�e est {@link Boolean#FALSE}.
	 * @throws RuntimeException Si le param�tre appara�t plusieurs fois.
	 */
	public Object getValue(CustomizedRequest request) throws RuntimeException {
		return getValue(request, null, null);
	}



	/**
	 * Retourne la valeur du param�tre si elle existe dans les donn�es d'entr�e de la servlet et est valide, la valeur indiqu�e par d�faut sinon.
	 * @param request Donn�es d'entr�e de la servlet.
	 * @param valueWhenNoParameter Valeur � retourner si le param�tre n'existe pas dans les donn�es d'entr�e de la servlet.
	 * @param valueWhenParameterWithoutValue Valeur � retourner si le param�tre existe dans les donn�s d'entr�e de la servlet mais n'a pas de valeur.
	 * @return La valeur du param�tre si elle existe dans les donn�es d'entr�e de la servlet et est valide, la valeur indiqu�e par d�faut sinon.
	 * @throws RuntimeException Si le param�tre appara�t plusieurs fois.
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
			throw new RuntimeException("Le param�tre est multi-occurrent: " + getName());
		}
	}



	/**
	 * Retourne un objet du type correspondant � celui du type de param�tre.<br>
	 * Cette m�thode est destin�e � �tre surcharg�e pour les param�tres dont la valeur n'est pas une chaine.
	 * @param parameterValue Valeur chaine du param�tre.
	 * @return Un objet du type correspondant � celui du type de param�tre.
	 */
	@SuppressWarnings("static-method") protected Object getValue(String parameterValue) {
		return parameterValue;
	}



	/**
	 * Retourne un tableau contenant les valeurs du param�tre existantes et valides dans les donn�es d'entr�e de la servlet. Si une valeur est invalide, l'entr�e correspondante dans le tableau sera
	 * <code>null</code>.<br>
	 * Le tableau est vide si le param�tre n'apparait pas, jamais <code>null</code>.
	 * @param request Donn�es d'entr�e de la servlet.
	 * @return Un tableau contenant les valeurs du param�tre existantes et valides dans les donn�es d'entr�e de la servlet.
	 */
	public abstract Object[] getValues(CustomizedRequest request);



}
