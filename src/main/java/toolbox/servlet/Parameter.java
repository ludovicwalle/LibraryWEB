package toolbox.servlet;

import java.util.regex.*;



/**
 * La classe {@link Parameter} impl�mente la description d'un param�tre de servlet.<br>
 * Le nom du param�tre doit �tre constitu� uniquement des caract�res: {@value #NAME_PATTERN}.
 * @author Ludovic WALLE
 */
public abstract class Parameter implements Comparable<Parameter> {



	/**
	 * @param name Nom du param�tre.
	 * @param description Description du param�tre.
	 */
	public Parameter(String name, String description) {
		if (!NAME_PATTERN.matcher(name).matches()) {
			throw new RuntimeException("La syntaxe du nom de param�tre est invalide: " + name);
		}
		this.name = name;
		this.description = (description == null) ? "" : description;
	}



	/**
	 * Ajoute les informations sur le param�tre � la page d'aide indiqu�e.
	 * @param page Page d'aide � compl�ter.
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
	 * Teste si le param�tre existe dans les donn�es d'entr�e de la servlet, m�me sans valeur.
	 * @param request Donn�es d'entr�e de la servlet.
	 * @return <code>true</code> si le param�tre existe, <code>false</code> sinon.
	 */
	public abstract boolean exists(CustomizedRequest request);



	/**
	 * Retourne le nombre d'occurrences du param�tre.
	 * @param request Donn�es d'entr�e de la servlet.
	 * @return Le nombre d'occurrences du param�tre.
	 */
	public abstract int getCount(CustomizedRequest request);



	/**
	 * Retourne la description du param�tre.
	 * @return La description du param�tre.
	 */
	public final String getDescription() {
		return description;
	}



	/**
	 * Retourne le nom du param�tre.
	 * @return Le nom du param�tre.
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
	 * Description du param�tre.
	 */
	private final String description;



	/**
	 * Nom du param�tre.
	 */
	private final String name;



	/**
	 * Caract�res autoris�s dans les noms de param�tres.
	 */
	public static final String NAME_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";



	/**
	 * Mod�le de syntaxe des noms de param�tres.
	 */
	public static final Pattern NAME_PATTERN = Pattern.compile("[" + NAME_CHARACTERS + "]+");



}
