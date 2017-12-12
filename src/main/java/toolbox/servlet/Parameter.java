package toolbox.servlet;

import java.util.regex.*;



/**
 * La classe {@link Parameter} implémente la description d'un paramètre de servlet.<br>
 * Le nom du paramètre doit être constitué uniquement des caractères: {@value #NAME_PATTERN}.
 * @author Ludovic WALLE
 */
public abstract class Parameter implements Comparable<Parameter> {



	/**
	 * @param name Nom du paramètre.
	 * @param description Description du paramètre.
	 */
	public Parameter(String name, String description) {
		if (!NAME_PATTERN.matcher(name).matches()) {
			throw new RuntimeException("La syntaxe du nom de paramètre est invalide: " + name);
		}
		this.name = name;
		this.description = (description == null) ? "" : description;
	}



	/**
	 * Ajoute les informations sur le paramètre à la page d'aide indiquée.
	 * @param page Page d'aide à compléter.
	 */
	protected void appendHelp(Page page) {
		page.appendItem(getName(), getDescription());
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public final int compareTo(Parameter other) {
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
		Parameter other = (Parameter) object;
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
	 * Teste si le paramètre existe dans les données d'entrée de la servlet, même sans valeur.
	 * @param request Données d'entrée de la servlet.
	 * @return <code>true</code> si le paramètre existe, <code>false</code> sinon.
	 */
	public abstract boolean exists(CustomizedRequest request);



	/**
	 * Retourne le nombre d'occurrences du paramètre.
	 * @param request Données d'entrée de la servlet.
	 * @return Le nombre d'occurrences du paramètre.
	 */
	public abstract int getCount(CustomizedRequest request);



	/**
	 * Retourne la description du paramètre.
	 * @return La description du paramètre.
	 */
	public final String getDescription() {
		return description;
	}



	/**
	 * Retourne le nom du paramètre.
	 * @return Le nom du paramètre.
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
	 * Description du paramètre.
	 */
	private final String description;



	/**
	 * Nom du paramètre.
	 */
	private final String name;



	/**
	 * Caractères autorisés dans les noms de paramètres.
	 */
	public static final String NAME_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";



	/**
	 * Modèle de syntaxe des noms de paramètres.
	 */
	public static final Pattern NAME_PATTERN = Pattern.compile("[" + NAME_CHARACTERS + "]+");



}
