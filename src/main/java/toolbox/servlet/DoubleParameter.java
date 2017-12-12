package toolbox.servlet;

/**
 * La classe {@link DoubleParameter} impl�mente la description d'un param�tre de servlet dont la valeur est un double.
 * @author Ludovic WALLE
 */
public class DoubleParameter extends SimpleParameter {



	/**
	 * @param name Nom du param�tre.
	 * @param description Description du param�tre.
	 * @param canBeNull Indicateur de valeur nulle autoris�e.
	 */
	public DoubleParameter(String name, String description, boolean canBeNull) {
		super(name, description);
		this.canBeNull = canBeNull;
	}



	/**
	 * Ajoute les informations sur le param�tre � la page d'aide indiqu�e.
	 * @param page Page d'aide � compl�ter.
	 */
	@Override protected void appendHelp(Page page) {
		page.appendItem(getName(), getDescription());
	}



	/**
	 * {@inheritDoc}
	 */
	@Override protected void appendInvalidValue(Page page, String value) {
		page.appendSection("Erreur", "La valeur \"" + CustomizedServlet.encodeForHtml(value) + "\" du param�tre \"" + getName() + "\" n'est pas un double.");
	}



	/**
	 * Retourne l'indicateur de valeur nulle autoris�e.
	 * @return L'indicateur de valeur nulle autoris�e.
	 */
	public boolean canBeNull() {
		return canBeNull;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public boolean check(String parameterValue) {
		try {
			if (canBeNull && parameterValue.equals("")) {
				return true;
			} else if (parameterValue.matches("\\A(?:(?:0)|(?:-?[1-9][0-9]*)|(?:-?(?:0|[1-9][0-9]*)\\.[0-9]+))\\z")) {
				Double.parseDouble(parameterValue);
				return true;
			}
		} catch (NumberFormatException exception) {
		}
		return false;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public Double getValue(CustomizedRequest request) {
		return (Double) super.getValue(request);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public Double getValue(CustomizedRequest request, String valueWhenNoParameter, String valueWhenParameterWithoutValue) {
		return (Double) super.getValue(request, valueWhenNoParameter, valueWhenParameterWithoutValue);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override protected Double getValue(String parameterValue) {
		try {
			return (parameterValue == null) ? null : Double.parseDouble(parameterValue);
		} catch (NumberFormatException exception) {
		}
		return null;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public Double[] getValues(CustomizedRequest request) {
		String[] strings;
		Double[] doubles;

		strings = request.getParameterValues(getName());
		if (strings == null) {
			doubles = new Double[0];
		} else {
			doubles = new Double[strings.length];
			for (int i = 0; i < strings.length; i++) {
				check(strings[i]);
				doubles[i] = getValue(strings[i]);
			}
		}
		return doubles;
	}



	/**
	 * Indicateur de valeur nulle autoris�e.
	 */
	private final boolean canBeNull;



}
