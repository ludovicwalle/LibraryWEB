package toolbox.servlet;

/**
 * La classe {@link BooleanParameter} impl�mente la description d'un param�tre de servlet dont la valeur est bool�enne.
 * @author Ludovic WALLE
 */
public class BooleanParameter extends SimpleParameter {



	/**
	 * Param�tre bool�en avec une valeur par d�faut � faux.
	 * @param name Nom du param�tre.
	 * @param description Description du param�tre.
	 */
	public BooleanParameter(String name, String description) {
		this(name, description, Boolean.FALSE);
	}



	/**
	 * @param name Nom du param�tre.
	 * @param description Description du param�tre.
	 * @param defaultValue Valeur par d�faut.
	 */
	public BooleanParameter(String name, String description, boolean defaultValue) {
		super(name, description);
		this.defaultValue = defaultValue;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override protected void appendHelp(Page page) {
		page.appendItem(getName(), getDescription(), "Seule la pr�sence du param�tre compte (pr�sent = vrai, absent = faux), la valeur est ignor�e.");
	}



	/**
	 * Retourne la valeur si le param�tre n'est pas d�fini dans les donn�es d'entr�e.
	 * @return La valeur si le param�tre n'est pas d�fini dans les donn�es d'entr�e.
	 */
	public boolean getDefaultValue() {
		return defaultValue;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public Boolean getValue(CustomizedRequest request) {
		return super.getValue(request) != null;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public Boolean getValue(CustomizedRequest request, String valueWhenNoParameter, String valueWhenParameterWithoutValue) {
		return (Boolean) super.getValue(request, valueWhenNoParameter, valueWhenParameterWithoutValue);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override protected Boolean getValue(String parameterValue) {
		return parameterValue != null;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public Boolean[] getValues(CustomizedRequest request) {
		String[] strings;
		Boolean[] booleans;

		strings = request.getParameterValues(getName());
		if (strings == null) {
			booleans = Constants.NO_BOOLEAN;
		} else {
			booleans = new Boolean[strings.length];
			for (int i = 0; i < strings.length; i++) {
				check(strings[i]);
				booleans[i] = (getValue(strings[i]) != null);
			}
		}
		return booleans;
	}



	/**
	 * Valeur si le param�tre n'est pas d�fini dans les donn�es d'entr�e.
	 */
	private final Boolean defaultValue;



}
