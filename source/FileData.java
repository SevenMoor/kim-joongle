package source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileData {
	private File file;
	private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	private ArrayList<String> keywords = new ArrayList<String>();
	private String location;

	public FileData(File currentFile) {
		super();
		this.location = currentFile.getAbsolutePath();
		this.file = currentFile;
	}

	public void readFile() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {

			// If we encounter a h1 or title tag...
			Pattern title = Pattern.compile("(<h1>(.*?)</h1>|<title>(.*?)</title>)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = title.matcher(line);

			while (matcher.find()) {
				// First process the h1 matches
				String codeGroup = matcher.group(2);

				if (codeGroup != null) {
					codeGroup = codeGroup.toLowerCase();
					codeGroup = codeGroup.replaceAll("<(/)?(.*?)>", "");
					codeGroup = codeGroup.replaceAll("&agrave;", "à");
					codeGroup = codeGroup.replaceAll("&eacute;", "é");
					codeGroup = codeGroup.replaceAll("&egrave;", "è");
					codeGroup = codeGroup.replaceAll("&ecirc;", "ê");
					codeGroup = codeGroup.replaceAll("&euml;", "ë");
					codeGroup = codeGroup.replaceAll("&iuml;", "ï");
					codeGroup = codeGroup.replaceAll("&icirc;", "î");
					codeGroup = codeGroup.replaceAll("&ucirc;", "û");
					codeGroup = codeGroup.replaceAll("&ocirc;", "ô");
					codeGroup = codeGroup.replaceAll("&nbsp;", " ");
					codeGroup = codeGroup.replaceAll("&", " ");

					keywords.add(codeGroup);
				}

				// Then the title matches
				codeGroup = matcher.group(3);
				if (codeGroup != null) {
					codeGroup = codeGroup.toLowerCase();
					codeGroup = codeGroup.replaceAll("(<(/)?(.*?)>)", "");
					codeGroup = codeGroup.replaceAll("&agrave;", "à");
					codeGroup = codeGroup.replaceAll("&eacute;", "é");
					codeGroup = codeGroup.replaceAll("&egrave;", "è");
					codeGroup = codeGroup.replaceAll("&ecirc;", "ê");
					codeGroup = codeGroup.replaceAll("&euml;", "ë");
					codeGroup = codeGroup.replaceAll("&iuml;", "ï");
					codeGroup = codeGroup.replaceAll("&icirc;", "î");
					codeGroup = codeGroup.replaceAll("&ucirc;", "û");
					codeGroup = codeGroup.replaceAll("&ocirc;", "ô");
					codeGroup = codeGroup.replaceAll("&nbsp;", " ");
					codeGroup = codeGroup.replaceAll("&", " ");

					keywords.add(codeGroup);
				}

				// And remove empty entries
				String empty = new String();
				for (int i = 0; i < keywords.size(); i++) {
					if (keywords.get(i).equals(empty)) {
						keywords.remove(i);
						i--;
					}
				}

			}
		}
		reader.close();
	}

	public String getDate() {
		return date.format(file.lastModified());
	}

	public ArrayList<String> getKeywords() {
		return keywords;
	}

	public String getLocation() {
		return location;
	}

	public void save(BufferedWriter writer) throws IOException {
		writer.write("\t<file>");
		writer.write("\t<location>" + location + "</location>\n");
		for (String keyword : keywords) {
			writer.write("\t\t\t<keyword>" + keyword + "</keyword>\n");
		}
		writer.write("\t</file>\n");
	}

	@Override
	public String toString() {
		String result = "LastMod :" + getDate() + "\nLocation :" + getLocation();
		for (String keyword : keywords) {
			result += "\n\t->" + keyword;
		}
		return result;
	}
}
