package toolbox.servlet;

import java.util.regex.*;



/**
 * La classe {@link Variable} implémente la description d'une variable (paramètre ou fichier) de servlet.<br>
 *
 * Le nom de la variable doit être constitué uniquement des caractères: {@value #NAME_PATTERN}.
 * @author Ludovic WALLE
 */
public abstract class Variable implements Comparable<Variable> {



	/**
	 * @param name Nom de la variable.
	 * @param description Description de la variable.
	 */
	public Variable(String name, String description) {
		if (!NAME_PATTERN.matcher(name).matches()) {
			throw new RuntimeException("La syntaxe du nom de la variable est invalide: " + name);
		}
		this.name = name;
		this.description = (description == null) ? "" : description;
	}



	/**
	 * Ajoute les informations sur la variable à la page d'aide indiquée.
	 * @param page Page d'aide à compléter.
	 */
	protected void appendHelp(Page page) {
		page.appendItem(getName(), getDescription());
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public final int compareTo(Variable other) {
		return name.compareTo(other.name);
	}



	/**
	 * Teste si l'objet indiqué est égal à cet objet.<br>
	 * Il sont égaux si tous leurs champs sont égaux.
	 * @param object Objet à comparer.
	 * @return <code>true</code> si les objets sont égaux, <code>false</code> sinon.
	 */
	@Override public final boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		Variable other = (Variable) object;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}



	/**
	 * Teste si la variable existe dans les données d'entrée de la servlet, même sans valeur.
	 * @param request Données d'entrée de la servlet.
	 * @return <code>true</code> si la variable existe, <code>false</code> sinon.
	 */
	public abstract boolean exists(CustomizedRequest request);



	/**
	 * Retourne le nombre d'occurrences de la variable.
	 * @param request Données d'entrée de la servlet.
	 * @return Le nombre d'occurrences de la variable.
	 */
	public abstract int getCount(CustomizedRequest request);



	/**
	 * Retourne la description de la variable.
	 * @return La description de la variable.
	 */
	public final String getDescription() {
		return description;
	}



	/**
	 * Retourne le nom de la variable.
	 * @return Le nom de la variable.
	 */
	public final String getName() {
		return name;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public final int hashCode() {
		return name.hashCode();
	}



	/**
	 * Description de la variable.
	 */
	private final String description;



	/**
	 * Nom de la variable.
	 */
	private final String name;



	/**
	 * Caractères autorisés dans les noms de variabless.
	 */
	public static final String NAME_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";



	/**
	 * Modèle de syntaxe des noms de variables.
	 */
	public static final Pattern NAME_PATTERN = Pattern.compile("[" + NAME_CHARACTERS + "]+");



}
