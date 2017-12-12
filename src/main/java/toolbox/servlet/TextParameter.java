package toolbox.servlet;

/**
 * La classe {@link TextParameter} implémente la description d'un paramètre de servlet dont la valeur est textuelle.
 * @author Ludovic WALLE
 */
public class TextParameter extends SimpleParameter {



	/**
	 * @param name Nom du paramètre.
	 * @param description Description du paramètre.
	 */
	public TextParameter(String name, String description) {
		super(name, description);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public String getValue(CustomizedRequest request) {
		return (String) super.getValue(request);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public String getValue(CustomizedRequest request, String valueWhenNoParameter, String valueWhenParameterWithoutValue) {
		return (String) super.getValue(request, valueWhenNoParameter, valueWhenParameterWithoutValue);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override protected String getValue(String parameterValue) {
		return parameterValue;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public String[] getValues(CustomizedRequest request) {
		String[] values;

		if ((values = request.getParameterValues(getName())) == null) {
			return Constants.NO_STRING;
		} else {
			return values.clone();
		}
	}



}
