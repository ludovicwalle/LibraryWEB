package toolbox.servlet;

/**
 * La classe {@link FileParameter} implémente la description d'un paramètre de servlet dont la valeur est un fichier.
 * @author Ludovic WALLE
 */
public class FileParameter extends Parameter {



	/**
	 * @param name Nom du paramètre.
	 * @param description Description du paramètre.
	 */
	public FileParameter(String name, String description) {
		super(name, description);
	}



	/**
	 * Ajoute la description de la valeur invalide indiquée à la page indiquée.
	 * @param page Page d'aide à compléter.
	 */
	protected void appendIsNotAFile(Page page) {
		page.appendSection("Erreur", "La valeur du paramètre \"" + getName() + "\" n'est pas un fichier.");
	}



	/**
	 * Teste si le paramètre existe dans les données d'entrée de la servlet, même sans valeur.
	 * @param request Données d'entrée de la servlet.
	 * @return <code>true</code> si le paramètre existe, <code>false</code> sinon.
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
	 * Retourne la valeur du paramètre si elle existe dans les données d'entrée de la servlet, <code>null</code> sinon.
	 * @param request Données d'entrée de la servlet.
	 * @return La valeur du paramètre si elle existe dans les données d'entrée de la servlet, <code>null</code> sinon.
	 * @throws RuntimeException Si le paramètre apparaît plusieurs fois.
	 */
	public FilePart getFilePart(CustomizedRequest request) {
		FilePart[] parts;

		parts = request.getFileParts(getName());
		if ((parts == null) || (parts.length == 0)) {
			return null;
		} else if (parts.length == 1) {
			return parts[0];
		} else {
			throw new RuntimeException("Il y a plus d'une valeur pour le paramètre \"" + getName() + "\".");
		}
	}



	/**
	 * Retourne un tableau contenant les valeurs du paramètre existantes dans les données d'entrée de la servlet.<br>
	 * Le tableau est vide si le paramètre n'apparait pas, jamais <code>null</code>.
	 * @param request Données d'entrée de la servlet.
	 * @return Un tableau contenant les valeurs du paramètre existantes dans les données d'entrée de la servlet.
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
