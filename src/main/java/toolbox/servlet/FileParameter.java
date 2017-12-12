package toolbox.servlet;

/**
 * La classe {@link FileParameter} impl�mente la description d'un param�tre de servlet dont la valeur est un fichier.
 * @author Ludovic WALLE
 */
public class FileParameter extends Parameter {



	/**
	 * @param name Nom du param�tre.
	 * @param description Description du param�tre.
	 */
	public FileParameter(String name, String description) {
		super(name, description);
	}



	/**
	 * Ajoute la description de la valeur invalide indiqu�e � la page indiqu�e.
	 * @param page Page d'aide � compl�ter.
	 */
	protected void appendIsNotAFile(Page page) {
		page.appendSection("Erreur", "La valeur du param�tre \"" + getName() + "\" n'est pas un fichier.");
	}



	/**
	 * Teste si le param�tre existe dans les donn�es d'entr�e de la servlet, m�me sans valeur.
	 * @param request Donn�es d'entr�e de la servlet.
	 * @return <code>true</code> si le param�tre existe, <code>false</code> sinon.
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
	 * Retourne la valeur du param�tre si elle existe dans les donn�es d'entr�e de la servlet, <code>null</code> sinon.
	 * @param request Donn�es d'entr�e de la servlet.
	 * @return La valeur du param�tre si elle existe dans les donn�es d'entr�e de la servlet, <code>null</code> sinon.
	 * @throws RuntimeException Si le param�tre appara�t plusieurs fois.
	 */
	public FilePart getFilePart(CustomizedRequest request) {
		FilePart[] parts;

		parts = request.getFileParts(getName());
		if ((parts == null) || (parts.length == 0)) {
			return null;
		} else if (parts.length == 1) {
			return parts[0];
		} else {
			throw new RuntimeException("Il y a plus d'une valeur pour le param�tre \"" + getName() + "\".");
		}
	}



	/**
	 * Retourne un tableau contenant les valeurs du param�tre existantes dans les donn�es d'entr�e de la servlet.<br>
	 * Le tableau est vide si le param�tre n'apparait pas, jamais <code>null</code>.
	 * @param request Donn�es d'entr�e de la servlet.
	 * @return Un tableau contenant les valeurs du param�tre existantes dans les donn�es d'entr�e de la servlet.
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
