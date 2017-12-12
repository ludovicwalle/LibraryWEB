package toolbox.servlet;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.*;

import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

import toolbox.*;



/**
 * La classe {@link CustomizedRequest} étend la classe {@link HttpServletRequest}, simplifiant le traitement des données encodées en multipart/form-data par attachement. Les méthodes
 * {@link #getParameterMap()} , {@link #getParameterNames()}, {@link #getParameter(String)}, {@link #getParameterValues(String)} et {@link #getQueryString()} subissent une correction d'encodage des
 * noms et des valeurs des paramètres.<br>
 * @author Ludovic WALLE
 */
public class CustomizedRequest extends HttpServletRequestWrapper {



	/**
	 * @param request Données d'entrée de la servlet.
	 * @throws IOException
	 */
	public CustomizedRequest(HttpServletRequest request) throws IOException {
		super(request);

		Vector<String> strings;
		@SuppressWarnings("hiding") Map<String, Vector<String>> stringsMap = new HashMap<>();
		@SuppressWarnings("hiding") Map<String, Vector<FilePart>> filesMap = new HashMap<>();
		@SuppressWarnings("hiding") Set<String> names = new HashSet<>();

		if (ServletFileUpload.isMultipartContent(request)) {
			for (Entry<String, String[]> entry : super.getParameterMap().entrySet()) {
				stringsMap.put(entry.getKey(), strings = new Vector<>());
				for (String string : entry.getValue()) {
					strings.add(string);
				}
			}
			try {
				for (FileItem item : new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request)) {
					if (item.isFormField()) {
						if (!stringsMap.containsKey(item.getFieldName())) {
							stringsMap.put(item.getFieldName(), new Vector<String>());
						}
						stringsMap.get(item.getFieldName()).add(item.getString());
					} else {
						if (!filesMap.containsKey(item.getFieldName())) {
							filesMap.put(item.getFieldName(), new Vector<FilePart>());
						}
						filesMap.get(item.getFieldName()).add(new FilePart(item));
					}
				}
			} catch (FileUploadException exception) {
				throw new IOException(exception);
			}
			this.stringsMap = new HashMap<>(stringsMap.size());
			for (Entry<String, Vector<String>> entry : stringsMap.entrySet()) {
				this.stringsMap.put(entry.getKey(), entry.getValue().toArray(new String[entry.getValue().size()]));
			}
			this.filesMap = new HashMap<>(filesMap.size());
			for (Entry<String, Vector<FilePart>> entry : filesMap.entrySet()) {
				this.filesMap.put(entry.getKey(), entry.getValue().toArray(new FilePart[entry.getValue().size()]));
			}
		} else {
			this.stringsMap = super.getParameterMap();
			this.filesMap = new HashMap<>();
		}
		names.addAll(stringsMap.keySet());
		names.addAll(filesMap.keySet());
		this.names = names.toArray(new String[names.size()]);
	}



	/**
	 * Retourne une collection contenant les fichiers attachés.
	 * @return Une collection contenant les fichiers attachés.
	 */
	public Map<String, FilePart[]> getFileMap() {
		return filesMap;
	}



	/**
	 * Retourne l'unique fichier, ou <code>null</code> si il n'y en a pas.
	 * @param name Nom du paramètre.
	 * @return L'unique fichier, ou <code>null</code> si il n'y en a pas.
	 * @throw {@link RuntimeException} si il y a plusieurs fichiers.
	 */
	public FilePart getFilePart(String name) {
		if (!filesMap.containsKey(name) || (filesMap.get(name).length == 0)) {
			return null;
		} else if (filesMap.get(name).length == 1) {
			return filesMap.get(name)[0];
		} else {
			throw new RuntimeException("Il y a plus d'un fichier pour le paramètre \"" + name + "\".");
		}
	}



	/**
	 * Retourne une collection contenant les fichiers attachés.
	 * @param name Nom du paramètre.
	 * @return Une collection contenant les fichiers attachés.
	 */
	public FilePart[] getFileParts(String name) {
		if (filesMap.containsKey(name)) {
			return filesMap.get(name);
		} else {
			return Constants.NO_FILE;
		}
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public String getParameter(String name) {
		String[] values;

		if ((values = stringsMap.get(name)) == null) {
			return null;
		} else {
			return values[0];
		}
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public Map<String, String[]> getParameterMap() {
		return stringsMap;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override public Enumeration<String> getParameterNames() {
		return new ArrayEnumeration<>(names);
	}



	/**
	 * Retourne l'unique valeur du paramètre, ou <code>null</code> si il n'y en a pas.
	 * @param name Nom du paramètre.
	 * @return L'unique valeur du paramètre, ou <code>null</code> si il n'y en a pas.
	 * @throw {@link RuntimeException} si il y a plusieurs valeurs.
	 */
	public String getParameterValue(String name) {
		if (!stringsMap.containsKey(name) || (stringsMap.get(name).length == 0)) {
			return null;
		} else if (stringsMap.get(name).length == 1) {
			return stringsMap.get(name)[0];
		} else {
			throw new RuntimeException("Il y a plus d'une valeur pour le paramètre \"" + name + "\".");
		}
	}



	/**
	 * {@inheritDoc}
	 */

	@Override public String[] getParameterValues(String name) {
		return stringsMap.get(name);
	}



	/**
	 * {@inheritDoc}<br>
	 * La valeur est calculée d'après les paramètres.
	 */
	@Override public String getQueryString() {
		return computeQueryString(stringsMap);
	}



	/**
	 * Calcule le paramètre QUERY_STRING.
	 * @param parameters Paramètres.
	 * @return Le paramètre QUERY_STRING calculé.
	 */
	public static String computeQueryString(Map<String, String[]> parameters) {
		StringBuilder builder = new StringBuilder();
		String separator = "";

		try {
			for (Entry<String, String[]> values : parameters.entrySet()) {
				if (values.getValue().length == 0) {
					builder.append(String.format("%s%s", separator, values.getKey()));
					separator = "+";
				} else {
					for (String value : values.getValue()) {
						if (value.isEmpty()) {
							builder.append(String.format("%s%s", separator, values.getKey()));
						} else {
							builder.append(String.format("%s%s=%s", separator, values.getKey(), URLEncoder.encode(value, "UTF-8").replaceAll("%5C", "\\\\").replaceAll("%5B", "[").replaceAll("%5D", "]").replaceAll("%7C", "|")));
						}
						separator = "&";
					}
				}
			}
			return builder.toString();
		} catch (UnsupportedEncodingException exception) {
			throw new RuntimeException(exception);
		}
	}



	/**
	 * Fichiers par nom de paramètre.
	 */
	private final Map<String, FilePart[]> filesMap;



	/**
	 * Noms des paramètres de la servlet.
	 */
	private final String[] names;



	/**
	 * Valeurs des paramètres de la servlet.
	 */
	private final Map<String, String[]> stringsMap;



}
