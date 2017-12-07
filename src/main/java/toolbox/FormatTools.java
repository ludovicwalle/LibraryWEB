package toolbox;

import java.io.*;
import java.util.*;



/**
 * La classe {@link FormatTools} contient des méthodes statiques d'usage général relatives aux formatage de données.
 * @author Ludovic WALLE
 */
public class FormatTools {



	/**
	 * Constructeur privé pour interdire l'instanciation.
	 */
	private FormatTools() {}



	/**
	 * Retourne une chaine contenant la description complète d'une exception.
	 * @param exception Exception.
	 * @return La chaine contenant la description complète d'une exception.
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
	 * Concatène la représentation des objets non <code>null</code> indiqués sous forme de chaine, précédés, séparés et terminés par les chaines indiquées.<br>
	 * Si aucun objet n'est indiqué, ou que tous les objets sont <code>null</code>, la méthode retourne une chaine vide.<br>
	 * @param <O> Type des objets à formatter.
	 * @param before Chaine précédant le premier objet (peut être vide ou <code>null</code>).
	 * @param between Chaine séparant les objets (peut être vide ou <code>null</code>).
	 * @param after Chaine suivant le dernier objet (peut être vide ou <code>null</code>).
	 * @param objects Objets (peut être vide ou <code>null</code>).
	 * @return Les objets formatés.
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
	 * Concatène la représentation des objets non <code>null</code> indiqués sous forme de chaine, précédés, séparés et terminés par les chaines indiquées.<br>
	 * Si l'ensemble est <code>null</code>, la méthode retourne <code>null</code>.<br>
	 * Si l'ensemble est vide, ou ne contient que des <code>null</code>, la méthode retourne une chaine vide.<br>
	 * @param before Chaine précédant le premier objet (peut être vide ou <code>null</code>).
	 * @param between Chaine séparant les objets (peut être vide ou <code>null</code>).
	 * @param after Chaine suivant le dernier objet (peut être vide ou <code>null</code>).
	 * @param objects Ensemble d'objets (peut être vide ou <code>null</code>).
	 * @return Les objets formatés.
	 */
	public static String formatList(String before, String between, String after, Set<?> objects) {
		return formatList(before, between, after, (objects == null) ? null : objects.toArray(new Object[objects.size()]));
	}



}
