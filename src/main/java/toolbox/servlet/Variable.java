package toolbox.servlet;

import java.util.regex.*;



/**
 * La classe {@link Variable} impl�mente la description d'une variable (param�tre ou fichier) de servlet.<br>
 *
 * Le nom de la variable doit �tre constitu� uniquement des caract�res: {@value #NAME_PATTERN}.
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
	 * Ajoute les informations sur la variable � la page d'aide indiqu�e.
	 * @param page Page d'aide � compl�ter.
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
	 * Teste si l'objet indiqu� est �gal � cet objet.<br>
	 * Il sont �gaux si tous leurs champs sont �gaux.
	 * @param object Objet � comparer.
	 * @return <code>true</code> si les objets sont �gaux, <code>false</code> sinon.
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
	 * Teste si la variable existe dans les donn�es d'entr�e de la servlet, m�me sans valeur.
	 * @param request Donn�es d'entr�e de la servlet.
	 * @return <code>true</code> si la variable existe, <code>false</code> sinon.
	 */
	public abstract boolean exists(CustomizedRequest request);



	/**
	 * Retourne le nombre d'occurrences de la variable.
	 * @param request Donn�es d'entr�e de la servlet.
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
	 * Caract�res autoris�s dans les noms de variabless.
	 */
	public static final String NAME_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";



	/**
	 * Mod�le de syntaxe des noms de variables.
	 */
	public static final Pattern NAME_PATTERN = Pattern.compile("[" + NAME_CHARACTERS + "]+");



}
