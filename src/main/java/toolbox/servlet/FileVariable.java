package toolbox.servlet;

/**
 * La classe {@link FileVariable} implémente la description d'une variable de servlet dont la valeur est un fichier.
 * @author Ludovic WALLE
 */
public class FileVariable extends Variable {



	/**
	 * @param name Nom de la variable.
	 * @param description Description de la variable.
	 */
	public FileVariable(String name, String description) {
		super(name, description);
	}



	/**
	 * Ajoute la description de la valeur invalide indiquée à la page indiquée.
	 * @param page Page d'aide à compléter.
	 */
	protected void appendIsNotAFile(Page page) {
		page.appendSection("Erreur", "La valeur de la variable \"" + getName() + "\" n'est pas un fichier.");
	}



	/**
	 * Teste si la variable existe dans les données d'entrée de la servlet, même sans valeur.
	 * @param request Données d'entrée de la servlet.
	 * @return <code>true</code> si la variable existe, <code>false</code> sinon.
	 */
	@Override public final boolean exists(CustomizedRequest request) {
		return request.getFileParts(getName()) != null;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public int getCount(CustomizedRequest request) {
		return request.getFileParts(getName()).length;
	}



	/**
	 * Retourne la valeur de la variable si elle existe dans les données d'entrée de la servlet, <code>null</code> sinon.
	 * @param request Données d'entrée de la servlet.
	 * @return La valeur de la variable si elle existe dans les données d'entrée de la servlet, <code>null</code> sinon.
	 * @throws RuntimeException Si la variable apparaît plusieurs fois.
	 */
	public FilePart getFilePart(CustomizedRequest request) {
		FilePart[] parts;

		parts = request.getFileParts(getName());
		if ((parts == null) || (parts.length == 0)) {
			return null;
		} else if (parts.length == 1) {
			return parts[0];
		} else {
			throw new RuntimeException("Il y a plus d'une valeur pour le a variable \"" + getName() + "\".");
		}
	}



	/**
	 * Retourne un tableau contenant les valeurs de la variable existantes dans les données d'entrée de la servlet.<br>
	 * Le tableau est vide si la variable n'apparait pas, jamais <code>null</code>.
	 * @param request Données d'entrée de la servlet.
	 * @return Un tableau contenant les valeurs de la variable existantes dans les données d'entrée de la servlet.
	 */
	public FilePart[] getFilesParts(CustomizedRequest request) {
		FilePart[] parts;

		if ((parts = request.getFileParts(getName())) == null) {
			return Constants.NO_FILE;
		} else {
			return parts.clone();
		}
	}



}
