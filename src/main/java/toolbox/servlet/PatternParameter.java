package toolbox.servlet;

import java.util.regex.*;



/**
 * La classe {@link PatternParameter} implémente la description d'un paramètre de servlet dont la valeur doit respecter un modèle de syntaxe.
 * @author Ludovic WALLE
 */
public class PatternParameter extends SimpleParameter {



	/**
	 * @param name Nom du paramètre.
	 * @param description Description du paramètre.
	 * @param patternString Chaine décrivant le modèle de la valeur.
	 */
	public PatternParameter(String name, String description, String patternString) {
		super(name, description);
		this.pattern = Pattern.compile(patternString);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override protected void appendHelp(Page page) {
		page.appendItem(getName(), getDescription(), "La valeur doit respecter le modèle de syntaxe: " + pattern.pattern());
	}



	/**
	 * {@inheritDoc}
	 */
	@Override protected void appendInvalidValue(Page page, String value) {
		page.appendSection("Erreur", "La valeur \"" + CustomizedServlet.encodeForHtml(value) + "\" du paramètre \"" + getName() + "\" ne respecte pas le modèle de syntaxe :" + pattern.pattern());
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public boolean check(String parameterValue) {
		if (pattern.matcher(parameterValue).matches()) {
			return true;
		}
		return false;
	}



	/**
	 * Retourne la chaine décrivant le modèle de la valeur.
	 * @return La chaine décrivant le modèle de la valeur.
	 */
	public String getPatternString() {
		return pattern.toString();
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



	/**
	 * Modèle de la valeur.
	 */
	private final Pattern pattern;



}
