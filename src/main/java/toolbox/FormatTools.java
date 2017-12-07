package toolbox;

import java.io.*;
import java.util.*;



/**
 * La classe {@link FormatTools} contient des m�thodes statiques d'usage g�n�ral relatives aux formatage de donn�es.
 * @author Ludovic WALLE
 */
public class FormatTools {



	/**
	 * Constructeur priv� pour interdire l'instanciation.
	 */
	private FormatTools() {}



	/**
	 * Retourne une chaine contenant la description compl�te d'une exception.
	 * @param exception Exception.
	 * @return La chaine contenant la description compl�te d'une exception.
	 */
	public static String formatException(Throwable exception) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		if (exception == null) {
			return null;
		} else {
			exception.printStackTrace(printWriter);
			printWriter.flush();
			return stringWriter.toString();
		}
	}



	/**
	 * Concat�ne la repr�sentation des objets non <code>null</code> indiqu�s sous forme de chaine, pr�c�d�s, s�par�s et termin�s par les chaines indiqu�es.<br>
	 * Si aucun objet n'est indiqu�, ou que tous les objets sont <code>null</code>, la m�thode retourne une chaine vide.<br>
	 * @param <O> Type des objets � formatter.
	 * @param before Chaine pr�c�dant le premier objet (peut �tre vide ou <code>null</code>).
	 * @param between Chaine s�parant les objets (peut �tre vide ou <code>null</code>).
	 * @param after Chaine suivant le dernier objet (peut �tre vide ou <code>null</code>).
	 * @param objects Objets (peut �tre vide ou <code>null</code>).
	 * @return Les objets format�s.
	 */
	@SafeVarargs public static <O extends Object> String formatList(String before, String between, String after, O... objects) {
		StringBuilder builder = new StringBuilder();
		int i;

		if ((objects == null) || (objects.length == 0)) {
			return "";
		} else {
			for (i = 0; (i < objects.length) && (objects[i] == null); i++) {
			}
			if (i < objects.length) {
				builder.append((before == null) ? "" : before);
				builder.append(objects[i++]);
				between = (between == null) ? "" : between;
				for (; i < objects.length; i++) {
					if (objects[i] != null) {
						builder.append(between);
						builder.append(objects[i].toString());
					}
				}
				builder.append((after == null) ? "" : after);
			}
			return builder.toString();
		}
	}



	/**
	 * Concat�ne la repr�sentation des objets non <code>null</code> indiqu�s sous forme de chaine, pr�c�d�s, s�par�s et termin�s par les chaines indiqu�es.<br>
	 * Si l'ensemble est <code>null</code>, la m�thode retourne <code>null</code>.<br>
	 * Si l'ensemble est vide, ou ne contient que des <code>null</code>, la m�thode retourne une chaine vide.<br>
	 * @param before Chaine pr�c�dant le premier objet (peut �tre vide ou <code>null</code>).
	 * @param between Chaine s�parant les objets (peut �tre vide ou <code>null</code>).
	 * @param after Chaine suivant le dernier objet (peut �tre vide ou <code>null</code>).
	 * @param objects Ensemble d'objets (peut �tre vide ou <code>null</code>).
	 * @return Les objets format�s.
	 */
	public static String formatList(String before, String between, String after, Set<?> objects) {
		return formatList(before, between, after, (objects == null) ? null : objects.toArray(new Object[objects.size()]));
	}



}
